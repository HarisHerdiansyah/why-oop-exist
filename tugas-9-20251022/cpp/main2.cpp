/*
Nama - NPM    : ~ Raissa Christabel Sebayang - 140810240008
                ~ Abraham Gomes Samosir - 140810240044
                ~ Haris Herdiansyah - 140810240074
Kelas         : B
Nama File     : Main.cpp
Deskripsi     : Program Inheritance Parkir Pelabuhan
                Kelas:
                - Waktu (Date, Time)
                - Pemilik
                - Kendaraan (Mobil, Motor, Truk) -> inheritance
                - Input waktu bisa manual atau otomatis (dari sistem)
*/

#include <iostream>
#include <string>
#include <vector>
#include <ctime>
#include <iomanip>
#include <sstream>
#include <cmath>
#include <map>

using namespace std;

class JenisKendaraan {
private:
    string nama;
    int tarif_per_jam, tarif_menginap;

public:
    static const JenisKendaraan MOBIL;
    static const JenisKendaraan MOTOR;
    static const JenisKendaraan TRUK;

    JenisKendaraan(string nama = "", int tarif_per_jam = 0, int tarif_menginap = 0)
        : nama(nama), tarif_per_jam(tarif_per_jam), tarif_menginap(tarif_menginap) {}

    string get_nama() const { return nama; }
    int get_tarif_per_jam() const { return tarif_per_jam; }
    int get_tarif_menginap() const { return tarif_menginap; }
};

const JenisKendaraan JenisKendaraan::MOBIL("MOBIL", 15000, 30000);
const JenisKendaraan JenisKendaraan::MOTOR("MOTOR", 7000, 15000);
const JenisKendaraan JenisKendaraan::TRUK("TRUK", 40000, 80000);

class Time {
private:
    int hour, minute, seconds;

public:
    Time(int hour = 0, int minute = 0, int seconds = 0)
        : hour(hour), minute(minute), seconds(seconds) {}

    int get_hour() const { return hour; }
    int get_minute() const { return minute; }
    int get_seconds() const { return seconds; }

    string to_attr_string() const {
        ostringstream ss;
        ss << setw(2) << setfill('0') << hour << ":"
           << setw(2) << setfill('0') << minute;
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

    string to_attr_string() const {
        ostringstream ss;
        ss << year << "/" << setw(2) << setfill('0') << month
           << "/" << setw(2) << setfill('0') << day;
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

    int hitungTarif() const {
        tm in = {}, out = {};
        Dates dIn = waktu_masuk.get_dates();
        Dates dOut = waktu_keluar.get_dates();
        Time tIn = waktu_masuk.get_time();
        Time tOut = waktu_keluar.get_time();

        in.tm_year = dIn.get_year() - 1900;
        in.tm_mon = dIn.get_month() - 1;
        in.tm_mday = dIn.get_day();
        in.tm_hour = tIn.get_hour();
        in.tm_min = tIn.get_minute();
        in.tm_sec = 0;

        out.tm_year = dOut.get_year() - 1900;
        out.tm_mon = dOut.get_month() - 1;
        out.tm_mday = dOut.get_day();
        out.tm_hour = tOut.get_hour();
        out.tm_min = tOut.get_minute();
        out.tm_sec = 0;

        time_t tInSec = mktime(&in);
        time_t tOutSec = mktime(&out);
        double diffHour = difftime(tOutSec, tInSec) / 3600.0;

        if (diffHour >= 24)
            return jenis_kendaraan.get_tarif_menginap();
        else
            return ceil(diffHour) * jenis_kendaraan.get_tarif_per_jam();
    }
};

class Mobil : public Kendaraan {
public:
    Mobil(Pemilik p, Waktu wm, Waktu wk, string nk)
        : Kendaraan(JenisKendaraan::MOBIL, p, wm, wk, nk) {}
};
class Motor : public Kendaraan {
public:
    Motor(Pemilik p, Waktu wm, Waktu wk, string nk)
        : Kendaraan(JenisKendaraan::MOTOR, p, wm, wk, nk) {}
};
class Truk : public Kendaraan {
public:
    Truk(Pemilik p, Waktu wm, Waktu wk, string nk)
        : Kendaraan(JenisKendaraan::TRUK, p, wm, wk, nk) {}
};

namespace MainProgram {
    vector<Kendaraan> daftar_kendaraan;

    tm parseDateTime(const string &input) {
        tm t = {};
        istringstream ss(input);
        ss >> get_time(&t, "%Y/%m/%d %H:%M");
        return t;
    }

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
            cout << "Tekan Enter untuk catat waktu masuk...";
            cin.ignore();
            cin.get();
            dateMap["in"] = getSystemTime();

            cout << "Tekan Enter untuk catat waktu keluar...";
            cin.get();
            dateMap["out"] = getSystemTime();
        } else { 
            cout << "Waktu masuk (YYYY/MM/DD HH:MM): ";
            getline(cin, input);
            dateMap["in"] = parseDateTime(input);

            cout << "Waktu keluar (YYYY/MM/DD HH:MM): ";
            getline(cin, input);
            dateMap["out"] = parseDateTime(input);
        }
        return dateMap;
    }

    Kendaraan inputObject() {
        string nama, jenis, nomor;
        int option;

        cout << "Nama Pemilik: ";
        getline(cin, nama);
        cout << "Jenis Kendaraan (MOBIL/MOTOR/TRUK): ";
        getline(cin, jenis);
        cout << "Nomor Kendaraan: ";
        getline(cin, nomor);

        cout << "Opsi waktu: (1) Otomatis  (2) Manual : ";
        cin >> option;
        cin.ignore();

        map<string, tm> waktuMap = inputDateTime(option);
        tm in = waktuMap["in"];
        tm out = waktuMap["out"];

        Dates dIn(in.tm_year + 1900, in.tm_mon + 1, in.tm_mday);
        Time tIn(in.tm_hour, in.tm_min);
        Dates dOut(out.tm_year + 1900, out.tm_mon + 1, out.tm_mday);
        Time tOut(out.tm_hour, out.tm_min);

        Waktu waktuMasuk(dIn, tIn);
        Waktu waktuKeluar(dOut, tOut);
        Pemilik pemilik(nama);

        if (jenis == "MOBIL")
            return Mobil(pemilik, waktuMasuk, waktuKeluar, nomor);
        else if (jenis == "MOTOR")
            return Motor(pemilik, waktuMasuk, waktuKeluar, nomor);
        else
            return Truk(pemilik, waktuMasuk, waktuKeluar, nomor);
    }

    void outputTable() {
        cout << "+----+------------------+-------+--------------+------------------+------------------+--------------+\n";
        cout << "| No | Nama Pemilik     | Jenis | No Kendaraan | Waktu Masuk      | Waktu Keluar     | Tarif (Rp)   |\n";
        cout << "+----+------------------+-------+--------------+------------------+------------------+--------------+\n";

        int totalPendapatan = 0;
        for (size_t i = 0; i < daftar_kendaraan.size(); i++) {
            const auto &k = daftar_kendaraan[i];
            
            string namaPemilik = k.get_pemilik().get_name();
            if (namaPemilik.length() > 16) {
                namaPemilik = namaPemilik.substr(0, 13) + "...";
            }
            
            string noKendaraan = k.get_no_kendaraan();
            if (noKendaraan.length() > 12) {
                noKendaraan = noKendaraan.substr(0, 9) + "...";
            }
            
            string waktuMasuk = k.get_waktu_masuk().get_dates().to_attr_string() + " " + 
                               k.get_waktu_masuk().get_time().to_attr_string();
            string waktuKeluar = k.get_waktu_keluar().get_dates().to_attr_string() + " " + 
                                k.get_waktu_keluar().get_time().to_attr_string();
            
            cout << "| " << setw(2) << right << (i + 1) << " "
                 << "| " << left << setw(16) << namaPemilik << " "
                 << "| " << left << setw(5) << k.get_jenis_kendaraan().get_nama() << " "
                 << "| " << left << setw(12) << noKendaraan << " "
                 << "| " << left << setw(16) << waktuMasuk << " "
                 << "| " << left << setw(16) << waktuKeluar << " "
                 << "| " << right << setw(12) << k.hitungTarif() << " |\n";
            
            totalPendapatan += k.hitungTarif();
        }

        cout << "+----+------------------+-------+--------------+------------------+------------------+--------------+\n";
        cout << "| Total Pendapatan:" << setw(79) << right << totalPendapatan << " |\n";
        cout << "+----+------------------+-------+--------------+------------------+------------------+--------------+\n";
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

        cout << "\n";
        outputTable();
    }
}

int main() {
    MainProgram::main();
    return 0;
}