#include <iostream>
#include <string>
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
    
    string getNama() { return nama; }
    void setNama(string nama) { this->nama = nama; }
    
    string getNpm() { return npm; }
    void setNpm(string npm) { this->npm = npm; }
    
    Time* getWaktuMulai() { return waktuMulai; }
    void setWaktuMulai(Time* waktuMulai) { this->waktuMulai = waktuMulai; }
    
    Time* getWaktuSelesai() { return waktuSelesai; }
    void setWaktuSelesai(Time* waktuSelesai) { this->waktuSelesai = waktuSelesai; }
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
    
    void prepend(MhsTPB* mhs) {
        if (head == nullptr) {
            head = new Node(mhs);
        } else {
            Node* newNode = new Node(mhs);
            newNode->setNext(head);
            head = newNode;
        }
    }
    
    void append(MhsTPB* mhs) {
        if (head == nullptr) {
            head = new Node(mhs);
            return;
        }
        
        Node* current = head;
        while (current->getNext() != nullptr) {
            current = current->getNext();
        }
        current->setNext(new Node(mhs));
    }
    
    void insert(MhsTPB* mhs, int pos) {
        if (head == nullptr) {
            head = new Node(mhs);
            return;
        }
        
        Node* current = head;
        for (int i = 0; i < pos - 1 && current->getNext() != nullptr; i++) {
            current = current->getNext();
        }
        
        Node* newNode = new Node(mhs);
        newNode->setNext(current->getNext());
        current->setNext(newNode);
    }
    
    void deleteFirst() {
        if (head == nullptr) return;
        Node* temp = head;
        head = temp->getNext();
        delete temp;
    }
    
    void deleteLast() {
        if (head == nullptr) return;
        
        if (head->getNext() == nullptr) {
            delete head;
            head = nullptr;
            return;
        }
        
        Node* current = head;
        while (current->getNext()->getNext() != nullptr) {
            current = current->getNext();
        }
        Node* temp = current->getNext();
        current->setNext(nullptr);
        delete temp;
    }
    
    Node* find(string npm) {
        if (head == nullptr) return nullptr;
        
        if (head->getNext() == nullptr) {
            return head->getMhs()->getNpm() == npm ? head : nullptr;
        }
        
        Node* current = head;
        while (current != nullptr) {
            if (current->getMhs()->getNpm() == npm) {
                return current;
            }
            current = current->getNext();
        }
        return nullptr;
    }
    
    void printNode() {
        Node* current = head;
        while (current != nullptr) {
            cout << current->getMhs()->getNama() << endl;
            current = current->getNext();
        }
    }
};

int main() {
    LinkedList ll;
    
    Time* t1 = new Time(8, 0, 0);
    Time* t2 = new Time(10, 0, 0);
    MhsTPB* mhs1 = new MhsTPB("Budi", "123456", t1, t2);
    
    ll.append(mhs1);
    ll.printNode();
    
    return 0;
}