import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

enum JenisKendaraan {
    MOBIL(15000),
    MOTOR(7000),
    TRUK(40000);

    private int tarifPerJam;

    JenisKendaraan(int tarifPerJam) {
        this.tarifPerJam = tarifPerJam;
    }

    public int getTarifPerJam() {
        return tarifPerJam;
    }

    public void setTarifPerJam(int tarifPerJam) {
        this.tarifPerJam = tarifPerJam;
    }
}

class Time {
    private int hour, minute, seconds;

    public Time(int hour, int minute, int seconds) {
        this.hour = hour;
        this.minute = minute;
        this.seconds = seconds;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}

class Dates {
    private int year, month, day;

    public Dates(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}

class Waktu {
    private Dates dates;
    private Time time;

    public Waktu(Dates dates, Time time) {
        this.dates = dates;
        this.time = time;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}

class Pemilik {
    private String name;

    public Pemilik(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

abstract class Kendaraan {
    JenisKendaraan jenisKendaraan;
    Pemilik pemilik;
    Waktu waktuMasuk, waktuKeluar;
    String noKendaraan;

    public Kendaraan(JenisKendaraan jenisKendaraan, Pemilik pemilik, Waktu waktuMasuk, Waktu waktuKeluar, String noKendaraan) {
        this.jenisKendaraan = jenisKendaraan;
        this.pemilik = pemilik;
        this.waktuMasuk = waktuMasuk;
        this.waktuKeluar = waktuKeluar;
        this.noKendaraan = noKendaraan;
    }

    public JenisKendaraan getJenisKendaraan() {
        return jenisKendaraan;
    }

    public void setJenisKendaraan(JenisKendaraan jenisKendaraan) {
        this.jenisKendaraan = jenisKendaraan;
    }

    public Pemilik getPemilik() {
        return pemilik;
    }

    public void setPemilik(Pemilik pemilik) {
        this.pemilik = pemilik;
    }

    public Waktu getWaktuMasuk() {
        return waktuMasuk;
    }

    public void setWaktuMasuk(Waktu waktuMasuk) {
        this.waktuMasuk = waktuMasuk;
    }

    public Waktu getWaktuKeluar() {
        return waktuKeluar;
    }

    public void setWaktuKeluar(Waktu waktuKeluar) {
        this.waktuKeluar = waktuKeluar;
    }

    public String getNoKendaraan() {
        return noKendaraan;
    }

    public void setNoKendaraan(String noKendaraan) {
        this.noKendaraan = noKendaraan;
    }
}

class Mobil extends Kendaraan {
    public Mobil(JenisKendaraan jenisKendaraan, Pemilik pemilik, Waktu waktuMasuk, Waktu waktuKeluar, String noKendaraan) {
        super(jenisKendaraan, pemilik, waktuMasuk, waktuKeluar, noKendaraan);
    }
}

class Motor extends Kendaraan {
    public Motor(JenisKendaraan jenisKendaraan, Pemilik pemilik, Waktu waktuMasuk, Waktu waktuKeluar, String noKendaraan) {
        super(jenisKendaraan, pemilik, waktuMasuk, waktuKeluar, noKendaraan);
    }
}

class Truk extends Kendaraan {
    public Truk(JenisKendaraan jenisKendaraan, Pemilik pemilik, Waktu waktuMasuk, Waktu waktuKeluar, String noKendaraan) {
        super(jenisKendaraan, pemilik, waktuMasuk, waktuKeluar, noKendaraan);
    }
}

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final List<Kendaraan> daftarKendaraan = new ArrayList<>();

    static Kendaraan inputObject() {
        System.out.print("Nama pemilik: ");
        String nama = sc.nextLine();
        System.out.print("Jenis kendaraan: ");
        JenisKendaraan jenisKendaraan = JenisKendaraan.valueOf(sc.nextLine().toUpperCase());
        System.out.print("Nomor kendaraan: ");
        String nomorKendaraan = sc.nextLine();
        System.out.print("Waktu masuk (Tekan enter): ");
        sc.nextLine();
        LocalDateTime timeIn = LocalDateTime.now();
        System.out.print("Waktu keluar (Tekan enter): ");
        sc.nextLine();
        LocalDateTime timeOut = LocalDateTime.now();

        Pemilik pemilik = new Pemilik(nama);
        Waktu waktuMasuk = new Waktu(new Dates(timeIn.getYear(), timeIn.getMonthValue(), timeIn.getDayOfMonth()),
                new Time(timeIn.getHour(), timeIn.getMinute(), timeIn.getSecond()));
        Waktu waktuKeluar = new Waktu(new Dates(timeOut.getYear(), timeOut.getMonthValue(), timeOut.getDayOfMonth()),
                new Time(timeOut.getHour(), timeOut.getMinute(), timeOut.getSecond()));

        return switch (jenisKendaraan) {
            case JenisKendaraan.MOBIL -> new Mobil(jenisKendaraan, pemilik, waktuMasuk, waktuKeluar, nomorKendaraan);
            case JenisKendaraan.MOTOR -> new Motor(jenisKendaraan, pemilik, waktuMasuk, waktuKeluar, nomorKendaraan);
            case JenisKendaraan.TRUK -> new Truk(jenisKendaraan, pemilik, waktuMasuk, waktuKeluar, nomorKendaraan);
        };
    }

    static Duration selisih(Waktu m, Waktu k) {
        LocalDateTime masuk = LocalDateTime.of(
                m.getDates().getYear(), m.getDates().getMonth(), m.getDates().getDay(),
                m.getTime().getHour(), m.getTime().getMinute(), m.getTime().getSeconds()
        );
        LocalDateTime keluar = LocalDateTime.of(
                k.getDates().getYear(), k.getDates().getMonth(), k.getDates().getDay(),
                k.getTime().getHour(), k.getTime().getMinute(), k.getTime().getSeconds()
        );
        return Duration.between(masuk, keluar);
    }

    static int hitungBiayaParkir(Kendaraan k) {
        Duration sel = selisih(k.getWaktuMasuk(), k.getWaktuKeluar());
        long selisihDetik = sel.getSeconds();
        if (selisihDetik <= 0) return 0;

        int totalJam = (int) (selisihDetik / 3600);
        if (selisihDetik % 3600 > 0) totalJam++;

        return totalJam * k.getJenisKendaraan().getTarifPerJam();
    }

    static void main() {
        System.out.print("Jumlah kendaraan: ");
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 1; i <= n ; i++) {
            System.out.println("\nKendaraan ke-" + i);
            daftarKendaraan.add(inputObject());
        }
    }
}
