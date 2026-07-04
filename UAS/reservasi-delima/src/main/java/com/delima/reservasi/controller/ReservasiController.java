package com.delima.reservasi.controller;

import com.delima.reservasi.exception.KamarPenuhException;
import com.delima.reservasi.exception.ReservasiGagalException;
import com.delima.reservasi.model.Reservasi;
import com.delima.reservasi.model.TipeKamar;
import com.delima.reservasi.service.ReservasiService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller ini adalah "pintu masuk" dari web browser (input pengguna)
 * ke aplikasi. Setiap method di sini menangani satu alamat (endpoint) URL,
 * memanggil ReservasiService untuk memproses data, lalu menentukan halaman
 * Thymeleaf (output/tampilan) mana yang akan ditampilkan kembali ke pengguna.
 *
 * Alur INPUT -> PROSES -> OUTPUT:
 *   INPUT  : Data dari form HTML (nama tamu, tipe kamar, tanggal check-in/out)
 *   PROSES : Dikirim ke ReservasiService.buatReservasi() untuk divalidasi & disimpan
 *   OUTPUT : Halaman daftar reservasi (index.html) atau pesan error jika gagal
 */
@Controller
@RequestMapping("/")
public class ReservasiController {

    private final ReservasiService reservasiService;

    public ReservasiController(ReservasiService reservasiService) {
        this.reservasiService = reservasiService;
    }

    // Menampilkan daftar seluruh reservasi (OUTPUT utama aplikasi)
    @GetMapping
    public String daftarReservasi(Model model) {
        model.addAttribute("daftarReservasi", reservasiService.getSemuaReservasi());
        return "index";
    }

    // Menampilkan form untuk membuat reservasi baru (INPUT)
    @GetMapping("/reservasi/baru")
    public String formReservasiBaru(Model model) {
        model.addAttribute("reservasi", new Reservasi());
        model.addAttribute("semuaTipeKamar", TipeKamar.values());
        return "form";
    }

    // Memproses submit form reservasi baru
    @PostMapping("/reservasi/simpan")
    public String simpanReservasi(@Valid @ModelAttribute("reservasi") Reservasi reservasi,
                                   BindingResult bindingResult,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {

        // Validasi otomatis dari anotasi @NotBlank, @NotNull, dsb pada entity Reservasi
        if (bindingResult.hasErrors()) {
            model.addAttribute("semuaTipeKamar", TipeKamar.values());
            return "form";
        }

        try {
            reservasiService.buatReservasi(reservasi);
            redirectAttributes.addFlashAttribute("pesanSukses",
                    "Reservasi atas nama " + reservasi.getNamaTamu() + " berhasil dibuat!");
            return "redirect:/";

        } catch (KamarPenuhException | ReservasiGagalException ex) {
            // Exception Handling: pesan error ditampilkan kembali di form,
            // pengguna tidak perlu mengisi ulang data dari awal.
            model.addAttribute("errorPesan", ex.getMessage());
            model.addAttribute("semuaTipeKamar", TipeKamar.values());
            return "form";
        }
    }

    // Menghapus reservasi
    @PostMapping("/reservasi/hapus/{id}")
    public String hapusReservasi(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reservasiService.hapusReservasi(id);
            redirectAttributes.addFlashAttribute("pesanSukses", "Reservasi berhasil dihapus");
        } catch (ReservasiGagalException ex) {
            redirectAttributes.addFlashAttribute("errorPesan", ex.getMessage());
        }
        return "redirect:/";
    }
}
