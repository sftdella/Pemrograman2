package com.delima.reservasi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Kelas utama untuk menjalankan aplikasi Reservasi Delima.
 *
 * Aplikasi ini adalah sistem reservasi hotel sederhana yang dibuat
 * menggunakan Spring Boot + Thymeleaf (tampilan web) dan
 * Spring Data JPA/JDBC + MySQL (penyimpanan data).
 *
 * Cara menjalankan:
 * 1. Pastikan MySQL sudah berjalan dan database "db_reservasi_delima" sudah dibuat.
 * 2. Sesuaikan username/password MySQL di application.properties.
 * 3. Jalankan aplikasi ini (klik kanan -> Run) di NetBeans, atau
 *    jalankan perintah: mvn spring-boot:run
 * 4. Buka browser ke http://localhost:8080/
 */
@SpringBootApplication
public class ReservasiDelimaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservasiDelimaApplication.class, args);
    }
}
