package com.delima.reservasi.exception;

/**
 * Exception khusus (custom exception) yang dilempar (throw) ketika
 * kuota kamar untuk tipe tertentu sudah penuh, sehingga reservasi
 * baru tidak bisa diproses.
 *
 * Ini adalah bagian dari requirement "Exception Handling: Tangani error
 * jika reservasi gagal dilakukan atau kamar penuh".
 */
public class KamarPenuhException extends RuntimeException {

    public KamarPenuhException(String message) {
        super(message);
    }
}
