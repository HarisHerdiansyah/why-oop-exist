import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

enum JenisKendaraan {MOTOR, MOBIL}

enum Status {REGULAR, MENGINAP}

class Time {
    private int hour, minute, second;

    public Time() {
    }

    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
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

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}

class Date {
    private int year, month, day;

    public Date() {
    }

    public Date(int year, int month, int day) {
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
        return String.format("%04d/%02d/%02d", year, month, day);
    }
}

class Waktu {
    private Time time;
    private Date date;

    public Waktu() {
    }

    public Waktu(Time time, Date date) {
        this.time = time;
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

class WaktuHelper {
    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private static int daysInMonth(int year, int month) {
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2 && isLeapYear(year)) return 29;
        return days[month - 1];
    }

    public static Waktu calculateTimeDifference(Waktu datang, Waktu pulang) {
        Date d1 = datang.getDate();
        Time t1 = datang.getTime();
        Date d2 = pulang.getDate();
        Time t2 = pulang.getTime();

        int yearD = d1.getYear(), monthD = d1.getMonth(), dayD = d1.getDay();
        int hourD = t1.getHour(), minuteD = t1.getMinute(), secondD = t1.getSecond();

        int yearP = d2.getYear(), monthP = d2.getMonth(), dayP = d2.getDay();
        int hourP = t2.getHour(), minuteP = t2.getMinute(), secondP = t2.getSecond();

        // 1. Seconds
        if (secondP < secondD) {
            secondP += 60;
            minuteP -= 1;
        }
        int secondDiff = secondP - secondD;

        // 2. Minutes
        if (minuteP < minuteD) {
            minuteP += 60;
            hourP -= 1;
        }
        int minuteDiff = minuteP - minuteD;

        // 3. Hours
        if (hourP < hourD) {
            hourP += 24;
            dayP -= 1;
        }
        int hourDiff = hourP - hourD;

        // 4. Days
        if (dayP < dayD) {
            monthP -= 1;
            if (monthP < 1) {
                monthP = 12;
                yearP -= 1;
            }
            dayP += daysInMonth(yearP, monthP);
        }
        int dayDiff = dayP - dayD;

        // 5. Months
        if (monthP < monthD) {
            monthP += 12;
            yearP -= 1;
        }
        int monthDiff = monthP - monthD;

        // 6. Years
        int yearDiff = yearP - yearD;

        return new Waktu(new Time(hourDiff, minuteDiff, secondDiff), new Date(yearDiff, monthDiff, dayDiff));
    }
}

class ParkedVehicle {
    private final String noKendaraan;
    private final JenisKendaraan kendaraan;
    private final Status status;
    private final Waktu datang, pulang;

    public ParkedVehicle(String noKendaraan, JenisKendaraan kendaraan, Status status, Waktu datang, Waktu pulang) {
        this.noKendaraan = noKendaraan;
        this.kendaraan = kendaraan;
        this.status = status;
        this.datang = datang;
        this.pulang = pulang;
    }

    public String getNoKendaraan() {
        return noKendaraan;
    }

    public JenisKendaraan getKendaraan() {
        return kendaraan;
    }

    public Status getStatus() {
        return status;
    }

    public Waktu getDatang() {
        return datang;
    }

    public Waktu getPulang() {
        return pulang;
    }
}

class ParkedVehicleHelper {
    public static int getPay(ParkedVehicle vehicle, int totalJam, int totalHari) {
        int multiplier = 0;
        int pay = 0;

        if (vehicle.getStatus() == Status.MENGINAP) {
            multiplier = (vehicle.getKendaraan() == JenisKendaraan.MOTOR) ? 15000 : 25000;
            pay = multiplier * totalHari;
        } else {
            multiplier = (vehicle.getKendaraan() == JenisKendaraan.MOTOR) ? 2000 : 3000;
            pay = multiplier * totalJam;
        }
        return pay;
    }
}

public class Soal3 {
    private static final Scanner SC = new Scanner(System.in);
    private static final List<ParkedVehicle> parkedVehicles = new ArrayList<>();
    private static int totalPay = 0;

    private static Time parseTime(String s) {
        String[] p = s.split(":");
        return new Time(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]));
    }

    private static Date parseDate(String s) {
        String[] p = s.split("/");
        return new Date(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]));
    }

    private static void printVehicleSummary(ParkedVehicle pv, Waktu diff, int totalHari, int totalJam, int pay) {
        System.out.println("\n--- RINCIAN KENDARAAN ---");
        System.out.println("Nomor Kendaraan : " + pv.getNoKendaraan());
        System.out.println("Jenis Kendaraan : " + pv.getKendaraan());
        System.out.println("Status Parkir   : " + pv.getStatus());
        System.out.println("Tanggal Datang  : " + pv.getDatang().getDate());
        System.out.println("Tanggal Pulang  : " + pv.getPulang().getDate());
        System.out.println("Jam Datang      : " + pv.getDatang().getTime());
        System.out.println("Jam Pulang      : " + pv.getPulang().getTime());
        System.out.println("Selisih Tanggal : " + diff.getDate());
        System.out.println("Selisih Waktu   : " + diff.getTime());
        System.out.println("Total Hari      : " + totalHari);
        System.out.println("Total Jam       : " + totalJam);
        System.out.println("Biaya           : " + pay);
    }

    private static void calculateAndPrint() {
        for (ParkedVehicle pv : parkedVehicles) {
            Waktu diff = WaktuHelper.calculateTimeDifference(pv.getDatang(), pv.getPulang());
            Time timeDiff = diff.getTime();
            Date dateDiff = diff.getDate();

            int totalHari = dateDiff.getDay();
            int totalJam = timeDiff.getHour();
            if (timeDiff.getMinute() > 0 || timeDiff.getSecond() > 0) totalJam++;

            int pay = ParkedVehicleHelper.getPay(pv, totalJam, totalHari);
            totalPay += pay;

            printVehicleSummary(pv, diff, totalHari, totalJam, pay);
        }
        System.out.println("\nTotal Bayar Keseluruhan: " + totalPay);
    }

    private static void inputVehicle(int n) {
        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Data Kendaraan ke-" + (i + 1) + " ---");
            System.out.print("Nomor Kendaraan: ");
            String no = SC.nextLine();
            System.out.print("Jenis Kendaraan (0: Motor, 1: Mobil): ");
            int jenis = Integer.parseInt(SC.nextLine());
            System.out.print("Status Parkir (0: Regular, 1: Menginap): ");
            int stat = Integer.parseInt(SC.nextLine());

            System.out.println("\nWaktu Kedatangan");
            System.out.print("Tanggal (yyyy/MM/dd): ");
            String datangTgl = SC.nextLine();
            System.out.print("Jam (HH:mm:ss): ");
            String datangJam = SC.nextLine();

            System.out.println("\nWaktu Kepulangan");
            System.out.print("Tanggal (yyyy/MM/dd): ");
            String pulangTgl = SC.nextLine();
            System.out.print("Jam (HH:mm:ss): ");
            String pulangJam = SC.nextLine();

            Waktu datang = new Waktu(parseTime(datangJam), parseDate(datangTgl));
            Waktu pulang = new Waktu(parseTime(pulangJam), parseDate(pulangTgl));

            JenisKendaraan kendaraan = (jenis == 0) ? JenisKendaraan.MOTOR : JenisKendaraan.MOBIL;
            Status status = (stat == 0) ? Status.REGULAR : Status.MENGINAP;

            parkedVehicles.add(new ParkedVehicle(no, kendaraan, status, datang, pulang));
        }
    }

    public static void main(String[] args) {
        System.out.print("Masukkan jumlah kendaraan: ");
        int n = Integer.parseInt(SC.nextLine());
        inputVehicle(n);
        calculateAndPrint();
    }
}
