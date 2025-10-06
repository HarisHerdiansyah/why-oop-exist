#include <iostream>
#include <iomanip>
#include <string>
#include <map>
using namespace std;

class Waktu {
private:
    int jam;
    int menit;
    int detik;      

public:
    Waktu() : jam(0), menit(0), detik(0) {}

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
        return (jam * 3600) +(menit * 60) + detik;
    }

    string toString () const{
        ostringstream oss;
        oss << setfill('0') << setw(2) << jam << ":"
            << setw(2) << menit << ":"
            << setw(2) << detik;
        return oss.str();
    }
};

class Mahasiswa{
private:
    string name;
    string npm;
    Waktu mulai;
    Waktu selesai;  

public:
    Mahasiswa(){}   

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

    Waktu getSelesai() const { return selesai; }
    void setSelesai(Waktu selesai) { this->selesai = selesai; }

    Waktu getMulai() const { return mulai; }
    void setMulai(Waktu mulai) { this->mulai = mulai; }

};

class LarikMahasiswa{
private:
    Mahasiswa* daftarMahasiswa;
    int size;
    int indexPointer;

public:
    LarikMahasiswa(int size) {
        this->size = size;
        this->indexPointer = 0;
        daftarMahasiswa = new Mahasiswa[size];
    }

    ~LarikMahasiswa() {
        delete[] daftarMahasiswa;
    }

    void push (const Mahasiswa& mahasiswa){
        if(indexPointer >= size){
            cout << "Maximum size of array exceeded." << endl;
            return;
        }
        daftarMahasiswa[indexPointer++] = mahasiswa;
    }
    Mahasiswa getMahasiswaAt (int index) const{
        return daftarMahasiswa[index];
    }
    int getIndexPointer() const{
        return indexPointer;
    }
};

Waktu parseTime(const string& t){
    int jam, menit, detik;
    char sep1;
    istringstream iss(t);
    iss >> jam >> sep1 >> menit >> sep1 >> detik;
    return Waktu(jam, menit, detik);
}

Mahasiswa inputMahasiswa(){
    string name, npm, mulaiStr, selesaiStr;
    cout << "Masukkan Nama: ";
    getline(cin, name);
    cout << "Masukkan NPM: ";
    getline(cin, npm);
    cout << "Masukkan Waktu Mulai (hh:mm:ss): ";
    getline(cin, mulaiStr);
    cout << "Masukkan Waktu Selesai (hh:mm:ss): ";
    getline(cin, selesaiStr);

    Waktu mulai = parseTime(mulaiStr);
    Waktu selesai = parseTime(selesaiStr);

    return Mahasiswa(name, npm, mulai, selesai);
}

Waktu difference(Waktu mulai, Waktu selesai){
    int jamMulai = mulai.getJam();
    int menitMulai = mulai.getMenit();
    int detikMulai = mulai.getDetik();          
    int jamSelesai = selesai.getJam();
    int menitSelesai = selesai.getMenit();
    int detikSelesai = selesai.getDetik();

    int selisiDetik = detikSelesai - detikMulai;
    if(detikSelesai < detikMulai){
        selisiDetik += 60;
        menitSelesai -= 1;
    }

    int selisiMenit = menitSelesai - menitMulai;    
    if(menitSelesai < menitMulai){
        selisiMenit += 60;
        jamSelesai -= 1;
    }

    int selisiJam = jamSelesai - jamMulai;

    return Waktu(selisiJam, selisiMenit, selisiDetik);
}

map<string, string> calculateGrade(Mahasiswa mhs){
    map<string, string> result;
    Waktu selisih = difference(mhs.getMulai(), mhs.getSelesai());
    int totalSeconds = selisih.totalTimeInSeconds();

    result["status"] = "Lulus";
    if(totalSeconds >=0 && totalSeconds < (7.5 * 60)){
        result["HM"] = "A";
    }else if(totalSeconds >= (7.5 * 60) && totalSeconds < (12.5 * 60)){
        result["HM"] = "B";
    }else if(totalSeconds >= (12.5 * 60) && totalSeconds < (30 * 60)){
        result["HM"] = "C";
    }else {
        result["HM"] = "D";
        result["status"] = "Gagal";
    }
    return result;
}

void outputBuilder (Mahasiswa mhs, map<string, string> hasil){
    cout << "\nUjian Lari\n";
    cout << "Nama Mahasiswa: " << mhs.getName() << endl;
    cout << "NPM Mahasiswa: " << mhs.getNpm() << endl;
    cout << "Huruf Mutu: " << hasil["HM"] << endl;
    cout << "Status: " << hasil["status"] << endl;
    cout << "Waktu Mulai: " << mhs.getMulai().toString() << endl;
    cout << "Waktu Selesai: " << mhs.getSelesai().toString() << endl;
    cout << "Lama Lari: " << difference(mhs.getMulai(), mhs.getSelesai()).toString() << endl;
}

int main(){
    int n;
    cout << "Masukkan jumlah mahasiswa: ";
    cin >> n;
    cin.ignore(); // To ignore the newline character after the integer input

    LarikMahasiswa larik(n);
    for(int i = 0; i < n; i++){
        cout << "\nInput data mahasiswa ke-" << (i + 1) << ":\n";
        Mahasiswa mhs = inputMahasiswa();
        larik.push(mhs);
    }

    for(int i = 0; i < larik.getIndexPointer(); i++){
        Mahasiswa mhs = larik.getMahasiswaAt(i);
        map<string, string> hasil = calculateGrade(mhs);
        outputBuilder(mhs, hasil);
    }

    return 0;
}