"""
Nama - NPM    : ~ Raissa Christabel Sebayang - 140810240008
                ~ Abraham Gomes Samosir - 140810240044
                ~ Haris Herdiansyah - 140810240074
Kelas         : B
Nama File     : Soal2.py
Deskripsi     : Membuat program untuk menghitung nilai ujian lari mahasiswa
                berdasarkan waktu mulai dan selesai lari menggunakan array.
"""

class Waktu:
    def __init__(self, jam=0, menit=0, detik=0):
        self.jam = jam
        self.menit = menit
        self.detik = detik

    @staticmethod
    def parse_time(t):
        jam, menit, detik = map(int, t.split(":"))
        return Waktu(jam, menit, detik)

    def total_seconds(self):
        return self.jam * 3600 + self.menit * 60 + self.detik

    def __str__(self):
        return f"{self.jam:02d}:{self.menit:02d}:{self.detik:02d}"


def difference(mulai, selesai):
    total_mulai = mulai.total_seconds()
    total_selesai = selesai.total_seconds()
    selisih = total_selesai - total_mulai
    if selisih < 0:
        selisih += 24 * 3600
    jam = selisih // 3600
    menit = (selisih % 3600) // 60
    detik = selisih % 60
    return Waktu(jam, menit, detik)


class Mahasiswa:
    def __init__(self, name, npm, mulai, selesai):
        self.name = name
        self.npm = npm
        self.mulai = mulai
        self.selesai = selesai


def calculate_grade(mhs):
    lama = difference(mhs.mulai, mhs.selesai)
    total_detik = lama.total_seconds()
    hasil = {"status": "Lulus"}
    if 0 <= total_detik < 7.5 * 60:
        hasil["HM"] = "A"
    elif 7.5 * 60 <= total_detik < 12.5 * 60:
        hasil["HM"] = "B"
    elif 12.5 * 60 <= total_detik < 30 * 60:
        hasil["HM"] = "C"
    else:
        hasil["HM"] = "D"
        hasil["status"] = "Gagal"
    return hasil, lama


def input_mahasiswa():
    name = input("\nMasukkan nama: ")
    npm = input("Masukkan npm: ")
    mulai = Waktu.parse_time(input("Masukkan waktu mulai (HH:mm:ss): "))
    selesai = Waktu.parse_time(input("Masukkan waktu selesai (HH:mm:ss): "))
    return Mahasiswa(name, npm, mulai, selesai)


def output_table(mahasiswa_list):
    print("\nUjian Lari")
    print("-" * 100)
    print(
        f"| {'Nama':20} | {'NPM':12} | {'Huruf Mutu':10} | {'Status':8} | {'Waktu Mulai':12} | {'Waktu Selesai':14} | {'Lama Lari':10} |")
    print("-" * 100)
    for mhs in mahasiswa_list:
        hasil, lama = calculate_grade(mhs)
        print(
            f"| {mhs.name:20} | {mhs.npm:12} | {hasil['HM']:10} | {hasil['status']:8} | {mhs.mulai}      | {mhs.selesai}         | {lama}     |")
        print("-" * 100)


def main():
    n = int(input("Masukkan jumlah mahasiswa: "))
    mahasiswa_list = []
    for _ in range(n):
        mahasiswa_list.append(input_mahasiswa())
    output_table(mahasiswa_list)


if __name__ == "__main__":
    main()
