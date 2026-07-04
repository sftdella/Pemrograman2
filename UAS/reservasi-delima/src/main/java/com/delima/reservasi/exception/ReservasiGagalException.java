package com.delima.reservasi.exception;

/**
 * Exception umum yang dilempar ketika proses reservasi gagal
 * karena alasan selain kamar penuh, misalnya:
 *   - tanggal check-out lebih awal/sama dengan tanggal check-in
 *   - data yang dikirim tidak valid
 *   - terjadi kegagalan saat menyimpan ke database
 */
public class ReservasiGagalException extends RuntimeException {

    public ReservasiGagalException(String message) {
        super(message);
    }

    public ReservasiGagalException(String message, Throwable cause) {
        super(message, cause);
    }
}
