package com.della.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MahasiswaController {

    @Autowired
    private MahasiswaRepository repo;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("list", repo.findAll());
        return "index";
    }

    @GetMapping("/tambah")
    public String tambah(Model model) {
        model.addAttribute("mhs", new Mahasiswa());
        return "form";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Mahasiswa mhs) {
        repo.save(mhs);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("mhs", repo.findById(id).orElse(new Mahasiswa()));
        return "form";
    }

    @GetMapping("/hapus/{id}")
    public String hapus(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/";
    }
}