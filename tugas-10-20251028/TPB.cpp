/*
Nama - NPM    : ~ Raissa Christabel Sebayang - 140810240008
                ~ Abraham Gomes Samosir - 140810240044
                ~ Haris Herdiansyah - 140810240074
Kelas         : B
Nama File     : TPB.cpp
Deskripsi     : program primitif list TPB lari
                1. Insert firt
                2. insert last
                3. insert after
                4. delete first
                5. delete last
                6. delete after
                7. traversal
                8. searching
*/

#include <iostream>
#include <string>
#include <iomanip>
#include <limits>
#include <cstdio>
using namespace std;

class Time {
private:
    int hour, minute, second;
    
public:
    Time(int hour, int minute, int second) {
        this->hour = hour;
        this->minute = minute;
        this->second = second;
    }
    
    int getHour() { return hour; }
    void setHour(int hour) { this->hour = hour; }
    
    int getMinute() { return minute; }
    void setMinute(int minute) { this->minute = minute; }
    
    int getSecond() { return second; }
    void setSecond(int second) { this->second = second; }
};

class MhsTPB {
private:
    string nama;
    string npm;
    Time* waktuMulai;
    Time* waktuSelesai;
    
public:
    MhsTPB(string nama, string npm, Time* waktuMulai, Time* waktuSelesai) {
        this->nama = nama;
        this->npm = npm;
        this->waktuMulai = waktuMulai;
        this->waktuSelesai = waktuSelesai;
    }
    
    ~MhsTPB() {
        delete waktuMulai;
        delete waktuSelesai;
    }
    
    int getLamaUjianDetik() {
        int detikMulai = waktuMulai->getHour() * 3600 + waktuMulai->getMinute() * 60 + waktuMulai->getSecond();
        int detikSelesai = waktuSelesai->getHour() * 3600 + waktuSelesai->getMinute() * 60 + waktuSelesai->getSecond();
        return detikSelesai - detikMulai;
    }
    
    int getLamaUjian() {
        return getLamaUjianDetik() / 60;
    }
    
    string getHurufMutu() {
        int lama = getLamaUjian();
        if (lama >= 0 && lama < 7.5) {
            return "A";
        } else if (lama >= 7.5 && lama < 12.5) {
            return "B";
        } else if (lama >= 12.5 && lama < 30) {
            return "C";
        } else {
            return "D";
        }
    }
    
    string getStatus() {
        string hm = getHurufMutu();
        if (hm == "A" || hm == "B" || hm == "C") {
            return "Lulus";
        } else {
            return "Gagal";
        }
    }
    
    string getNama() { return nama; }
    void setNama(string nama) { this->nama = nama; }
    
    string getNpm() { return npm; }
    void setNpm(string npm) { this->npm = npm; }
    
    Time* getWaktuMulai() { return waktuMulai; }
    void setWaktuMulai(Time* waktuMulai) { 
        this->waktuMulai = waktuMulai;
    }
    
    Time* getWaktuSelesai() { return waktuSelesai; }
    void setWaktuSelesai(Time* waktuSelesai) { 
        this->waktuSelesai = waktuSelesai;
    }
};

class Node {
private:
    MhsTPB* mhs;
    Node* next;
    
public:
    Node(MhsTPB* mhs) {
        this->mhs = mhs;
        this->next = nullptr;
    }
    
    ~Node() {
        delete mhs;
    }
    
    MhsTPB* getMhs() { return mhs; }
    void setMhs(MhsTPB* mhs) { this->mhs = mhs; }
    
    Node* getNext() { return next; }
    void setNext(Node* next) { this->next = next; }
};

class LinkedList {
private:
    Node* head;
    
public:
    LinkedList() {
        this->head = nullptr;
    }
    
    ~LinkedList() {
        Node* current = head;
        while (current != nullptr) {
            Node* temp = current;
            current = current->getNext();
            delete temp;
        }
    }
    
    void insertFirst(MhsTPB* mhs) {
        Node* newNode = new Node(mhs);
        newNode->setNext(head);
        head = newNode;
        cout << "Data berhasil ditambahkan di awal list!" << endl;
    }
    
    void insertLast(MhsTPB* mhs) {
        if (head == nullptr) {
            head = new Node(mhs);
        } else {
            Node* current = head;
            while (current->getNext() != nullptr) {
                current = current->getNext();
            }
            current->setNext(new Node(mhs));
        }
        cout << "Data berhasil ditambahkan di akhir list!" << endl;
    }
    
    void insertAfter(MhsTPB* mhs, string npm) {
        Node* found = find(npm);
        if (found == nullptr) {
            cout << "NPM tidak ditemukan!" << endl;
            delete mhs;
            return;
        }
        
        Node* newNode = new Node(mhs);
        newNode->setNext(found->getNext());
        found->setNext(newNode);
        cout << "Data berhasil ditambahkan setelah NPM " << npm << endl;
    }
    
    void deleteFirst() {
        if (head == nullptr) {
            cout << "List kosong!" << endl;
            return;
        }
        Node* temp = head;
        head = temp->getNext();
        delete temp;
        cout << "Data pertama berhasil dihapus!" << endl;
    }
    
    void deleteLast() {
        if (head == nullptr) {
            cout << "List kosong!" << endl;
            return;
        }
        
        if (head->getNext() == nullptr) {
            delete head;
            head = nullptr;
        } else {
            Node* current = head;
            while (current->getNext()->getNext() != nullptr) {
                current = current->getNext();
            }
            Node* temp = current->getNext();
            current->setNext(nullptr);
            delete temp;
        }
        cout << "Data terakhir berhasil dihapus!" << endl;
    }
    
    void deleteAfter(string npm) {
        Node* found = find(npm);
        if (found == nullptr) {
            cout << "NPM tidak ditemukan!" << endl;
            return;
        }
        
        if (found->getNext() == nullptr) {
            cout << "Tidak ada node setelah NPM ini!" << endl;
            return;
        }
        
        Node* temp = found->getNext();
        found->setNext(temp->getNext());
        delete temp;
        cout << "Data setelah NPM " << npm << " berhasil dihapus!" << endl;
    }
    
    Node* find(string npm) {
        Node* current = head;
        while (current != nullptr) {
            if (current->getMhs()->getNpm() == npm) {
                return current;
            }
            current = current->getNext();
        }
        return nullptr;
    }
    
    void searching(string npm) {
        Node* found = find(npm);
        if (found == nullptr) {
            cout << "\nData tidak ditemukan!" << endl;
            return;
        }
        
        MhsTPB* mhs = found->getMhs();
        cout << "\n=== Data Ditemukan ===" << endl;
        cout << "Nama          : " << mhs->getNama() << endl;
        cout << "NPM           : " << mhs->getNpm() << endl;
        cout << "Waktu Mulai   : " << setfill('0') << setw(2) << mhs->getWaktuMulai()->getHour() 
             << ":" << setw(2) << mhs->getWaktuMulai()->getMinute() 
             << ":" << setw(2) << mhs->getWaktuMulai()->getSecond() << endl;
        cout << "Waktu Selesai : " << setw(2) << mhs->getWaktuSelesai()->getHour() 
             << ":" << setw(2) << mhs->getWaktuSelesai()->getMinute() 
             << ":" << setw(2) << mhs->getWaktuSelesai()->getSecond() << endl;
        int totalDetik = mhs->getLamaUjianDetik();
        int menit = totalDetik / 60;
        int detik = totalDetik % 60;
        cout << "Lama Ujian    : " << menit << " menit " << detik << " detik" << endl;
        cout << "Huruf Mutu    : " << mhs->getHurufMutu() << endl;
        cout << "Status        : " << mhs->getStatus() << endl;
    }
    
    void traversal() {
        if (head == nullptr) {
            cout << "\nList kosong!" << endl;
            return;
        }
        
        cout << "\n=== Daftar Mahasiswa TPB ===" << endl;
        cout << string(120, '=') << endl;
        cout << left << setw(20) << "Nama" 
             << setw(15) << "NPM" 
             << setw(15) << "HM" 
             << setw(15) << "Status" 
             << setw(15) << "Waktu Mulai" 
             << setw(15) << "Waktu Selesai" 
             << "Lama (menit:detik)" << endl;
        cout << string(120, '=') << endl;
        
        Node* current = head;
        while (current != nullptr) {
            MhsTPB* mhs = current->getMhs();
            cout << left << setw(20) << mhs->getNama()
                 << setw(15) << mhs->getNpm()
                 << setw(15) << mhs->getHurufMutu()
                 << setw(15) << mhs->getStatus();
            
            char timeBuffer[20];
            sprintf(timeBuffer, "%02d:%02d:%02d", 
                    mhs->getWaktuMulai()->getHour(),
                    mhs->getWaktuMulai()->getMinute(),
                    mhs->getWaktuMulai()->getSecond());
            cout << left << setw(15) << timeBuffer;
            
            sprintf(timeBuffer, "%02d:%02d:%02d",
                    mhs->getWaktuSelesai()->getHour(),
                    mhs->getWaktuSelesai()->getMinute(),
                    mhs->getWaktuSelesai()->getSecond());
            cout << left << setw(15) << timeBuffer;
            
            int totalDetik = mhs->getLamaUjianDetik();
            int menit = totalDetik / 60;
            int detik = totalDetik % 60;
            sprintf(timeBuffer, "%02d:%02d", menit, detik);
            cout << timeBuffer << endl;
            
            current = current->getNext();
        }
        cout << string(120, '=') << endl;
    }
    
    bool isEmpty() {
        return head == nullptr;
    }
};

void tampilkanMenu() {
    cout << "\n========== MENU UTAMA ==========" << endl;
    cout << "1. Insert First" << endl;
    cout << "2. Insert Last" << endl;
    cout << "3. Insert After" << endl;
    cout << "4. Delete First" << endl;
    cout << "5. Delete Last" << endl;
    cout << "6. Delete After" << endl;
    cout << "7. Traversal (Tampilkan Semua Data)" << endl;
    cout << "8. Searching (Cari Data)" << endl;
    cout << "0. Keluar" << endl;
    cout << "================================" << endl;
    cout << "Pilih menu: ";
}

bool inputWaktu(int& jam, int& menit, int& detik, const string& label) {
    cout << label << " (jam menit detik): ";
    if (cin >> jam >> menit >> detik) {
        if (jam >= 0 && jam < 24 &&
            menit >= 0 && menit < 60 &&
            detik >= 0 && detik < 60) {
            return true;
        } else {
            cout << "Error: Nilai waktu tidak valid (jam 0–23, menit/detik 0–59)!\n";
            return false;
        }
    } else {
        cout << "Error: Input waktu harus berupa angka!\n";
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
        return false;
    }
}

MhsTPB* inputDataMahasiswa() {
    string nama, npm;
    int jamMulai, menitMulai, detikMulai;
    int jamSelesai, menitSelesai, detikSelesai;

    cout << "\n=== Input Data Mahasiswa ===" << endl;
    cout << "Nama          : ";
    cin.ignore();
    getline(cin, nama);
    cout << "NPM           : ";
    getline(cin, npm);

    while (!inputWaktu(jamMulai, menitMulai, detikMulai, "Waktu Mulai"));
    while (!inputWaktu(jamSelesai, menitSelesai, detikSelesai, "Waktu Selesai"));

    cin.ignore(numeric_limits<streamsize>::max(), '\n');

    Time* waktuMulai = new Time(jamMulai, menitMulai, detikMulai);
    Time* waktuSelesai = new Time(jamSelesai, menitSelesai, detikSelesai);

    return new MhsTPB(nama, npm, waktuMulai, waktuSelesai);
}


int main() {
    LinkedList ll;
    int pilihan;
    
    cout << "========================================" << endl;
    cout << "   PROGRAM LINKED LIST TPB MAHASISWA   " << endl;
    cout << "========================================" << endl;
    
    do {
        tampilkanMenu();
        cin >> pilihan;
        
        switch(pilihan) {
            case 1: {
                MhsTPB* mhs = inputDataMahasiswa();
                ll.insertFirst(mhs);
                break;
            }
            case 2: {
                MhsTPB* mhs = inputDataMahasiswa();
                ll.insertLast(mhs);
                break;
            }
            case 3: {
                if (ll.isEmpty()) {
                    cout << "\nList masih kosong! Tambahkan data terlebih dahulu." << endl;
                    break;
                }
                string npm;
                cin.ignore();
                cout << "\nMasukkan NPM yang akan dijadikan acuan: ";
                getline(cin, npm);
                MhsTPB* mhs = inputDataMahasiswa();
                ll.insertAfter(mhs, npm);
                break;
            }
            case 4:
                ll.deleteFirst();
                break;
            case 5:
                ll.deleteLast();
                break;
            case 6: {
                if (ll.isEmpty()) {
                    cout << "\nList kosong!" << endl;
                    break;
                }
                string npm;
                cin.ignore();
                cout << "\nMasukkan NPM: ";
                getline(cin, npm);
                ll.deleteAfter(npm);
                break;
            }
            case 7:
                ll.traversal();
                break;
            case 8: {
                if (ll.isEmpty()) {
                    cout << "\nList kosong!" << endl;
                    break;
                }
                string npm;
                cin.ignore();
                cout << "\nMasukkan NPM yang dicari: ";
                getline(cin, npm);
                ll.searching(npm);
                break;
            }
            case 0:
                cout << "\nTerima kasih telah menggunakan program ini!" << endl;
                break;
            default:
                cout << "\nPilihan tidak valid!" << endl;
        }
        
    } while(pilihan != 0);
    
    return 0;
}
