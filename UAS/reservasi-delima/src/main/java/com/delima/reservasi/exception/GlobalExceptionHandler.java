package com.delima.reservasi.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Penangan error secara GLOBAL untuk seluruh aplikasi.
 *
 * Jika ada exception yang TIDAK DITANGKAP secara khusus di dalam Controller
 * (misalnya error tak terduga lain di luar KamarPenuhException /
 * ReservasiGagalException), maka class inilah yang akan menangkapnya
 * sehingga pengguna tidak melihat halaman error mentah bawaan Spring Boot
 * (whitelabel error page), melainkan halaman error yang rapi.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String tanganiErrorUmum(Exception ex, Model model) {
        model.addAttribute("errorPesan", "Terjadi kesalahan tak terduga: " + ex.getMessage());
        return "error";
    }
}
