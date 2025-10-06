class Waktu:
    def __init__(self, jam=0, menit=0, detik=0):
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

    def total_detik(self):
        return (self.jam * 3600) + (self.menit * 60) + self.detik

    def __str__(self):
        return f"{self.jam:02}:{self.menit:02}:{self.detik:02}"


class Mahasiswa:
    def __init__(self, nama="", npm="", mulai=None, selesai=None):
        self.nama = nama
        self.npm = npm
        self.mulai = mulai
        self.selesai = selesai

    def get_nama(self):
        return self.nama

    def set_nama(self, nama):
        self.nama = nama

    def get_npm(self):
        return self.npm

    def set_npm(self, npm):
        self.npm = npm

    def get_mulai(self):
        return self.mulai

    def set_mulai(self, mulai):
        self.mulai = mulai

    def get_selesai(self):
        return self.selesai

    def set_selesai(self, selesai):
        self.selesai = selesai


class LarikMahasiswa:
    def __init__(self, size):
        self.daftar_mahasiswa = []
        self.size = size

    def push(self, mahasiswa):
        if len(self.daftar_mahasiswa) >= self.size:
            print("Kapasitas maksimum sudah tercapai.")
            return
        self.daftar_mahasiswa.append(mahasiswa)

    def get_mahasiswa_at(self, index):
        return self.daftar_mahasiswa[index]

    def get_all(self):
        return self.daftar_mahasiswa


def parse_time(t):
    jam, menit, detik = map(int, t.split(":"))
    return Waktu(jam, menit, detik)


def difference(mulai, selesai):
    jam_mulai, menit_mulai, detik_mulai = mulai.jam, mulai.menit, mulai.detik
    jam_selesai, menit_selesai, detik_selesai = selesai.jam, selesai.menit, selesai.detik

    selisih_detik = detik_selesai - detik_mulai
    if detik_selesai < detik_mulai:
        selisih_detik += 60
        menit_selesai -= 1

    selisih_menit = menit_selesai - menit_mulai
    if menit_selesai < menit_mulai:
        selisih_menit += 60
        jam_selesai -= 1

    selisih_jam = jam_selesai - jam_mulai

    return Waktu(selisih_jam, selisih_menit, selisih_detik)


def calculate_grade(mhs):
    hasil = {}
    selisih_waktu = difference(mhs.get_mulai(), mhs.get_selesai())
    total_detik = selisih_waktu.total_detik()

    hasil["status"] = "Lulus"
    if 0 <= total_detik < (7.5 * 60):
        hasil["HM"] = "A"
    elif (7.5 * 60) <= total_detik < (12.5 * 60):
        hasil["HM"] = "B"
    elif (12.5 * 60) <= total_detik < (30 * 60):
        hasil["HM"] = "C"
    else:
        hasil["HM"] = "D"
        hasil["status"] = "Gagal"

    return hasil


def output_builder(mhs, hasil):
    print("\n=== UJIAN LARI ===")
    print(f"Nama Mahasiswa : {mhs.get_nama()}")
    print(f"NPM            : {mhs.get_npm()}")
    print(f"Huruf Mutu     : {hasil['HM']}")
    print(f"Status         : {hasil['status']}")
    print(f"Waktu Mulai    : {mhs.get_mulai()}")
    print(f"Waktu Selesai  : {mhs.get_selesai()}")
    print(f"Lama Lari      : {difference(mhs.get_mulai(), mhs.get_selesai())}")


def main():
    n = int(input("Masukkan jumlah mahasiswa: "))
    daftar_mhs = LarikMahasiswa(n)

    for i in range(n):
        print(f"\nMahasiswa ke-{i+1}")
        nama = input("Masukkan nama: ")
        npm = input("Masukkan npm: ")
        mulai = parse_time(input("Masukkan waktu mulai (HH:MM:SS): "))
        selesai = parse_time(input("Masukkan waktu selesai (HH:MM:SS): "))
        daftar_mhs.push(Mahasiswa(nama, npm, mulai, selesai))

    for i in range(n):
        mhs = daftar_mhs.get_mahasiswa_at(i)
        hasil = calculate_grade(mhs)
        output_builder(mhs, hasil)


if __name__ == "__main__":
    main()
