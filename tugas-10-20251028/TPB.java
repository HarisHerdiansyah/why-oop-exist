import java.util.Scanner;

import com.example.waktu.*;
import com.example.mahasiswa.*;
import com.example.linkedlist.*;

public class TPB {
    private static Scanner scanner = new Scanner(System.in);
    private static LinkedList list = new LinkedList();
    
    public static void main(String[] args) {
        System.out.println("=== PROGRAM MANAJEMEN DATA MAHASISWA TPB ===");
        
        // Menu utama
        int pilihan;
        do {
            tampilkanMenu();
            System.out.print("Pilih menu (1-8): ");
            pilihan = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (pilihan) {
                case 1:
                    tambahDiAwal();
                    break;
                case 2:
                    tambahDiAkhir();
                    break;
                case 3:
                    tambahDiPosisi();
                    break;
                case 4:
                    hapusPertama();
                    break;
                case 5:
                    hapusTerakhir();
                    break;
                case 6:
                    cariData();
                    break;
                case 7:
                    tampilkanDenganNilai();
                    break;
                case 8:
                    System.out.println("Terima kasih telah menggunakan program!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 8);
    }
    
    private static void tampilkanMenu() {
        System.out.println("\n=== MENU UTAMA ===");
        System.out.println("1. Insert First");
        System.out.println("2. Insert Last");
        System.out.println("3. Insert After");
        System.out.println("4. Delete First");
        System.out.println("5. Delete Last");
        System.out.println("6. Searching (NPM)");
        System.out.println("7. Traversal");
        System.out.println("8. Keluar");
    }
    
    private static void tambahDiAwal() {
        System.out.println("\n=== TAMBAH DATA DI AWAL ===");
        MhsTPB mhs = inputDataMahasiswa();
        list.prepend(mhs);
        System.out.println("Data berhasil ditambahkan di awal!");
    }
    
    private static void tambahDiAkhir() {
        System.out.println("\n=== TAMBAH DATA DI AKHIR ===");
        MhsTPB mhs = inputDataMahasiswa();
        list.append(mhs);
        System.out.println("Data berhasil ditambahkan di akhir!");
    }
    
    private static void tambahDiPosisi() {
        System.out.println("\n=== TAMBAH DATA DI POSISI TERTENTU ===");
        
        System.out.print("Masukkan posisi (index dimulai dari 0): ");
        int posisi = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        MhsTPB mhs = inputDataMahasiswa();
        list.insert(mhs, posisi);
        System.out.println("Data berhasil ditambahkan di posisi " + posisi + "!");
    }
    
    private static MhsTPB inputDataMahasiswa() {
        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine();
        
        System.out.print("Masukkan NPM: ");
        String npm = scanner.nextLine();
        
        System.out.print("Masukkan Waktu Mulai (format HH:MM:SS): ");
        Time waktuMulai = inputWaktu();
        
        System.out.print("Masukkan Waktu Selesai (format HH:MM:SS): ");
        Time waktuSelesai = inputWaktu();
        
        return new MhsTPB(nama, npm, waktuMulai, waktuSelesai);
    }
    
    private static Time inputWaktu() {
        while (true) {
            try {
                String waktuStr = scanner.nextLine();
                String[] parts = waktuStr.split(":");
                
                if (parts.length != 3) {
                    System.out.print("Format salah! Gunakan HH:MM:SS: ");
                    continue;
                }
                
                int hour = Integer.parseInt(parts[0]);
                int minute = Integer.parseInt(parts[1]);
                int second = Integer.parseInt(parts[2]);
                
                // Validasi range
                if (hour < 0 || hour > 23 || minute < 0 || minute > 59 || second < 0 || second > 59) {
                    System.out.print("Waktu tidak valid! Masukkan lagi (HH:MM:SS): ");
                    continue;
                }
                
                return new Time(hour, minute, second);
            } catch (NumberFormatException e) {
                System.out.print("Input harus angka! Masukkan lagi (HH:MM:SS): ");
            }
        }
    }
    
    private static void hapusPertama() {
        System.out.println("\n=== HAPUS DATA PERTAMA ===");
        if (list.getHead() == null) {
            System.out.println("Linked list kosong, tidak ada data yang bisa dihapus!");
        } else {
            list.deleteFirst();
            System.out.println("Data pertama berhasil dihapus!");
        }
    }
    
    private static void hapusTerakhir() {
        System.out.println("\n=== HAPUS DATA TERAKHIR ===");
        if (list.getHead() == null) {
            System.out.println("Linked list kosong, tidak ada data yang bisa dihapus!");
        } else {
            list.deleteLast();
            System.out.println("Data terakhir berhasil dihapus!");
        }
    }
    
    private static void cariData() {
        System.out.println("\n=== CARI DATA BERDASARKAN NPM ===");
        System.out.print("Masukkan NPM yang dicari: ");
        String npm = scanner.nextLine();
        
        Node hasil = list.find(npm);
        if (hasil != null) {
            MhsTPB mhs = hasil.getMhs();
            double totalDetik = mhs.getWaktuMulai().hitungSelisih(mhs.getWaktuSelesai());
            int menit = (int) (totalDetik / 60);
            int detik = (int) (totalDetik % 60);
            String hurufMutu = mhs.getHurufMutu();
            String status = mhs.getStatus();
            
            System.out.println("\nData ditemukan:");
            System.out.println("Nama          : " + mhs.getNama());
            System.out.println("NPM           : " + mhs.getNpm());
            System.out.println("Waktu Mulai   : " + formatTime(mhs.getWaktuMulai()));
            System.out.println("Waktu Selesai : " + formatTime(mhs.getWaktuSelesai()));
            System.out.println("Lama          : " + menit + " menit " + detik + " detik");
            System.out.println("Huruf Mutu    : " + hurufMutu);
            System.out.println("Status        : " + status);
        } else {
            System.out.println("Data dengan NPM " + npm + " tidak ditemukan!");
        }
    }
    
    private static void tampilkanDenganNilai() {
        System.out.println("\n=== DATA MAHASISWA DENGAN NILAI ===");
        if (list.getHead() == null) {
            System.out.println("Tidak ada data mahasiswa.");
        } else {
            list.tampilkanDenganNilai();
        }
    }
    
    private static String formatTime(Time time) {
        return String.format("%02d:%02d:%02d", 
            time.getHour(), time.getMinute(), time.getSecond());
    }
}