/*
Nama - NPM    : ~ Raissa Christabel Sebayang - 140810240008
                ~ Abraham Gomes Samosir - 140810240044
                ~ Haris Herdiansyah - 140810240074
Kelas         : B
Nama File     : Time.java
Deskripsi     : class waktu
*/


package com.example.waktu;

public class Time {
    private int hour, minute, second;
    
    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }
    
    public int getHour() { return hour; }
    public void setHour(int hour) { this.hour = hour; }
    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }
    public int getSecond() { return second; }
    public void setSecond(int second) { this.second = second; }
    
    public double hitungSelisih(Time waktuSelesai) {
        int totalDetikMulai = this.hour * 3600 + this.minute * 60 + this.second;
        int totalDetikSelesai = waktuSelesai.hour * 3600 + waktuSelesai.minute * 60 + waktuSelesai.second;
        
        int selisihDetik = totalDetikSelesai - totalDetikMulai;
        
        if (selisihDetik < 0) {
            selisihDetik += 24 * 3600;
        }
        
        return selisihDetik;
    }
}
