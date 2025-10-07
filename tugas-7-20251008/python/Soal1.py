"""
Nama - NPM    : ~ Raissa Christabel Sebayang - 140810240008
                ~ Abraham Gomes Samosir - 140810240044
                ~ Haris Herdiansyah - 140810240074
Kelas         : B
Nama File     : Soal1.py
Deskripsi     : Membuat program untuk menghitung nilai ujian lari mahasiswa
                berdasarkan waktu mulai dan selesai lari menggunakan konsep OOP.
"""


class Waktu:
    def __init__(self, jam, menit, detik):
        self.jam = jam
        self.menit = menit
        self.detik = detik

    def total_time_in_seconds(self):
        return (self.jam * 3600) + (self.menit * 60) + self.detik

    def __str__(self):
        return f"{self.jam:02}:{self.menit:02}:{self.detik:02}"


class MahasiswaTPB:
    def __init__(self, nama, npm, mulai, selesai):
        self.nama = nama
        self.npm = npm
        self.mulai = mulai
        self.selesai = selesai

    def get_nama(self): return self.nama
    def get_npm(self): return self.npm
    def get_mulai(self): return self.mulai
    def get_selesai(self): return self.selesai


class Main:
    def parse_time(self, t):
        h, m, s = map(int, t.split(":"))
        return Waktu(h, m, s)

    def input_data(self):
        nama = input("Masukkan nama: ")
        npm = input("Masukkan npm: ")
        mulai = self.parse_time(input("Masukkan waktu mulai (HH:mm:ss): "))
        selesai = self.parse_time(input("Masukkan waktu selesai (HH:mm:ss): "))
        return MahasiswaTPB(nama, npm, mulai, selesai)

    def difference(self, start, end):
        total_mulai = start.total_time_in_seconds()
        total_selesai = end.total_time_in_seconds()
        selisih = total_selesai - total_mulai
        if selisih < 0: selisih += 24 * 3600
        jam = selisih // 3600
        menit = (selisih % 3600) // 60
        detik = selisih % 60
        return Waktu(jam, menit, detik)

    def calculate_grade(self, mhs):
        selisih = self.difference(mhs.get_mulai(), mhs.get_selesai())
        total_detik = selisih.total_time_in_seconds()
        if 0 <= total_detik < 7.5 * 60:
            return {"HM": "A", "status": "Lulus"}
        elif 7.5 * 60 <= total_detik < 12.5 * 60:
            return {"HM": "B", "status": "Lulus"}
        elif 12.5 * 60 <= total_detik < 30 * 60:
            return {"HM": "C", "status": "Lulus"}
        else:
            return {"HM": "D", "status": "Gagal"}

    def output_builder(self, mhs, hasil):
        print("\n=== Hasil Ujian Lari ===")
        print(
            "| " + "Nama".ljust(20) +
            "| " + "NPM".ljust(12) +
            "| " + "Waktu Mulai".ljust(12) +
            "| " + "Waktu Selesai".ljust(14) +
            "| " + "Lama Lari".ljust(12) +
            "| " + "Huruf Mutu".ljust(12) +
            "| " + "Status".ljust(10) + "|"
        )
        print("-" * 108)
        print(
            "| " + mhs.get_nama().ljust(20) +
            "| " + mhs.get_npm().ljust(12) +
            "| " + str(mhs.get_mulai()).ljust(12) +
            "| " + str(mhs.get_selesai()).ljust(14) +
            "| " + str(self.difference(mhs.get_mulai(), mhs.get_selesai())).ljust(12) +
            "| " + hasil["HM"].ljust(12) +
            "| " + hasil["status"].ljust(10) + "|"
        )
        print("-" * 108)

    def execute(self):
        mhs = self.input_data()
        hasil = self.calculate_grade(mhs)
        self.output_builder(mhs, hasil)


def main():
    program = Main()
    program.execute()


if __name__ == "__main__":
    main()
