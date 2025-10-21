/*
Nama - NPM    : ~ Raissa Christabel Sebayang - 140810240008
                ~ Abraham Gomes Samosir - 140810240044
                ~ Haris Herdiansyah - 140810240074
Kelas         : B
Nama File     : Main.java
Deskripsi     : program inheritance dengan Java, C++ dan python masalah Parkir  di suatu pelabuhan:
                Class : 
                Waktu (date, time)   perhatikan kabisat
                Person/ pemilik, 
                Kendaraan  : (mobil, motor, truk), dll  --> inheritance
                Buat selengkap mungkin (Data array)
*/

import javax.xml.crypto.dsig.keyinfo.KeyName;
import java.util.*;
import java.time.*;

enum JenisKendaraan {
    MOBIL(15000, 30000),
    MOTOR(7000, 15000),
    TRUK(40000, 80000);

    private final int tarifPerJam, tarifMenginap;

    JenisKendaraan(int tarifPerJam, int tarifMenginap) {
        this.tarifPerJam = tarifPerJam;
        this.tarifMenginap = tarifMenginap;
    }

    public int getTarifPerJam() {
        return tarifPerJam;
    }

    public int getTarifMenginap() { return tarifMenginap; }
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

    @Override
    public String toString() {
        return "Time{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", seconds=" + seconds +
                '}';
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

    @Override
    public String toString() {
        return "Dates{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
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
    static final Scanner sc = new Scanner(System.in);
    static final List<Kendaraan> daftarKendaraan = new ArrayList<>();

    static LocalDateTime dateTimeParser(String dateTime) {
        String[] dt = dateTime.split(" ");
        String[] d = dt[0].split("/");
        String[] t = dt[1].split(":");
        return LocalDateTime.of(
                Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2]),
                Integer.parseInt(t[0]), Integer.parseInt(t[1]), Integer.parseInt(t[2])
        );
    }

    static Map<String, LocalDateTime> inputDateTime(int option) {
        Map<String, LocalDateTime> dateMap = new HashMap<>();

        // datetime sistem
        if (option == 1) {
            System.out.print("Waktu masuk (Tekan enter): "); sc.nextLine();
            dateMap.put("in", LocalDateTime.now());
            System.out.print("Waktu keluar (Tekan enter): "); sc.nextLine();
            dateMap.put("out", LocalDateTime.now());
        } else if (option == 2) { // datetime manual
            System.out.print("Waktu masuk (yyyy/MM/dd HH:mm:ss): ");
            dateMap.put("in", dateTimeParser(sc.nextLine()));
            System.out.print("Waktu keluar (yyyy/MM/dd HH:mm:ss): ");
            dateMap.put("out", dateTimeParser(sc.nextLine()));
        }

        return dateMap;
    }

    static Kendaraan inputObject() {
        System.out.print("Nama pemilik: ");
        String nama = sc.nextLine();
        System.out.print("Jenis kendaraan: ");
        JenisKendaraan jenisKendaraan = JenisKendaraan.valueOf(sc.nextLine().toUpperCase());
        System.out.print("Nomor kendaraan: ");
        String nomorKendaraan = sc.nextLine();
        System.out.print("Opsi masukkan waktu, (1) Sistem; (2) Manual: ");
        int option = Integer.parseInt(sc.nextLine());

        Map<String, LocalDateTime> userDateTime = inputDateTime(option);
        LocalDateTime timeIn = userDateTime.get("in");
        LocalDateTime timeOut = userDateTime.get("out");

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

    static LocalDateTime toLocalDateTime(Waktu w) {
        return LocalDateTime.of(
                w.getDates().getYear(), w.getDates().getMonth(), w.getDates().getDay(),
                w.getTime().getHour(), w.getTime().getMinute(), w.getTime().getSeconds()
        );
    }

    static int hitungBiayaParkir(Kendaraan k) {
        LocalDateTime waktuMasuk = toLocalDateTime(k.getWaktuMasuk());
        LocalDateTime waktuKeluar = toLocalDateTime(k.getWaktuKeluar());

        if (!waktuKeluar.toLocalDate().equals(waktuMasuk.toLocalDate())) {
            long selisihHari = java.time.temporal.ChronoUnit.DAYS.between(waktuMasuk.toLocalDate(), waktuKeluar.toLocalDate());
            return (int) selisihHari * k.getJenisKendaraan().getTarifMenginap();
        }

        Duration sel = Duration.between(waktuMasuk, waktuKeluar);
        long selisihDetik = sel.getSeconds();
        if (selisihDetik <= 0) return 0;

        int totalJam = (int) (selisihDetik / 3600);
        if (selisihDetik % 3600 > 0) totalJam++;

        return totalJam * k.getJenisKendaraan().getTarifPerJam();
    }

    static void outputTable(List<Kendaraan> list) {
        // Table header
    String fmtHeader = "%-4s | %-12s | %-20s | %-8s | %-19s | %-19s | %12s";
    String fmtRow = "%-4d | %-12s | %-20s | %-8s | %-19s | %-19s | %12d";

        System.out.println();
        System.out.println(String.format(fmtHeader, "No", "No Kendaraan", "Nama Pemilik", "Jenis", "Waktu Masuk", "Waktu Keluar", "Biaya Parkir"));
    System.out.println("----+--------------+----------------------+----------+---------------------+---------------------+--------------");

        long total = 0;
        for (int i = 0; i < list.size(); i++) {
            Kendaraan k = list.get(i);
            String waktuMasuk = toLocalDateTime(k.getWaktuMasuk()).toString().replace('T',' ');
            String waktuKeluar = toLocalDateTime(k.getWaktuKeluar()).toString().replace('T',' ');
            int biaya = hitungBiayaParkir(k);
            total += biaya;

            System.out.println(String.format(fmtRow, i + 1, k.getNoKendaraan(), k.getPemilik().getName(), k.getJenisKendaraan(), waktuMasuk, waktuKeluar, biaya));
        }

        // Total row
        System.out.println("----+--------------+----------------------+----------+---------------------+---------------------+--------------");
        System.out.println(String.format("%-66s %12d", "Total Biaya:", total));
    }

    public static void main(String[] args) {
        System.out.print("Jumlah kendaraan: ");
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 1; i <= n ; i++) {
            System.out.println("\nKendaraan ke-" + i);
            daftarKendaraan.add(inputObject());
        }

        outputTable(daftarKendaraan);
    }
}
