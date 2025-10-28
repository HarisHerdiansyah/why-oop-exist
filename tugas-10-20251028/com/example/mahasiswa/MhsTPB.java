/*
Nama - NPM    : ~ Raissa Christabel Sebayang - 140810240008
                ~ Abraham Gomes Samosir - 140810240044
                ~ Haris Herdiansyah - 140810240074
Kelas         : B
Nama File     : MhsTPB.java
Deskripsi     : class Mahasiswa
*/

package com.example.mahasiswa;

import com.example.waktu.*;

public class MhsTPB {
    private String nama;
    private String npm;
    private Time waktuMulai;
    private Time waktuSelesai;

    public MhsTPB(String nama, String npm, Time waktuMulai, Time waktuSelesai) {
        this.nama = nama;
        this.npm = npm;
        this.waktuMulai = waktuMulai;
        this.waktuSelesai = waktuSelesai;
    }

    public String getNama() { return nama; }
    public String getNpm() { return npm; }
    public Time getWaktuMulai() { return waktuMulai; }
    public Time getWaktuSelesai() { return waktuSelesai; }
    public void setNama(String nama) { this.nama = nama; }
    public void setNpm(String npm) { this.npm = npm; }
    public void setWaktuMulai(Time waktuMulai) { this.waktuMulai = waktuMulai; }
    public void setWaktuSelesai(Time waktuSelesai) { this.waktuSelesai = waktuSelesai; }
    
    // Method untuk menghitung lama pengerjaan dalam menit
    public double hitungLamaMenit() {
        return waktuMulai.hitungSelisih(waktuSelesai) / 60.0;
    }
    
    // Method untuk menentukan huruf mutu
    public String getHurufMutu() {
        double lama = hitungLamaMenit();
        
        if (lama >= 0 && lama < 7.5) {
            return "A";
        } else if (lama >= 7.5 && lama < 12.5) {
            return "B";
        } else if (lama >= 12.5 && lama < 30) {
            return "C";
        } else if (lama >= 30) {
            return "D";
        } else {
            return "Tidak Valid";
        }
    }
    
    // Method untuk menentukan status
    public String getStatus() {
        String hurufMutu = getHurufMutu();
        
        if (hurufMutu.equals("A") || hurufMutu.equals("B") || hurufMutu.equals("C")) {
            return "Lulus";
        } else if (hurufMutu.equals("D")) {
            return "Gagal";
        } else {
            return "Tidak Valid";
        }
    }
}
