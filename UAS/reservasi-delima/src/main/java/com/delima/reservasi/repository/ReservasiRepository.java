package com.delima.reservasi.repository;

import com.delima.reservasi.model.Reservasi;
import com.delima.reservasi.model.TipeKamar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository untuk entity Reservasi.
 *
 * Dengan meng-extends JpaRepository, kita otomatis mendapatkan method-method
 * dasar CRUD (Create, Read, Update, Delete) yang sebenarnya di belakang
 * layar dijalankan oleh Spring Data JPA melalui JDBC ke database MySQL,
 * TANPA kita perlu menulis query SQL secara manual, contohnya:
 *   - save(reservasi)      -> INSERT / UPDATE
 *   - findAll()            -> SELECT * FROM reservasi
 *   - findById(id)         -> SELECT * FROM reservasi WHERE id = ?
 *   - deleteById(id)       -> DELETE FROM reservasi WHERE id = ?
 *
 * Method tambahan di bawah ini (countByTipeKamar) adalah contoh
 * "derived query method": Spring Data JPA otomatis membuatkan query
 * SQL-nya hanya berdasarkan nama method.
 */
public interface ReservasiRepository extends JpaRepository<Reservasi, Long> {

    // Menghitung berapa banyak reservasi aktif untuk 1 tipe kamar tertentu.
    // Dipakai untuk mengecek apakah kamar sudah penuh atau belum.
    long countByTipeKamar(TipeKamar tipeKamar);

    List<Reservasi> findAllByOrderByTanggalCheckInAsc();
}
