/*
Nama - NPM    : ~ Raissa Christabel Sebayang - 140810240008
                ~ Abraham Gomes Samosir - 140810240044
                ~ Haris Herdiansyah - 140810240074
Kelas         : B
Nama File     : Soal3.cpp
Deskripsi     : Membuat program untuk menghitung biaya parkir kendaraan
                berdasarkan waktu datang dan pulang menggunakan array.
*/

#include <iostream>
#include <iomanip>
#include <vector>
#include <string>
#include <sstream>

enum JenisKendaraan { MOTOR, MOBIL };
enum Status { REGULAR, MENGINAP };

class Time {
public:
    int hour, minute, second;
    Time(int h = 0, int m = 0, int s = 0) : hour(h), minute(m), second(s) {}
    int totalSeconds() const { return hour * 3600 + minute * 60 + second; }
    std::string toString() const {
        std::ostringstream oss;
        oss << std::setw(2) << std::setfill('0') << hour << ":"
            << std::setw(2) << std::setfill('0') << minute << ":"
            << std::setw(2) << std::setfill('0') << second;
        return oss.str();
    }
    static Time parse(const std::string& s) {
        int h, m, sec;
        char sep;
        std::istringstream iss(s);
        iss >> h >> sep >> m >> sep >> sec;
        return Time(h, m, sec);
    }
};

class Date {
public:
    int year, month, day;
    Date(int y = 0, int m = 0, int d = 0) : year(y), month(m), day(d) {}
    std::string toString() const {
        std::ostringstream oss;
        oss << std::setw(4) << std::setfill('0') << year << "/"
            << std::setw(2) << std::setfill('0') << month << "/"
            << std::setw(2) << std::setfill('0') << day;
        return oss.str();
    }
    static Date parse(const std::string& s) {
        int y, m, d;
        char sep;
        std::istringstream iss(s);
        iss >> y >> sep >> m >> sep >> d;
        return Date(y, m, d);
    }
};

class Waktu {
public:
    Time time;
    Date date;
    Waktu(const Time& t = Time(), const Date& d = Date()) : time(t), date(d) {}
};

bool isLeapYear(int year) {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
}

int daysInMonth(int year, int month) {
    int days[] = {31,28,31,30,31,30,31,31,30,31,30,31};
    if (month == 2 && isLeapYear(year)) return 29;
    return days[month - 1];
}

Waktu calculateTimeDifference(const Waktu& datang, const Waktu& pulang) {
    int year = pulang.date.year - datang.date.year;
    int month = pulang.date.month - datang.date.month;
    int day = pulang.date.day - datang.date.day;
    int hour = pulang.time.hour - datang.time.hour;
    int minute = pulang.time.minute - datang.time.minute;
    int second = pulang.time.second - datang.time.second;

    if (second < 0) { second += 60; minute -= 1; }
    if (minute < 0) { minute += 60; hour -= 1; }
    if (hour < 0) { hour += 24; day -= 1; }
    if (day < 0) {
        month -= 1;
        int prevMonth = pulang.date.month - 1;
        int yearPrevMonth = pulang.date.year;
        if (prevMonth <= 0) { prevMonth += 12; yearPrevMonth -= 1; }
        day += daysInMonth(yearPrevMonth, prevMonth);
    }
    if (month < 0) { month += 12; year -= 1; }
    return Waktu(Time(hour, minute, second), Date(year, month, day));
}

class ParkedVehicle {
public:
    std::string noKendaraan;
    JenisKendaraan kendaraan;
    Status status;
    Waktu datang, pulang;
    ParkedVehicle(const std::string& no, JenisKendaraan jk, Status st, const Waktu& dtg, const Waktu& plg)
        : noKendaraan(no), kendaraan(jk), status(st), datang(dtg), pulang(plg) {}
};

int getPay(const ParkedVehicle& vehicle, const Waktu& diff) {
    int multiplier = 0, pay = 0;
    if (vehicle.status == MENGINAP) {
        multiplier = (vehicle.kendaraan == MOTOR) ? 15000 : 25000;
        pay = multiplier * diff.date.day;
    } else {
        multiplier = (vehicle.kendaraan == MOTOR) ? 2000 : 3000;
        int jam = diff.time.hour;
        if (diff.time.minute > 0 || diff.time.second > 0) jam += 1;
        pay = multiplier * jam;
    }
    return pay;
}

std::string jenisToString(JenisKendaraan jk) {
    return (jk == MOTOR) ? "MOTOR" : "MOBIL";
}
std::string statusToString(Status st) {
    return (st == REGULAR) ? "REGULAR" : "MENGINAP";
}

ParkedVehicle inputVehicle(int i) {
    std::string no, jenisStr, statusStr, waktuDatang, waktuPulang;
    int jenis, status;
    std::cout << "\n--- Data Kendaraan ke-" << (i + 1) << " ---\n";
    std::cout << "Nomor Kendaraan: ";
    std::getline(std::cin, no);
    std::cout << "Jenis Kendaraan (0: Motor, 1: Mobil): ";
    std::cin >> jenis; std::cin.ignore();
    std::cout << "Status Parkir (0: Regular, 1: Menginap): ";
    std::cin >> status; std::cin.ignore();
    std::cout << "Waktu Kedatangan (yyyy/MM/dd-HH:mm:ss): ";
    std::getline(std::cin, waktuDatang);
    std::cout << "Waktu Kepulangan (yyyy/MM/dd-HH:mm:ss): ";
    std::getline(std::cin, waktuPulang);

    size_t dashPos = waktuDatang.find('-');
    Date datangDate = Date::parse(waktuDatang.substr(0, dashPos));
    Time datangTime = Time::parse(waktuDatang.substr(dashPos + 1));
    dashPos = waktuPulang.find('-');
    Date pulangDate = Date::parse(waktuPulang.substr(0, dashPos));
    Time pulangTime = Time::parse(waktuPulang.substr(dashPos + 1));

    return ParkedVehicle(no, static_cast<JenisKendaraan>(jenis), static_cast<Status>(status),
                         Waktu(datangTime, datangDate), Waktu(pulangTime, pulangDate));
}

void printVehicleSummary(const std::vector<ParkedVehicle>& vehicles) {
    std::string line = "+-----------------+-----------------+------------+-----------------+-----------------+--------------+--------------+------------+------------+------------+";
    std::cout << "\n" << line << "\n";
    std::cout << "| " << std::setw(15) << std::left << "No Kendaraan"
              << " | " << std::setw(15) << "Jenis Kendaraan"
              << " | " << std::setw(10) << "Status"
              << " | " << std::setw(15) << "Tanggal Datang"
              << " | " << std::setw(15) << "Tanggal Pulang"
              << " | " << std::setw(12) << "Jam Datang"
              << " | " << std::setw(12) << "Jam Pulang"
              << " | " << std::setw(10) << "Lama Hari"
              << " | " << std::setw(10) << "Lama Jam"
              << " | " << std::setw(10) << "Biaya"
              << " |\n" << line << "\n";
    for (const auto& v : vehicles) {
        Waktu diff = calculateTimeDifference(v.datang, v.pulang);
        int lamaJam = diff.time.hour + diff.date.day * 24;
        std::ostringstream lamaJamStr;
        lamaJamStr << std::setw(2) << std::setfill('0') << lamaJam << ":"
                   << std::setw(2) << std::setfill('0') << diff.time.minute << ":"
                   << std::setw(2) << std::setfill('0') << diff.time.second;
        int pay = getPay(v, diff);
        std::cout << "| " << std::setw(15) << std::left << v.noKendaraan
                  << " | " << std::setw(15) << jenisToString(v.kendaraan)
                  << " | " << std::setw(10) << statusToString(v.status)
                  << " | " << std::setw(15) << v.datang.date.toString()
                  << " | " << std::setw(15) << v.pulang.date.toString()
                  << " | " << std::setw(12) << v.datang.time.toString()
                  << " | " << std::setw(12) << v.pulang.time.toString()
                  << " | " << std::setw(10) << diff.date.day
                  << " | " << std::setw(10) << lamaJamStr.str()
                  << " | " << std::setw(10) << pay
                  << " |\n" << line << "\n";
    }
}

int main() {
    int n;
    std::cout << "Masukkan jumlah kendaraan: ";
    std::cin >> n; std::cin.ignore();
    std::vector<ParkedVehicle> vehicles;
    for (int i = 0; i < n; ++i) {
        vehicles.push_back(inputVehicle(i));
    }
    printVehicleSummary(vehicles);
    return 0;
}
