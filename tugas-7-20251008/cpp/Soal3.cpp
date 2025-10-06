#include <iostream>
#include <iomanip>
#include <string>
#include <vector>
#include <sstream>
using namespace std;

// === ENUMS ===
enum JenisKendaraan { MOTOR, MOBIL };

enum Status { REGULAR, MENGINAP };

// === CLASS TIME ===
class Time {
private:
    int hour, minute, second;

public:
    Time() : hour(0), minute(0), second(0) {
    }

    Time(int h, int m, int s) : hour(h), minute(m), second(s) {
    }

    int getHour() const { return hour; }
    int getMinute() const { return minute; }
    int getSecond() const { return second; }

    void setHour(int h) { hour = h; }
    void setMinute(int m) { minute = m; }
    void setSecond(int s) { second = s; }

    string toString() const {
        ostringstream oss;
        oss << setw(2) << setfill('0') << hour << ":"
                << setw(2) << minute << ":"
                << setw(2) << second;
        return oss.str();
    }
};

// === CLASS DATE ===
class Date {
private:
    int year, month, day;

public:
    Date() : year(0), month(0), day(0) {
    }

    Date(int y, int m, int d) : year(y), month(m), day(d) {
    }

    int getYear() const { return year; }
    int getMonth() const { return month; }
    int getDay() const { return day; }

    void setYear(int y) { year = y; }
    void setMonth(int m) { month = m; }
    void setDay(int d) { day = d; }

    string toString() const {
        ostringstream oss;
        oss << setw(4) << setfill('0') << year << "/"
                << setw(2) << month << "/"
                << setw(2) << day;
        return oss.str();
    }
};

// === CLASS WAKTU (GABUNGAN DATE + TIME) ===
class Waktu {
private:
    Time time;
    Date date;

public:
    Waktu() {
    }

    Waktu(Time t, Date d) : time(t), date(d) {
    }

    Time getTime() const { return time; }
    Date getDate() const { return date; }

    void setTime(Time t) { time = t; }
    void setDate(Date d) { date = d; }
};

// === HELPER UNTUK PERHITUNGAN WAKTU ===
class WaktuHelper {
private:
    static bool isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    static int daysInMonth(int year, int month) {
        int days[12] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2 && isLeapYear(year)) return 29;
        return days[month - 1];
    }

public:
    static Waktu calculateTimeDifference(const Waktu &datang, const Waktu &pulang) {
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

        return Waktu(Time(hourDiff, minuteDiff, secondDiff),
                     Date(yearDiff, monthDiff, dayDiff));
    }
};

// === CLASS PARKED VEHICLE ===
class ParkedVehicle {
private:
    string noKendaraan;
    JenisKendaraan kendaraan;
    Status status;
    Waktu datang, pulang;

public:
    ParkedVehicle(string no, JenisKendaraan k, Status s, Waktu d, Waktu p)
        : noKendaraan(no), kendaraan(k), status(s), datang(d), pulang(p) {
    }

    string getNoKendaraan() const { return noKendaraan; }
    JenisKendaraan getKendaraan() const { return kendaraan; }
    Status getStatus() const { return status; }
    Waktu getDatang() const { return datang; }
    Waktu getPulang() const { return pulang; }
};

// === HELPER UNTUK HITUNG BAYAR ===
class ParkedVehicleHelper {
public:
    static int getPay(const ParkedVehicle &v, int totalJam, int totalHari) {
        int pay = 0;
        if (v.getStatus() == MENGINAP) {
            int rate = (v.getKendaraan() == MOTOR) ? 15000 : 25000;
            pay = rate * totalHari;
        } else {
            int rate = (v.getKendaraan() == MOTOR) ? 2000 : 3000;
            pay = rate * totalJam;
        }
        return pay;
    }
};

// === FUNGSI PARSER UNTUK INPUT ===
Time parseTime(const string &s) {
    int h, m, sec;
    char sep;
    istringstream iss(s);
    iss >> h >> sep >> m >> sep >> sec;
    return Time(h, m, sec);
}

Date parseDate(const string &s) {
    int y, m, d;
    char sep;
    istringstream iss(s);
    iss >> y >> sep >> m >> sep >> d;
    return Date(y, m, d);
}

// === OUTPUT RINCIAN KENDARAAN ===
void printVehicleSummary(const ParkedVehicle &pv, const Waktu &diff, int totalHari, int totalJam, int pay) {
    cout << "\n--- RINCIAN KENDARAAN ---\n";
    cout << "Nomor Kendaraan : " << pv.getNoKendaraan() << endl;
    cout << "Jenis Kendaraan : " << (pv.getKendaraan() == MOTOR ? "Motor" : "Mobil") << endl;
    cout << "Status Parkir   : " << (pv.getStatus() == REGULAR ? "Regular" : "Menginap") << endl;
    cout << "Tanggal Datang  : " << pv.getDatang().getDate().toString() << endl;
    cout << "Tanggal Pulang  : " << pv.getPulang().getDate().toString() << endl;
    cout << "Jam Datang      : " << pv.getDatang().getTime().toString() << endl;
    cout << "Jam Pulang      : " << pv.getPulang().getTime().toString() << endl;
    cout << "Selisih Tanggal : " << diff.getDate().toString() << endl;
    cout << "Selisih Waktu   : " << diff.getTime().toString() << endl;
    cout << "Total Hari      : " << totalHari << endl;
    cout << "Total Jam       : " << totalJam << endl;
    cout << "Biaya           : " << pay << endl;
}

// === MAIN PROGRAM ===
int main() {
    int n;
    cout << "Masukkan jumlah kendaraan: ";
    cin >> n;
    cin.ignore();

    vector<ParkedVehicle> parkedVehicles;
    int totalPay = 0;

    for (int i = 0; i < n; i++) {
        cout << "\n--- Data Kendaraan ke-" << (i + 1) << " ---\n";
        string no, datangTgl, datangJam, pulangTgl, pulangJam;
        int jenis, stat;

        cout << "Nomor Kendaraan: ";
        getline(cin, no);
        cout << "Jenis Kendaraan (0: Motor, 1: Mobil): ";
        cin >> jenis;
        cout << "Status Parkir (0: Regular, 1: Menginap): ";
        cin >> stat;
        cin.ignore();

        cout << "\nWaktu Kedatangan\n";
        cout << "Tanggal (yyyy/MM/dd): ";
        getline(cin, datangTgl);
        cout << "Jam (HH:mm:ss): ";
        getline(cin, datangJam);

        cout << "\nWaktu Kepulangan\n";
        cout << "Tanggal (yyyy/MM/dd): ";
        getline(cin, pulangTgl);
        cout << "Jam (HH:mm:ss): ";
        getline(cin, pulangJam);

        Waktu datang(parseTime(datangJam), parseDate(datangTgl));
        Waktu pulang(parseTime(pulangJam), parseDate(pulangTgl));

        JenisKendaraan kendaraan = (jenis == 0) ? MOTOR : MOBIL;
        Status status = (stat == 0) ? REGULAR : MENGINAP;

        parkedVehicles.push_back(ParkedVehicle(no, kendaraan, status, datang, pulang));
    }

    for (auto &pv: parkedVehicles) {
        Waktu diff = WaktuHelper::calculateTimeDifference(pv.getDatang(), pv.getPulang());
        Time tDiff = diff.getTime();
        Date dDiff = diff.getDate();

        int totalHari = dDiff.getDay();
        int totalJam = tDiff.getHour();
        if (tDiff.getMinute() > 0 || tDiff.getSecond() > 0) totalJam++;

        int pay = ParkedVehicleHelper::getPay(pv, totalJam, totalHari);
        totalPay += pay;

        printVehicleSummary(pv, diff, totalHari, totalJam, pay);
    }

    cout << "\nTotal Bayar Keseluruhan: " << totalPay << endl;
    return 0;
}
