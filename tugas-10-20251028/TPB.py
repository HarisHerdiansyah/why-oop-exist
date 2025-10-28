"""
Nama - NPM    : ~ Raissa Christabel Sebayang - 140810240008
                ~ Abraham Gomes Samosir - 140810240044
                ~ Haris Herdiansyah - 140810240074
Kelas         : B
Nama File     : TPB.py
Deskripsi     : program primitif list TPB lari
                1. Insert firt
                2. insert last
                3. insert after
                4. delete first
                5. delete last
                6. delete after
                7. traversal
                8. searching
"""

class Time:
    def __init__(self, hour, minute, second):
        self.hour = hour
        self.minute = minute
        self.second = second
    
    def get_hour(self):
        return self.hour
    
    def set_hour(self, hour):
        self.hour = hour
    
    def get_minute(self):
        return self.minute
    
    def set_minute(self, minute):
        self.minute = minute
    
    def get_second(self):
        return self.second
    
    def set_second(self, second):
        self.second = second


class MhsTPB:
    def __init__(self, nama, npm, waktu_mulai, waktu_selesai):
        self.nama = nama
        self.npm = npm
        self.waktu_mulai = waktu_mulai
        self.waktu_selesai = waktu_selesai
    
    def get_lama_ujian_detik(self):
        detik_mulai = self.waktu_mulai.get_hour() * 3600 + self.waktu_mulai.get_minute() * 60 + self.waktu_mulai.get_second()
        detik_selesai = self.waktu_selesai.get_hour() * 3600 + self.waktu_selesai.get_minute() * 60 + self.waktu_selesai.get_second()
        return detik_selesai - detik_mulai
    
    def get_lama_ujian(self):
        return self.get_lama_ujian_detik() // 60
    
    def get_huruf_mutu(self):
        lama = self.get_lama_ujian()
        if lama >= 0 and lama < 7.5:
            return "A"
        elif lama >= 7.5 and lama < 12.5:
            return "B"
        elif lama >= 12.5 and lama < 30:
            return "C"
        else:
            return "D"
    
    def get_status(self):
        hm = self.get_huruf_mutu()
        if hm == "A" or hm == "B" or hm == "C":
            return "Lulus"
        else:
            return "Gagal"
    
    def get_nama(self):
        return self.nama
    
    def set_nama(self, nama):
        self.nama = nama
    
    def get_npm(self):
        return self.npm
    
    def set_npm(self, npm):
        self.npm = npm
    
    def get_waktu_mulai(self):
        return self.waktu_mulai
    
    def set_waktu_mulai(self, waktu_mulai):
        self.waktu_mulai = waktu_mulai
    
    def get_waktu_selesai(self):
        return self.waktu_selesai
    
    def set_waktu_selesai(self, waktu_selesai):
        self.waktu_selesai = waktu_selesai


class Node:
    def __init__(self, mhs):
        self.mhs = mhs
        self.next = None
    
    def get_mhs(self):
        return self.mhs
    
    def set_mhs(self, mhs):
        self.mhs = mhs
    
    def get_next(self):
        return self.next
    
    def set_next(self, next_node):
        self.next = next_node


class LinkedList:
    def __init__(self):
        self.head = None
    
    def insert_first(self, mhs):
        new_node = Node(mhs)
        new_node.set_next(self.head)
        self.head = new_node
        print("Data berhasil ditambahkan di awal list!")
    
    def insert_last(self, mhs):
        if self.head is None:
            self.head = Node(mhs)
        else:
            current = self.head
            while current.get_next() is not None:
                current = current.get_next()
            current.set_next(Node(mhs))
        print("Data berhasil ditambahkan di akhir list!")
    
    def insert_after(self, mhs, npm):
        found = self.find(npm)
        if found is None:
            print("NPM tidak ditemukan!")
            return
        
        new_node = Node(mhs)
        new_node.set_next(found.get_next())
        found.set_next(new_node)
        print(f"Data berhasil ditambahkan setelah NPM {npm}")
    
    def delete_first(self):
        if self.head is None:
            print("List kosong!")
            return
        self.head = self.head.get_next()
        print("Data pertama berhasil dihapus!")
    
    def delete_last(self):
        if self.head is None:
            print("List kosong!")
            return
        
        if self.head.get_next() is None:
            self.head = None
        else:
            current = self.head
            while current.get_next().get_next() is not None:
                current = current.get_next()
            current.set_next(None)
        print("Data terakhir berhasil dihapus!")
    
    def delete_after(self, npm):
        found = self.find(npm)
        if found is None:
            print("NPM tidak ditemukan!")
            return
        
        if found.get_next() is None:
            print("Tidak ada node setelah NPM ini!")
            return
        
        found.set_next(found.get_next().get_next())
        print(f"Data setelah NPM {npm} berhasil dihapus!")
    
    def find(self, npm):
        current = self.head
        while current is not None:
            if current.get_mhs().get_npm() == npm:
                return current
            current = current.get_next()
        return None
    
    def searching(self, npm):
        found = self.find(npm)
        if found is None:
            print("\nData tidak ditemukan!")
            return
        
        mhs = found.get_mhs()
        print("\n=== Data Ditemukan ===")
        print(f"Nama          : {mhs.get_nama()}")
        print(f"NPM           : {mhs.get_npm()}")
        print(f"Waktu Mulai   : {mhs.get_waktu_mulai().get_hour():02d}:{mhs.get_waktu_mulai().get_minute():02d}:{mhs.get_waktu_mulai().get_second():02d}")
        print(f"Waktu Selesai : {mhs.get_waktu_selesai().get_hour():02d}:{mhs.get_waktu_selesai().get_minute():02d}:{mhs.get_waktu_selesai().get_second():02d}")
        total_detik = mhs.get_lama_ujian_detik()
        menit = total_detik // 60
        detik = total_detik % 60
        print(f"Lama Ujian    : {menit} menit {detik} detik")
        print(f"Huruf Mutu    : {mhs.get_huruf_mutu()}")
        print(f"Status        : {mhs.get_status()}")
    
    def traversal(self):
        if self.head is None:
            print("\nList kosong!")
            return
        
        print("\n=== Daftar Mahasiswa TPB ===")
        print("=" * 120)
        print(f"{'Nama':<20}{'NPM':<15}{'HM':<15}{'Status':<15}{'Waktu Mulai':<15}{'Waktu Selesai':<15}{'Lama (menit:detik)':<20}")
        print("=" * 120)
        
        current = self.head
        while current is not None:
            mhs = current.get_mhs()
            waktu_mulai = f"{mhs.get_waktu_mulai().get_hour():02d}:{mhs.get_waktu_mulai().get_minute():02d}:{mhs.get_waktu_mulai().get_second():02d}"
            waktu_selesai = f"{mhs.get_waktu_selesai().get_hour():02d}:{mhs.get_waktu_selesai().get_minute():02d}:{mhs.get_waktu_selesai().get_second():02d}"
            
            total_detik = mhs.get_lama_ujian_detik()
            menit = total_detik // 60
            detik = total_detik % 60
            lama = f"{menit:02d}:{detik:02d}"
            
            print(f"{mhs.get_nama():<20}{mhs.get_npm():<15}{mhs.get_huruf_mutu():<15}{mhs.get_status():<15}{waktu_mulai:<15}{waktu_selesai:<15}{lama:<20}")
            
            current = current.get_next()
        print("=" * 120)
    
    def is_empty(self):
        return self.head is None


def tampilkan_menu():
    print("\n========== MENU UTAMA ==========")
    print("1. Insert First")
    print("2. Insert Last")
    print("3. Insert After")
    print("4. Delete First")
    print("5. Delete Last")
    print("6. Delete After")
    print("7. Traversal (Tampilkan Semua Data)")
    print("8. Searching (Cari Data)")
    print("0. Keluar")
    print("================================")
    print("Pilih menu: ", end="")


def input_waktu(label):
    while True:
        try:
            print(f"{label} (jam menit detik): ", end="")
            jam, menit, detik = map(int, input().split())
            if (0 <= jam < 24 and 0 <= menit < 60 and 0 <= detik < 60):
                return jam, menit, detik
            else:
                print("Error: Nilai waktu tidak valid (jam 0–23, menit/detik 0–59)!")
        except ValueError:
            print("Error: Input waktu harus berupa angka!")


def input_data_mahasiswa():
    print("\n=== Input Data Mahasiswa ===")
    nama = input("Nama          : ")
    npm = input("NPM           : ")
    
    jam_mulai, menit_mulai, detik_mulai = input_waktu("Waktu Mulai")
    jam_selesai, menit_selesai, detik_selesai = input_waktu("Waktu Selesai")
    
    waktu_mulai = Time(jam_mulai, menit_mulai, detik_mulai)
    waktu_selesai = Time(jam_selesai, menit_selesai, detik_selesai)
    
    return MhsTPB(nama, npm, waktu_mulai, waktu_selesai)


def main():
    ll = LinkedList()
    
    print("========================================")
    print("   PROGRAM LINKED LIST TPB MAHASISWA   ")
    print("========================================")
    
    while True:
        tampilkan_menu()
        try:
            pilihan = int(input())
        except ValueError:
            print("\nPilihan tidak valid!")
            continue
        
        if pilihan == 1:
            mhs = input_data_mahasiswa()
            ll.insert_first(mhs)
        elif pilihan == 2:
            mhs = input_data_mahasiswa()
            ll.insert_last(mhs)
        elif pilihan == 3:
            if ll.is_empty():
                print("\nList masih kosong! Tambahkan data terlebih dahulu.")
            else:
                npm = input("\nMasukkan NPM yang akan dijadikan acuan: ")
                mhs = input_data_mahasiswa()
                ll.insert_after(mhs, npm)
        elif pilihan == 4:
            ll.delete_first()
        elif pilihan == 5:
            ll.delete_last()
        elif pilihan == 6:
            if ll.is_empty():
                print("\nList kosong!")
            else:
                npm = input("\nMasukkan NPM: ")
                ll.delete_after(npm)
        elif pilihan == 7:
            ll.traversal()
        elif pilihan == 8:
            if ll.is_empty():
                print("\nList kosong!")
            else:
                npm = input("\nMasukkan NPM yang dicari: ")
                ll.searching(npm)
        elif pilihan == 0:
            print("\nTerima kasih telah menggunakan program ini!")
            break
        else:
            print("\nPilihan tidak valid!")


if __name__ == "__main__":
    main()