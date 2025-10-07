import java.util.Scanner;

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

    public int totalTimeInSecond() {
        return (hour * 3600) + (minute * 60) + second;
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
        int year = pulang.getDate().getYear() - datang.getDate().getYear();
        int month = pulang.getDate().getMonth() - datang.getDate().getMonth();
        int day = pulang.getDate().getDay() - datang.getDate().getDay();
        int hour = pulang.getTime().getHour() - datang.getTime().getHour();
        int minute = pulang.getTime().getMinute() - datang.getTime().getMinute();
        int second = pulang.getTime().getSecond() - datang.getTime().getSecond();

        if (second < 0) {
            second += 60;
            minute -= 1;
        }

        if (minute < 0) {
            minute += 60;
            hour -= 1;
        }

        if (hour < 0) {
            hour += 24;
            day -= 1;
        }

        if (day < 0) {
            month -= 1;
            int prevMonth = pulang.getDate().getMonth() - 1;
            int yearPrevMonth = pulang.getDate().getYear();
            if (prevMonth <= 0) {
                prevMonth += 12;
                yearPrevMonth -= 1;
            }
            day += daysInMonth(yearPrevMonth, prevMonth);
        }

        if (month < 0) {
            month += 12;
            year -= 1;
        }

        return new Waktu(new Time(hour, minute, second), new Date(year, month, day));
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
    public static int getPay(ParkedVehicle vehicle, Waktu diff) {
        int multiplier = 0;
        int pay = 0;

        if (vehicle.getStatus() == Status.MENGINAP) {
            multiplier = (vehicle.getKendaraan() == JenisKendaraan.MOTOR) ? 15000 : 25000;
            pay = multiplier * diff.getDate().getDay();
        } else {
            multiplier = (vehicle.getKendaraan() == JenisKendaraan.MOTOR) ? 2000 : 3000;
            if (diff.getTime().totalTimeInSecond() % 3600 > 0) {
                pay = multiplier * (diff.getTime().getHour() + 1);
            } else {
                pay = multiplier * diff.getTime().getHour();
            }
        }
        return pay;
    }
}

class LarikParkedVehicle {
    private final ParkedVehicle[] daftarKendaraan;
    private final int size;
    private int indexPointer = 0;

    public LarikParkedVehicle(int size) {
        this.daftarKendaraan = new ParkedVehicle[size];
        this.size = size;
    }

    public ParkedVehicle[] getDaftarKendaraan() {
        return daftarKendaraan;
    }

    public int getSize() {
        return size;
    }

    public int getIndexPointer() {
        return indexPointer;
    }

    public void setIndexPointer(int indexPointer) {
        this.indexPointer = indexPointer;
    }

    public ParkedVehicle getVehicleAt(int index) {
        return daftarKendaraan[index];
    }

    public void push(ParkedVehicle vehicle) {
        if (indexPointer >= size) {
            System.out.println("Maximum size of array exceeded.");
            return;
        }

        daftarKendaraan[indexPointer] = vehicle;
        indexPointer += 1;
    }

    public ParkedVehicle deleteAt(int index) {
        ParkedVehicle vehicle = daftarKendaraan[index];

        for (int i = index + 1; i < indexPointer; i++) {
            daftarKendaraan[i - 1] = daftarKendaraan[i];
        }
        indexPointer -= 1;
        daftarKendaraan[indexPointer] = null;

        return vehicle;
    }

    public ParkedVehicle pop() {
        return deleteAt(0);
    }
}

public class Soal3 {
    private static final Scanner SCANNER = new Scanner(System.in);

    private static Time parseTime(String s) {
        String[] p = s.split(":");
        return new Time(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]));
    }

    private static Date parseDate(String s) {
        String[] p = s.split("/");
        return new Date(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]));
    }

    private static void outputBuilder(ParkedVehicle vehicle) {
        String format = "| %-15s | %-15s | %-10s | %-15s | %-15s | %-12s | %-12s | %-10s | %-10s | %-10s |%n";
        String line = "+-----------------+-----------------+------------+-----------------+-----------------+--------------+--------------+------------+------------+------------+";

        Waktu diff = WaktuHelper.calculateTimeDifference(vehicle.getDatang(), vehicle.getPulang());
        Time temp = new Time(diff.getTime().getHour() + diff.getDate().getDay() * 24, diff.getTime().getMinute(), diff.getTime().getSecond());
        int pay = ParkedVehicleHelper.getPay(vehicle, diff);

        System.out.printf(format,
                vehicle.getNoKendaraan(),
                vehicle.getKendaraan(),
                vehicle.getStatus(),
                vehicle.getDatang().getDate(),
                vehicle.getPulang().getDate(),
                vehicle.getDatang().getTime(),
                vehicle.getPulang().getTime(),
                diff.getDate().getDay(),
                temp,
                pay
        );
        System.out.println(line);
    }

    private static void outputTable(LarikParkedVehicle daftarKendaraan) {
        String format = "| %-15s | %-15s | %-10s | %-15s | %-15s | %-12s | %-12s | %-10s | %-10s | %-10s |%n";
        String line = "+-----------------+-----------------+------------+-----------------+-----------------+--------------+--------------+------------+------------+------------+";
        System.out.println("\n" + line);
        System.out.printf(format, "No Kendaraan", "Jenis Kendaraan", "Status", "Tanggal Datang", "Tanggal Pulang", "Jam Datang", "Jam Pulang", "Lama Hari", "Lama Jam", "Biaya");
        System.out.println(line);
        for (int i = 0 ; i < daftarKendaraan.getSize(); i++) {
            outputBuilder(daftarKendaraan.getVehicleAt(i));
        }
    }

    private static ParkedVehicle input(int i) {
        System.out.println("\n--- Data Kendaraan ke-" + (i + 1) + " ---");
        System.out.print("Nomor Kendaraan: ");
        String no = SCANNER.nextLine();
        System.out.print("Jenis Kendaraan (0: Motor, 1: Mobil): ");
        int jenis = Integer.parseInt(SCANNER.nextLine());
        System.out.print("Status Parkir (0: Regular, 1: Menginap): ");
        int stat = Integer.parseInt(SCANNER.nextLine());

        System.out.print("Waktu Kedatangan (yyyy/MM/dd-HH:mm:ss): ");
        String[] waktuKedatangan = SCANNER.nextLine().split("-");
        System.out.print("Waktu Kepulangan (yyyy/MM/dd-HH:mm:ss): ");
        String[] waktuKepulangan = SCANNER.nextLine().split("-");

        Waktu datang = new Waktu(parseTime(waktuKedatangan[1]), parseDate(waktuKedatangan[0]));
        Waktu pulang = new Waktu(parseTime(waktuKepulangan[1]), parseDate(waktuKepulangan[0]));

        JenisKendaraan kendaraan = (jenis == 0) ? JenisKendaraan.MOTOR : JenisKendaraan.MOBIL;
        Status status = (stat == 0) ? Status.REGULAR : Status.MENGINAP;

        return new ParkedVehicle(no, kendaraan, status, datang, pulang);
    }

    public static void main(String[] args) {
        System.out.print("Masukkan jumlah kendaraan: ");
        int n = Integer.parseInt(SCANNER.nextLine());
        LarikParkedVehicle daftarKendaraan = new LarikParkedVehicle(n);

        for (int i = 0; i < n; i++) {
            daftarKendaraan.push(input(i));
        }

        outputTable(daftarKendaraan);
    }
}
