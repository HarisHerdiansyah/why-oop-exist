/*
Nama - NPM    : ~ Raissa Christabel Sebayang - 140810240008
                ~ Abraham Gomes Samosir - 140810240044
                ~ Haris Herdiansyah - 140810240074
Kelas         : B
Nama File     : Soal1.cpp
Deskripsi     : Membuat program untuk menghitung nilai ujian lari mahasiswa
                berdasarkan waktu mulai dan selesai lari menggunakan konsep OOP.
*/

#include <iostream>
#include <iomanip>
#include <sstream>
#include <string>
#include <map>
using namespace std;

class Waktu {
private:
    int jam;
    int menit;
    int detik;

public:
    Waktu() : jam(0), menit(0), detik(0) {
    }

    Waktu(int jam, int menit, int detik) {
        this->jam = jam;
        this->menit = menit;
        this->detik = detik;
    }

    int getJam() const { return jam; }
    void setJam(int jam) { this->jam = jam; }

    int getMenit() const { return menit; }
    void setMenit(int menit) { this->menit = menit; }

    int getDetik() const { return detik; }
    void setDetik(int detik) { this->detik = detik; }

    int totalTimeInSeconds() const {
        return (jam * 3600) + (menit * 60) + detik;
    }

    string toString() const {
        stringstream ss;
        ss << setfill('0') << setw(2) << jam << ":"
                << setfill('0') << setw(2) << menit << ":"
                << setfill('0') << setw(2) << detik;
        return ss.str();
    }
};

class Mahasiswa {
private:
    string name;
    string npm;
    Waktu mulai;
    Waktu selesai;

public:
    Mahasiswa() {
    }

    Mahasiswa(string name, string npm, Waktu mulai, Waktu selesai) {
        this->name = name;
        this->npm = npm;
        this->mulai = mulai;
        this->selesai = selesai;
    }

    string getName() const { return name; }
    void setName(string name) { this->name = name; }

    string getNpm() const { return npm; }
    void setNpm(string npm) { this->npm = npm; }

    Waktu getMulai() const { return mulai; }
    void setMulai(Waktu mulai) { this->mulai = mulai; }

    Waktu getSelesai() const { return selesai; }
    void setSelesai(Waktu selesai) { this->selesai = selesai; }
};

Waktu parseTime(string t) {
    int jam, menit, detik;
    char sep;
    stringstream ss(t);
    ss >> jam >> sep >> menit >> sep >> detik;
    return Waktu(jam, menit, detik);
}

Mahasiswa input() {
    string name, npm, mulaiStr, selesaiStr;

    cout << "Masukkan Nama: ";
    getline(cin, name);
    cout << "Masukkan NPM: ";
    getline(cin, npm);
    cout << "Masukkan Waktu Mulai (hh:mm:ss): ";
    getline(cin, mulaiStr);
    cout << "Masukkan Waktu Selesai (hh:mm:ss): ";
    getline(cin, selesaiStr);

    return Mahasiswa(name, npm, parseTime(mulaiStr), parseTime(selesaiStr));
}

Waktu difference(Waktu mulai, Waktu selesai) {
    int totalDetikMulai = mulai.totalTimeInSeconds();
    int totalDetikSelesai = selesai.totalTimeInSeconds();
    int selisihDetikTotal = totalDetikSelesai - totalDetikMulai;

    if (selisihDetikTotal < 0) selisihDetikTotal += 24 * 3600;

    int selisihJam = selisihDetikTotal / 3600;
    int selisihMenit = (selisihDetikTotal % 3600) / 60;
    int selisihDetik = selisihDetikTotal % 60;

    return Waktu(selisihJam, selisihMenit, selisihDetik);
}

map<string, string> calculateGrade(Mahasiswa mhs) {
    map<string, string> hasil;
    Waktu selisihWaktu = difference(mhs.getMulai(), mhs.getSelesai());
    int totalDetik = selisihWaktu.totalTimeInSeconds();

    hasil["status"] = "Lulus";
    if (totalDetik >= 0 && totalDetik < (7.5 * 60)) {
        hasil["HM"] = "A";
    } else if (totalDetik >= (7.5 * 60) && totalDetik < (12.5 * 60)) {
        hasil["HM"] = "B";
    } else if (totalDetik >= (12.5 * 60) && totalDetik < (30 * 60)) {
        hasil["HM"] = "C";
    } else {
        hasil["HM"] = "D";
        hasil["status"] = "Gagal";
    }
    return hasil;
}

void outputBuilder(Mahasiswa mhs, map<string, string> hasil) {
    cout << "\n=== Hasil Ujian Lari ===\n";
    cout << left
         << "| " << setw(20) << "Nama"
         << "| " << setw(12) << "NPM"
         << "| " << setw(12) << "Waktu Mulai"
         << "| " << setw(14) << "Waktu Selesai"
         << "| " << setw(12) << "Lama Lari"
         << "| " << setw(12) << "Huruf Mutu"
         << "| " << setw(10) << "Status" << "|" << endl;
    cout << string(108, '-') << endl;
    cout << left
         << "| " << setw(20) << mhs.getName()
         << "| " << setw(12) << mhs.getNpm()
         << "| " << setw(12) << mhs.getMulai().toString()
         << "| " << setw(14) << mhs.getSelesai().toString()
         << "| " << setw(12) << difference(mhs.getMulai(), mhs.getSelesai()).toString()
         << "| " << setw(12) << hasil["HM"]
         << "| " << setw(10) << hasil["status"] << "|" << endl;
    cout << string(108, '-') << endl;
}

int main() {
    Mahasiswa mhs = input();
    map<string, string> hasil = calculateGrade(mhs);
    outputBuilder(mhs, hasil);
    return 0;
}
