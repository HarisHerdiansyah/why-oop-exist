/*
Nama - NPM    : ~ Raissa Christabel Sebayang - 140810240008
                ~ Abraham Gomes Samosir - 140810240044
                ~ Haris Herdiansyah - 140810240074
Kelas         : B
Nama File     : Soal2.cpp
Deskripsi     : Membuat program untuk menghitung nilai ujian lari mahasiswa
                berdasarkan waktu mulai dan selesai lari menggunakan array.
*/

#include <iostream>
#include <iomanip>
#include <vector>
#include <string>
#include <sstream>

class Waktu {
public:
    int jam, menit, detik;
    Waktu(int j = 0, int m = 0, int d = 0) : jam(j), menit(m), detik(d) {}

    static Waktu parseTime(const std::string& t) {
        int j, m, d;
        char sep;
        std::istringstream iss(t);
        iss >> j >> sep >> m >> sep >> d;
        return Waktu(j, m, d);
    }

    int totalTimeInSeconds() const {
        return jam * 3600 + menit * 60 + detik;
    }

    std::string toString() const {
        std::ostringstream oss;
        oss << std::setw(2) << std::setfill('0') << jam << ":"
            << std::setw(2) << std::setfill('0') << menit << ":"
            << std::setw(2) << std::setfill('0') << detik;
        return oss.str();
    }
};

Waktu difference(const Waktu& mulai, const Waktu& selesai) {
    int totalMulai = mulai.totalTimeInSeconds();
    int totalSelesai = selesai.totalTimeInSeconds();
    int selisih = totalSelesai - totalMulai;
    if (selisih < 0) selisih += 24 * 3600;
    int jam = selisih / 3600;
    int menit = (selisih % 3600) / 60;
    int detik = selisih % 60;
    return Waktu(jam, menit, detik);
}

class Mahasiswa {
public:
    std::string name, npm;
    Waktu mulai, selesai;
    Mahasiswa(const std::string& n, const std::string& np, const Waktu& m, const Waktu& s)
        : name(n), npm(np), mulai(m), selesai(s) {}
};

struct Hasil {
    std::string HM;
    std::string status;
};

Hasil calculateGrade(const Mahasiswa& mhs, Waktu& lama) {
    lama = difference(mhs.mulai, mhs.selesai);
    int totalDetik = lama.totalTimeInSeconds();
    Hasil hasil;
    hasil.status = "Lulus";
    if (totalDetik >= 0 && totalDetik < (7.5 * 60)) {
        hasil.HM = "A";
    } else if (totalDetik >= (7.5 * 60) && totalDetik < (12.5 * 60)) {
        hasil.HM = "B";
    } else if (totalDetik >= (12.5 * 60) && totalDetik < (30 * 60)) {
        hasil.HM = "C";
    } else {
        hasil.HM = "D";
        hasil.status = "Gagal";
    }
    return hasil;
}

Mahasiswa inputMahasiswa() {
    std::string name, npm, mulaiStr, selesaiStr;
    std::cout << "\nMasukkan nama: ";
    std::getline(std::cin, name);
    std::cout << "Masukkan npm: ";
    std::getline(std::cin, npm);
    std::cout << "Masukkan waktu mulai (HH:mm:ss): ";
    std::getline(std::cin, mulaiStr);
    std::cout << "Masukkan waktu selesai (HH:mm:ss): ";
    std::getline(std::cin, selesaiStr);
    Waktu mulai = Waktu::parseTime(mulaiStr);
    Waktu selesai = Waktu::parseTime(selesaiStr);
    return Mahasiswa(name, npm, mulai, selesai);
}

void outputTable(const std::vector<Mahasiswa>& mahasiswaList) {
    std::cout << "\nUjian Lari\n";
    std::cout << "-------------------------------------------------------------------------------------------------------------\n";
    std::cout << "| " << std::setw(20) << std::left << "Nama"
              << " | " << std::setw(12) << "NPM"
              << " | " << std::setw(10) << "Huruf Mutu"
              << " | " << std::setw(8) << "Status"
              << " | " << std::setw(12) << "Waktu Mulai"
              << " | " << std::setw(14) << "Waktu Selesai"
              << " | " << std::setw(10) << "Lama Lari"
              << " |\n";
    std::cout << "-------------------------------------------------------------------------------------------------------------\n";
    for (const auto& mhs : mahasiswaList) {
        Waktu lama;
        Hasil hasil = calculateGrade(mhs, lama);
        std::cout << "| " << std::setw(20) << std::left << mhs.name
                  << " | " << std::setw(12) << mhs.npm
                  << " | " << std::setw(10) << hasil.HM
                  << " | " << std::setw(8) << hasil.status
                  << " | " << std::setw(12) << mhs.mulai.toString()
                  << " | " << std::setw(14) << mhs.selesai.toString()
                  << " | " << std::setw(10) << lama.toString()
                  << " |\n";
        std::cout << "-------------------------------------------------------------------------------------------------------------\n";
    }
}

int main() {
    int n;
    std::cout << "Masukkan jumlah mahasiswa: ";
    std::cin >> n;
    std::cin.ignore();
    std::vector<Mahasiswa> mahasiswaList;
    for (int i = 0; i < n; ++i) {
        mahasiswaList.push_back(inputMahasiswa());
    }
    outputTable(mahasiswaList);
    return 0;
}

