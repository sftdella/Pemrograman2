package com.delima.reservasi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Entity/Model Reservasi.
 *
 * Ini adalah inti dari konsep OOP (Object Oriented Programming) pada
 * aplikasi ini: setiap reservasi direpresentasikan sebagai sebuah OBJEK
 * dari class Reservasi, dengan ATRIBUT:
 *   - id            : identitas unik reservasi (primary key di database)
 *   - namaTamu      : nama tamu yang memesan kamar
 *   - tipeKamar     : jenis kamar yang dipesan (Standard, Deluxe, Suite)
 *   - tanggalCheckIn  : tanggal mulai menginap
 *   - tanggalCheckOut : tanggal selesai menginap
 *
 * Anotasi @Entity membuat class ini otomatis dipetakan (mapping) oleh
 * Spring Data JPA/Hibernate menjadi sebuah tabel di database MySQL
 * bernama "reservasi". Ini menggantikan penulisan query SQL CREATE TABLE
 * secara manual (JPA akan membuatkan tabelnya secara otomatis).
 */
@Entity
@Table(name = "reservasi")
public class Reservasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nama tamu wajib diisi")
    @Column(name = "nama_tamu", nullable = false, length = 100)
    private String namaTamu;

    @NotNull(message = "Tipe kamar wajib dipilih")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipe_kamar", nullable = false, length = 20)
    private TipeKamar tipeKamar;

    @NotNull(message = "Tanggal check-in wajib diisi")
    @FutureOrPresent(message = "Tanggal check-in tidak boleh di masa lalu")
    @Column(name = "tanggal_check_in", nullable = false)
    private LocalDate tanggalCheckIn;

    @NotNull(message = "Tanggal check-out wajib diisi")
    @Column(name = "tanggal_check_out", nullable = false)
    private LocalDate tanggalCheckOut;

    public Reservasi() {
        // Constructor kosong dibutuhkan oleh JPA/Hibernate
    }

    public Reservasi(String namaTamu, TipeKamar tipeKamar,
                      LocalDate tanggalCheckIn, LocalDate tanggalCheckOut) {
        this.namaTamu = namaTamu;
        this.tipeKamar = tipeKamar;
        this.tanggalCheckIn = tanggalCheckIn;
        this.tanggalCheckOut = tanggalCheckOut;
    }

    /**
     * Method milik object ini sendiri (behaviour OOP): menghitung
     * berapa lama malam menginap berdasarkan tanggal check-in & check-out.
     */
    public long hitungLamaMenginap() {
        if (tanggalCheckIn == null || tanggalCheckOut == null) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(tanggalCheckIn, tanggalCheckOut);
    }

    // ===== Getter & Setter =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaTamu() {
        return namaTamu;
    }

    public void setNamaTamu(String namaTamu) {
        this.namaTamu = namaTamu;
    }

    public TipeKamar getTipeKamar() {
        return tipeKamar;
    }

    public void setTipeKamar(TipeKamar tipeKamar) {
        this.tipeKamar = tipeKamar;
    }

    public LocalDate getTanggalCheckIn() {
        return tanggalCheckIn;
    }

    public void setTanggalCheckIn(LocalDate tanggalCheckIn) {
        this.tanggalCheckIn = tanggalCheckIn;
    }

    public LocalDate getTanggalCheckOut() {
        return tanggalCheckOut;
    }

    public void setTanggalCheckOut(LocalDate tanggalCheckOut) {
        this.tanggalCheckOut = tanggalCheckOut;
    }
}
