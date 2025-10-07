#include <iostream>
#include <iomanip>
#include <string>
#include <sstream>
#include <cmath>
using namespace std;

// === ENUMS ===
enum JenisKendaraan { MOTOR, MOBIL };
enum Status { REGULAR, MENGINAP };

// === CLASS TIME ===
class Time {
private:
    int hour, minute, second;

public:
    Time() : hour(0), minute(0), second(0) {}
    Time(int h, int m, int s) : hour(h), minute(m), second(s) {}

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
    Date() : year(0), month(0), day(0) {}
    Date(int y, int m, int d) : year(y), month(m), day(d) {}

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

// === CLASS WAKTU ===
class Waktu {
private:
    Time time;
    Date date;

public:
    Waktu() {}
    Waktu(Time t, Date d) : time(t), date(d) {}

    Time getTime() const { return time; }
    Date getDate() const { return date; }

    void setTime(Time t) { time = t; }
    void setDate(Date d) { date = d; }
};

// === CLASS PARKED VEHICLE ===
class ParkedVehicle {
private:
    string noKendaraan;
    JenisKendaraan kendaraan;
    Status status;
    Waktu datang, pulang;

public:
    ParkedVehicle() {} // === PERUBAHAN: tambahkan konstruktor default
    ParkedVehicle(string no, JenisKendaraan k, Status s, Waktu d, Waktu p)
        : noKendaraan(no), kendaraan(k), status(s), datang(d), pulang(p) {}

    string getNoKendaraan() const { return noKendaraan; }
    JenisKendaraan getKendaraan() const { return kendaraan; }
    Status getStatus() const { return status; }
    Waktu getDatang() const { return datang; }
    Waktu getPulang() const { return pulang; }

    void setData(string no, JenisKendaraan k, Status s, Waktu d, Waktu p) {
        noKendaraan = no;
        kendaraan = k;
        status = s;
        datang = d;
        pulang = p;
    }
};

// === HELPER: HITUNG SELISIH WAKTU ===
class WaktuHelper {
public:
    static void calculateTotalTime(const Waktu &datang, const Waktu &pulang, int &totalHari, int &totalJam) {
        tm datang_tm = {};
        datang_tm.tm_year = datang.getDate().getYear() - 1900;
        datang_tm.tm_mon = datang.getDate().getMonth() - 1;
        datang_tm.tm_mday = datang.getDate().getDay();
        datang_tm.tm_hour = datang.getTime().getHour();
        datang_tm.tm_min = datang.getTime().getMinute();
        datang_tm.tm_sec = datang.getTime().getSecond();

        tm pulang_tm = {};
        pulang_tm.tm_year = pulang.getDate().getYear() - 1900;
        pulang_tm.tm_mon = pulang.getDate().getMonth() - 1;
        pulang_tm.tm_mday = pulang.getDate().getDay();
        pulang_tm.tm_hour = pulang.getTime().getHour();
        pulang_tm.tm_min = pulang.getTime().getMinute();
        pulang_tm.tm_sec = pulang.getTime().getSecond();

        time_t datang_time = mktime(&datang_tm);
        time_t pulang_time = mktime(&pulang_tm);

        double diff_seconds = difftime(pulang_time, datang_time);
        if (diff_seconds < 0) diff_seconds += 86400;

        totalHari = diff_seconds / 86400;
        double sisa = fmod(diff_seconds, 86400);
        totalJam = sisa / 3600;
        if (fmod(sisa, 3600) > 0) totalJam += 1;
        totalJam += totalHari * 24;
    }
};

// === HELPER: HITUNG BIAYA ===
class ParkedVehicleHelper {
public:
    static int getPay(const ParkedVehicle &v, int totalJam, int totalHari) {
        if (v.getStatus() == MENGINAP) {
            int rate = (v.getKendaraan() == MOTOR) ? 15000 : 25000;
            return rate * max(totalHari, 1);
        } else {
            int rate = (v.getKendaraan() == MOTOR) ? 2000 : 3000;
            return rate * totalJam;
        }
    }
};

// === PARSER ===
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

// === OUTPUT ===
void printVehicleSummary(const ParkedVehicle &pv, int totalHari, int totalJam, int pay) {
    cout << "\n--- RINCIAN KENDARAAN ---\n";
    cout << "Nomor Kendaraan : " << pv.getNoKendaraan() << endl;
    cout << "Jenis Kendaraan : " << (pv.getKendaraan() == MOTOR ? "Motor" : "Mobil") << endl;
    cout << "Status Parkir   : " << (pv.getStatus() == REGULAR ? "Regular" : "Menginap") << endl;
    cout << "Tanggal Datang  : " << pv.getDatang().getDate().toString() << endl;
    cout << "Jam Datang      : " << pv.getDatang().getTime().toString() << endl;
    cout << "Tanggal Pulang  : " << pv.getPulang().getDate().toString() << endl;
    cout << "Jam Pulang      : " << pv.getPulang().getTime().toString() << endl;
    cout << "Total Hari      : " << totalHari << endl;
    cout << "Total Jam       : " << totalJam << endl;
    cout << "Biaya           : Rp " << pay << endl;
}

// === CLASS ARRAY KENDARAAN ===
class LarikKendaraan {
private:
    ParkedVehicle kendaraan[10];
    int n;

public:
    LarikKendaraan() : n(0) {}

    void tambahData(const ParkedVehicle &pv) {
        kendaraan[n++] = pv;
    }

    void tampilData() {
        int totalBayar = 0;
        for (int i = 0; i < n; i++) {
            int totalHari, totalJam;
            WaktuHelper::calculateTotalTime(kendaraan[i].getDatang(), kendaraan[i].getPulang(), totalHari, totalJam);
            int pay = ParkedVehicleHelper::getPay(kendaraan[i], totalJam, totalHari);
            totalBayar += pay;
            printVehicleSummary(kendaraan[i], totalHari, totalJam, pay);
        }
        cout << "\nTotal Bayar Keseluruhan : Rp " << totalBayar << endl;
    }
};

// === MAIN ===
int main() {
    int n;
    cout << "Masukkan jumlah kendaraan: ";
    cin >> n;
    cin.ignore();

    LarikKendaraan daftar; // === PERUBAHAN: ganti vector jadi objek array

    for (int i = 0; i < n; i++) {
        cout << "\n--- Data Kendaraan ke-" << i + 1 << " ---\n";
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
        JenisKendaraan jk = (jenis == 0) ? MOTOR : MOBIL;
        Status st = (stat == 0) ? REGULAR : MENGINAP;

        ParkedVehicle pv;
        pv.setData(no, jk, st, datang, pulang);

        daftar.tambahData(pv); // === PERUBAHAN
    }

    daftar.tampilData(); // === PERUBAHAN
    return 0;
}
