class Waktu:
    def __init__(self, jam, menit, detik):
        self.jam = jam
        self.menit = menit
        self.detik = detik

    def get_jam(self):
        return self.jam

    def set_jam(self, jam):
        self.jam = jam

    def get_menit(self):
        return self.menit

    def set_menit(self, menit):
        self.menit = menit

    def get_detik(self):
        return self.detik

    def set_detik(self, detik):
        self.detik = detik

    def conv_to_second(self):
        return (self.jam * 3600) + (self.menit * 60) + self.detik

    def __str__(self):
        return f"{self.jam:02}:{self.menit:02}:{self.detik:02}"


class MahasiswaTPB:
    def __init__(self, nama, npm):
        self.nama = nama
        self.npm = npm

    def get_nama(self):
        return self.nama

    def set_nama(self, nama):
        self.nama = nama

    def get_npm(self):
        return self.npm

    def set_npm(self, npm):
        self.npm = npm


class Main:
    def __init__(self):
        pass

    def input_mhs(self):
        nama = input("Masukkan nama : ")
        npm = input("Masukkan npm : ")
        return MahasiswaTPB(nama, npm)

    def input_waktu(self):
        jam = int(input("Masukkan jam : "))
        menit = int(input("Masukkan menit : "))
        detik = int(input("Masukkan detik : "))

        if not (0 <= jam < 24 and 0 <= menit < 60 and 0 <= detik < 60):
            print("Input waktu tidak valid! Harus dalam format 0-23 jam, 0-59 menit, 0-59 detik.")
            exit()

        return Waktu(jam, menit, detik)

    def get_time_diff(self, start, end):
        start_sec = start.conv_to_second()
        end_sec = end.conv_to_second()

        diff_sec = end_sec - start_sec
        if diff_sec < 0:
            diff_sec += 24 * 3600

        jam = diff_sec // 3600
        sisa = diff_sec % 3600
        menit = sisa // 60
        detik = sisa % 60

        return Waktu(jam, menit, detik)

    def get_grade(self, diff):
        total_detik = diff.conv_to_second()

        if 0 <= total_detik < 7.5 * 60:
            return "A", "Lulus"
        elif 7.5 * 60 <= total_detik < 12.5 * 60:
            return "B", "Lulus"
        elif 12.5 * 60 <= total_detik < 30 * 60:
            return "C", "Lulus"
        else:
            return "D", "Gagal"

    def execute(self):
        mhs = self.input_mhs()

        print("\nMasukkan waktu mulai:")
        start = self.input_waktu()

        print("\nMasukkan waktu selesai:")
        end = self.input_waktu()

        diff = self.get_time_diff(start, end)
        hm, status = self.get_grade(diff)

        print("\n===== HASIL UJIAN LARI =====")
        print(f"Nama Mahasiswa : {mhs.get_nama()}")
        print(f"NPM            : {mhs.get_npm()}")
        print(f"Waktu Mulai    : {start}")
        print(f"Waktu Selesai  : {end}")
        print(f"Lama Lari      : {diff}")
        print(f"Huruf Mutu     : {hm}")
        print(f"Status         : {status}")


def main():
    program = Main()
    program.execute()


if __name__ == "__main__":
    main()
