# 🌸 Reservasi Delima

Aplikasi Reservasi Hotel sederhana — UAS Pemrograman II
Dibuat dengan **Spring Boot + Thymeleaf + Spring Data JPA (JDBC) + MySQL**, tema **Soft Pink Cute**.

## Fitur

- Melihat daftar reservasi (nama tamu, tipe kamar, tanggal check-in/out, lama menginap)
- Membuat reservasi baru lewat form
- Menghapus reservasi
- Validasi input (nama wajib diisi, tanggal wajib diisi, dst)
- Exception handling: kamar penuh (kuota tiap tipe kamar terbatas) & tanggal tidak valid
- Desain OOP: Entity `Reservasi`, enum `TipeKamar`, layering Controller–Service–Repository

## Struktur Folder

```
reservasi-delima/
├── pom.xml
└── src/main/
    ├── java/com/delima/reservasi/
    │   ├── ReservasiDelimaApplication.java   -> class utama (main)
    │   ├── model/
    │   │   ├── Reservasi.java                -> entity/OOP class
    │   │   └── TipeKamar.java                -> enum tipe kamar + kuota
    │   ├── repository/
    │   │   └── ReservasiRepository.java      -> akses database (JPA/JDBC)
    │   ├── service/
    │   │   └── ReservasiService.java         -> logika bisnis + exception handling
    │   ├── controller/
    │   │   └── ReservasiController.java      -> menangani request web (input/output)
    │   └── exception/
    │       ├── KamarPenuhException.java
    │       ├── ReservasiGagalException.java
    │       └── GlobalExceptionHandler.java
    └── resources/
        ├── application.properties            -> konfigurasi database MySQL
        ├── templates/                        -> halaman Thymeleaf (index, form, error)
        └── static/css/style.css              -> tema Soft Pink Cute
```

## Cara Menjalankan di NetBeans

1. Pastikan **MySQL Server** sudah berjalan di komputer Anda (bisa lewat XAMPP/Laragon/MySQL Workbench).
2. Buat database (opsional, karena `createDatabaseIfNotExist=true` sudah diaktifkan):
   ```sql
   CREATE DATABASE db_reservasi_delima;
   ```
3. Buka folder `reservasi-delima` ini di **NetBeans** (`File > Open Project`, pilih project Maven).
4. Sesuaikan `src/main/resources/application.properties` dengan username & password MySQL Anda:
   ```
   spring.datasource.username=root
   spring.datasource.password=
   ```
5. Klik kanan pada project → **Run**. Atau jalankan lewat terminal:
   ```
   mvn spring-boot:run
   ```
6. Buka browser ke: **http://localhost:8080/**

Tabel `reservasi` akan dibuat secara otomatis oleh Hibernate/JPA saat aplikasi pertama kali dijalankan (`spring.jpa.hibernate.ddl-auto=update`), jadi tidak perlu membuat tabel secara manual.

## Alur Input & Output Aplikasi

1. **Input**: Pengguna membuka `/reservasi/baru`, mengisi nama tamu, memilih tipe kamar, tanggal check-in & check-out, lalu klik **Simpan Reservasi**.
2. **Proses**: Data dikirim ke `ReservasiController.simpanReservasi()` → divalidasi → diteruskan ke `ReservasiService.buatReservasi()`:
   - Jika tanggal check-out tidak lebih besar dari check-in → `ReservasiGagalException`.
   - Jika kuota kamar untuk tipe tersebut sudah penuh → `KamarPenuhException`.
   - Jika valid → data disimpan ke MySQL melalui `ReservasiRepository` (JPA/JDBC).
3. **Output**: Jika berhasil, pengguna diarahkan kembali ke halaman utama (`/`) dengan pesan sukses dan data baru langsung tampil di tabel. Jika gagal, pesan error ditampilkan langsung di form tanpa kehilangan data yang sudah diisi.

## Kuota Kamar (contoh data untuk testing "kamar penuh")

| Tipe Kamar | Kuota  | Harga / malam |
|------------|--------|---------------|
| Standard   | 5 kamar | Rp 350.000   |
| Deluxe     | 3 kamar | Rp 550.000   |
| Suite      | 2 kamar | Rp 950.000   |

Untuk menguji exception `KamarPenuhException`, buat reservasi Suite sebanyak 3 kali — reservasi ketiga akan gagal dengan pesan "kamar sudah penuh".
