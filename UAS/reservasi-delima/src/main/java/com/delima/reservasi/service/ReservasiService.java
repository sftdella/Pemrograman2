package com.delima.reservasi.service;

import com.delima.reservasi.exception.KamarPenuhException;
import com.delima.reservasi.exception.ReservasiGagalException;
import com.delima.reservasi.model.Reservasi;
import com.delima.reservasi.model.TipeKamar;
import com.delima.reservasi.repository.ReservasiRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer: tempat semua LOGIKA BISNIS aplikasi diletakkan,
 * dipisahkan dari Controller (yang mengurus HTTP request/response)
 * dan Repository (yang mengurus akses database).
 *
 * Di sinilah requirement "Exception Handling: Tangani error jika
 * reservasi gagal dilakukan atau kamar penuh" diimplementasikan.
 */
@Service
public class ReservasiService {

    private final ReservasiRepository reservasiRepository;

    public ReservasiService(ReservasiRepository reservasiRepository) {
        this.reservasiRepository = reservasiRepository;
    }

    public List<Reservasi> getSemuaReservasi() {
        return reservasiRepository.findAllByOrderByTanggalCheckInAsc();
    }

    public Reservasi getReservasiById(Long id) {
        return reservasiRepository.findById(id)
                .orElseThrow(() -> new ReservasiGagalException(
                        "Reservasi dengan id " + id + " tidak ditemukan"));
    }

    /**
     * Membuat reservasi baru, lengkap dengan validasi bisnis:
     *   1. Tanggal check-out harus setelah tanggal check-in.
     *   2. Kuota kamar untuk tipe yang dipilih tidak boleh melebihi
     *      kapasitas (jika sudah penuh -> KamarPenuhException).
     *   3. Jika terjadi kegagalan teknis saat menyimpan ke database
     *      (misalnya koneksi database terputus), akan ditangkap dan
     *      dilempar ulang sebagai ReservasiGagalException yang lebih
     *      ramah untuk ditampilkan ke pengguna.
     */
    public Reservasi buatReservasi(Reservasi reservasi) {
        // 1. Validasi tanggal
        if (reservasi.getTanggalCheckOut() == null
                || reservasi.getTanggalCheckIn() == null
                || !reservasi.getTanggalCheckOut().isAfter(reservasi.getTanggalCheckIn())) {
            throw new ReservasiGagalException(
                    "Reservasi gagal: tanggal check-out harus setelah tanggal check-in");
        }

        // 2. Validasi ketersediaan kamar (cek kamar penuh)
        TipeKamar tipeKamar = reservasi.getTipeKamar();
        long jumlahTerpakai = reservasiRepository.countByTipeKamar(tipeKamar);
        if (jumlahTerpakai >= tipeKamar.getKuota()) {
            throw new KamarPenuhException(
                    "Reservasi gagal: kamar tipe " + tipeKamar.getLabel() +
                    " sudah penuh (kuota " + tipeKamar.getKuota() + " kamar telah terisi semua)");
        }

        // 3. Simpan ke database, tangani kemungkinan error teknis dari database
        try {
            return reservasiRepository.save(reservasi);
        } catch (DataAccessException ex) {
            throw new ReservasiGagalException(
                    "Reservasi gagal disimpan karena terjadi masalah pada database", ex);
        }
    }

    public void hapusReservasi(Long id) {
        if (!reservasiRepository.existsById(id)) {
            throw new ReservasiGagalException("Reservasi dengan id " + id + " tidak ditemukan");
        }
        reservasiRepository.deleteById(id);
    }

    /**
     * Menghitung sisa kuota kamar yang masih tersedia untuk setiap tipe kamar,
     * dipakai untuk ditampilkan di halaman form reservasi.
     */
    public int getSisaKuota(TipeKamar tipeKamar) {
        long terpakai = reservasiRepository.countByTipeKamar(tipeKamar);
        return (int) Math.max(0, tipeKamar.getKuota() - terpakai);
    }
}
