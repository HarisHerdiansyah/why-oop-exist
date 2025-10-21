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

#include <iostream>
#include <string>
#include <vector>
#include <ctime>
#include <chrono>
#include <sstream>
#include <iomanip>
#include <map>

using namespace std;

class JenisKendaraan {
private:
    int tarif_per_jam, tarif_menginap;

public:
    static const JenisKendaraan MOBIL;
    static const JenisKendaraan MOTOR;
    static const JenisKendaraan TRUK;

    JenisKendaraan(int tarif_per_jam, int tarif_menginap)
        : tarif_per_jam(tarif_per_jam), tarif_menginap(tarif_menginap) {}

    int get_tarif_per_jam() const { return tarif_per_jam; }
    int get_tarif_menginap() const { return tarif_menginap; }
};

const JenisKendaraan JenisKendaraan::MOBIL(15000, 30000);
const JenisKendaraan JenisKendaraan::MOTOR(7000, 15000);
const JenisKendaraan JenisKendaraan::TRUK(40000, 80000);

class Time {
private:
    int hour, minute, seconds;

public:
    Time(int hour = 0, int minute = 0, int seconds = 0)
        : hour(hour), minute(minute), seconds(seconds) {}

    int get_hour() const { return hour; }
    int get_minute() const { return minute; }
    int get_seconds() const { return seconds; }

    void set_hour(int h) { hour = h; }
    void set_minute(int m) { minute = m; }
    void set_seconds(int s) { seconds = s; }

    string to_attr_string() const {
        ostringstream ss;
        ss << "Time{" << hour << ":" << minute << ":" << seconds << "}";
        return ss.str();
    }
};

class Dates {
private:
    int year, month, day;

public:
    Dates(int year = 0, int month = 0, int day = 0)
        : year(year), month(month), day(day) {}

    int get_year() const { return year; }
    int get_month() const { return month; }
    int get_day() const { return day; }

    void set_year(int y) { year = y; }
    void set_month(int m) { month = m; }
    void set_day(int d) { day = d; }

    string to_attr_string() const {
        ostringstream ss;
        ss << "Dates{" << year << "/" << month << "/" << day << "}";
        return ss.str();
    }
};

class Waktu {
private:
    Dates dates;
    Time time;

public:
    Waktu(Dates d = {}, Time t = {}) : dates(d), time(t) {}

    Dates get_dates() const { return dates; }
    Time get_time() const { return time; }
};

class Pemilik {
private:
    string name;

public:
    Pemilik(string name = "") : name(name) {}
    string get_name() const { return name; }
};

class Kendaraan {
private:
    JenisKendaraan jenis_kendaraan;
    Pemilik pemilik;
    Waktu waktu_masuk, waktu_keluar;
    string no_kendaraan;

public:
    Kendaraan(JenisKendaraan jk, Pemilik p, Waktu wm, Waktu wk, string nk)
        : jenis_kendaraan(jk), pemilik(p), waktu_masuk(wm), waktu_keluar(wk), no_kendaraan(nk) {}

    JenisKendaraan get_jenis_kendaraan() const { return jenis_kendaraan; }
    Pemilik get_pemilik() const { return pemilik; }
    Waktu get_waktu_masuk() const { return waktu_masuk; }
    Waktu get_waktu_keluar() const { return waktu_keluar; }
    string get_no_kendaraan() const { return no_kendaraan; }
};

class Mobil : public Kendaraan {
public:
    Mobil(JenisKendaraan jk, Pemilik p, Waktu wm, Waktu wk, string nk)
        : Kendaraan(jk, p, wm, wk, nk) {}
};
class Motor : public Kendaraan {
public:
    Motor(JenisKendaraan jk, Pemilik p, Waktu wm, Waktu wk, string nk)
        : Kendaraan(jk, p, wm, wk, nk) {}
};
class Truk : public Kendaraan {
public:
    Truk(JenisKendaraan jk, Pemilik p, Waktu wm, Waktu wk, string nk)
        : Kendaraan(jk, p, wm, wk, nk) {}
};

namespace MainProgram {
    vector<Kendaraan> daftar_kendaraan;

    // Fungsi parsing waktu manual seperti Java
    tm parseDateTime(const string& input) {
        tm t = {};
        istringstream ss(input);
        ss >> get_time(&t, "%Y/%m/%d %H:%M:%S");
        return t;
    }

    // Fungsi ambil waktu sistem (sekarang)
    tm getSystemTime() {
        time_t now = time(nullptr);
        tm localTime;
#ifdef _WIN32
        localtime_s(&localTime, &now);
#else
        localtime_r(&now, &localTime);
#endif
        return localTime;
    }

    map<string, tm> inputDateTime(int option) {
        map<string, tm> dateMap;
        string input;

        if (option == 1) {
            cout << "Waktu masuk (Tekan enter): ";
            cin.ignore();
            cin.get();
            dateMap["in"] = getSystemTime();
            cout << "Waktu keluar (Tekan enter): ";
            cin.get();
            dateMap["out"] = getSystemTime();
        } else if (option == 2) {
            cout << "Waktu masuk (yyyy/MM/dd HH:mm:ss): ";
            cin.ignore();
            getline(cin, input);
            dateMap["in"] = parseDateTime(input);
            cout << "Waktu keluar (yyyy/MM/dd HH:mm:ss): ";
            getline(cin, input);
            dateMap["out"] = parseDateTime(input);
        }
        return dateMap;
    }

    Kendaraan inputObject() {
        string nama, jenis, nomor;
        int option;

        cout << "Nama pemilik: ";
        getline(cin, nama);
        cout << "Jenis kendaraan (MOBIL/MOTOR/TRUK): ";
        getline(cin, jenis);
        cout << "Nomor kendaraan: ";
        getline(cin, nomor);
        cout << "Opsi masukkan waktu, (1) Sistem; (2) Manual: ";
        cin >> option;

        map<string, tm> userDateTime = inputDateTime(option);
        tm in = userDateTime["in"];
        tm out = userDateTime["out"];

        Dates dIn(in.tm_year + 1900, in.tm_mon + 1, in.tm_mday);
        Time tIn(in.tm_hour, in.tm_min, in.tm_sec);
        Dates dOut(out.tm_year + 1900, out.tm_mon + 1, out.tm_mday);
        Time tOut(out.tm_hour, out.tm_min, out.tm_sec);

        Waktu waktuMasuk(dIn, tIn);
        Waktu waktuKeluar(dOut, tOut);
        Pemilik pemilik(nama);

        if (jenis == "MOBIL")
            return Mobil(JenisKendaraan::MOBIL, pemilik, waktuMasuk, waktuKeluar, nomor);
        else if (jenis == "MOTOR")
            return Motor(JenisKendaraan::MOTOR, pemilik, waktuMasuk, waktuKeluar, nomor);
        else
            return Truk(JenisKendaraan::TRUK, pemilik, waktuMasuk, waktuKeluar, nomor);
    }

    void output(const Kendaraan& k) {
        cout << "\nNo. Kendaraan: " << k.get_no_kendaraan();
        cout << "\nNama Pemilik: " << k.get_pemilik().get_name();
        cout << "\nWaktu Masuk: " << k.get_waktu_masuk().get_dates().to_attr_string()
             << " " << k.get_waktu_masuk().get_time().to_attr_string();
        cout << "\nWaktu Keluar: " << k.get_waktu_keluar().get_dates().to_attr_string()
             << " " << k.get_waktu_keluar().get_time().to_attr_string();
        cout << "\n";
    }

    void main() {
        int n;
        cout << "Jumlah kendaraan: ";
        cin >> n;
        cin.ignore();

        for (int i = 0; i < n; i++) {
            cout << "\nKendaraan ke-" << i + 1 << endl;
            daftar_kendaraan.push_back(inputObject());
        }

        for (int i = 0; i < n; i++) {
            output(daftar_kendaraan[i]);
        }
    }
}

int main() {
    MainProgram::main();
    return 0;
}
