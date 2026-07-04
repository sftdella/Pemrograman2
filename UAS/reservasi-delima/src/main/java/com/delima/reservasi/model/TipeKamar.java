package com.delima.reservasi.model;

/**
 * Enum TipeKamar merepresentasikan jenis-jenis kamar yang tersedia
 * di hotel, lengkap dengan kuota (jumlah kamar tersedia) untuk masing-masing
 * tipe. Kuota ini dipakai oleh ReservasiService untuk mengecek apakah
 * kamar masih tersedia atau sudah penuh saat ada permintaan reservasi baru.
 */
public enum TipeKamar {
    STANDARD("Standard", 5, 350000),
    DELUXE("Deluxe", 3, 550000),
    SUITE("Suite", 2, 950000);

    private final String label;
    private final int kuota;
    private final long hargaPerMalam;

    TipeKamar(String label, int kuota, long hargaPerMalam) {
        this.label = label;
        this.kuota = kuota;
        this.hargaPerMalam = hargaPerMalam;
    }

    public String getLabel() {
        return label;
    }

    public int getKuota() {
        return kuota;
    }

    public long getHargaPerMalam() {
        return hargaPerMalam;
    }
}
