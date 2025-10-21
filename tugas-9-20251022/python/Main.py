"""
Nama - NPM    : ~ Raissa Christabel Sebayang - 140810240008
                ~ Abraham Gomes Samosir - 140810240044
                ~ Haris Herdiansyah - 140810240074
Kelas         : B
Nama File     : Main.java
Deskripsi     : program inheritance dengan Java, C++ dan python masalah Parkir  di suatu pelabuhan:
                Class : 
                Waktu (date, time)   perhatikan kabisat
                Person/ pemilik, 
                Kendaraan  : (mobil, motor, truk), dll  --> inheritance
                Buat selengkap mungkin (Data array)
"""

from enum import Enum
from datetime import datetime, timedelta
from typing import List, Dict


class JenisKendaraan(Enum):
    MOBIL = (15000, 30000)
    MOTOR = (7000, 15000)
    TRUK = (40000, 80000)
    
    def __init__(self, tarif_per_jam: int, tarif_menginap: int):
        self._tarif_per_jam = tarif_per_jam
        self._tarif_menginap = tarif_menginap
    
    def get_tarif_per_jam(self) -> int:
        return self._tarif_per_jam
    
    def get_tarif_menginap(self) -> int:
        return self._tarif_menginap


class Time:
    def __init__(self, hour: int, minute: int, seconds: int):
        self._hour = hour
        self._minute = minute
        self._seconds = seconds
    
    def get_hour(self) -> int:
        return self._hour
    
    def set_hour(self, hour: int):
        self._hour = hour
    
    def get_minute(self) -> int:
        return self._minute
    
    def set_minute(self, minute: int):
        self._minute = minute
    
    def get_seconds(self) -> int:
        return self._seconds
    
    def set_seconds(self, seconds: int):
        self._seconds = seconds
    
    def __str__(self) -> str:
        return f"Time{{hour={self._hour}, minute={self._minute}, seconds={self._seconds}}}"


class Dates:
    def __init__(self, year: int, month: int, day: int):
        self._year = year
        self._month = month
        self._day = day
    
    def get_year(self) -> int:
        return self._year
    
    def set_year(self, year: int):
        self._year = year
    
    def get_month(self) -> int:
        return self._month
    
    def set_month(self, month: int):
        self._month = month
    
    def get_day(self) -> int:
        return self._day
    
    def set_day(self, day: int):
        self._day = day
    
    def __str__(self) -> str:
        return f"Dates{{year={self._year}, month={self._month}, day={self._day}}}"


class Waktu:
    def __init__(self, dates: Dates, time: Time):
        self._dates = dates
        self._time = time
    
    def get_dates(self) -> Dates:
        return self._dates
    
    def set_dates(self, dates: Dates):
        self._dates = dates
    
    def get_time(self) -> Time:
        return self._time
    
    def set_time(self, time: Time):
        self._time = time


class Pemilik:
    def __init__(self, name: str):
        self._name = name
    
    def get_name(self) -> str:
        return self._name
    
    def set_name(self, name: str):
        self._name = name


class Kendaraan:
    def __init__(self, jenis_kendaraan: JenisKendaraan, pemilik: Pemilik, 
                 waktu_masuk: Waktu, waktu_keluar: Waktu, no_kendaraan: str):
        self._jenis_kendaraan = jenis_kendaraan
        self._pemilik = pemilik
        self._waktu_masuk = waktu_masuk
        self._waktu_keluar = waktu_keluar
        self._no_kendaraan = no_kendaraan
    
    def get_jenis_kendaraan(self) -> JenisKendaraan:
        return self._jenis_kendaraan
    
    def set_jenis_kendaraan(self, jenis_kendaraan: JenisKendaraan):
        self._jenis_kendaraan = jenis_kendaraan
    
    def get_pemilik(self) -> Pemilik:
        return self._pemilik
    
    def set_pemilik(self, pemilik: Pemilik):
        self._pemilik = pemilik
    
    def get_waktu_masuk(self) -> Waktu:
        return self._waktu_masuk
    
    def set_waktu_masuk(self, waktu_masuk: Waktu):
        self._waktu_masuk = waktu_masuk
    
    def get_waktu_keluar(self) -> Waktu:
        return self._waktu_keluar
    
    def set_waktu_keluar(self, waktu_keluar: Waktu):
        self._waktu_keluar = waktu_keluar
    
    def get_no_kendaraan(self) -> str:
        return self._no_kendaraan
    
    def set_no_kendaraan(self, no_kendaraan: str):
        self._no_kendaraan = no_kendaraan


class Mobil(Kendaraan):
    def __init__(self, jenis_kendaraan: JenisKendaraan, pemilik: Pemilik,
                 waktu_masuk: Waktu, waktu_keluar: Waktu, no_kendaraan: str):
        super().__init__(jenis_kendaraan, pemilik, waktu_masuk, waktu_keluar, no_kendaraan)


class Motor(Kendaraan):
    def __init__(self, jenis_kendaraan: JenisKendaraan, pemilik: Pemilik,
                 waktu_masuk: Waktu, waktu_keluar: Waktu, no_kendaraan: str):
        super().__init__(jenis_kendaraan, pemilik, waktu_masuk, waktu_keluar, no_kendaraan)


class Truk(Kendaraan):
    def __init__(self, jenis_kendaraan: JenisKendaraan, pemilik: Pemilik,
                 waktu_masuk: Waktu, waktu_keluar: Waktu, no_kendaraan: str):
        super().__init__(jenis_kendaraan, pemilik, waktu_masuk, waktu_keluar, no_kendaraan)


daftar_kendaraan: List[Kendaraan] = []


def date_time_parser(date_time: str) -> datetime:
    dt = date_time.split(" ")
    d = dt[0].split("/")
    t = dt[1].split(":")
    return datetime(
        int(d[0]), int(d[1]), int(d[2]),
        int(t[0]), int(t[1]), int(t[2])
    )


def input_date_time(option: int) -> Dict[str, datetime]:
    date_map = {}
    
    if option == 1:  # datetime sistem
        input("Waktu masuk (Tekan enter): ")
        date_map["in"] = datetime.now()
        input("Waktu keluar (Tekan enter): ")
        date_map["out"] = datetime.now()
    elif option == 2:  # datetime manual
        waktu_masuk_str = input("Waktu masuk (yyyy/MM/dd HH:mm:ss): ")
        date_map["in"] = date_time_parser(waktu_masuk_str)
        waktu_keluar_str = input("Waktu keluar (yyyy/MM/dd HH:mm:ss): ")
        date_map["out"] = date_time_parser(waktu_keluar_str)
    
    return date_map


def input_object() -> Kendaraan:
    nama = input("Nama pemilik: ")
    jenis_str = input("Jenis kendaraan: ").upper()
    jenis_kendaraan = JenisKendaraan[jenis_str]
    nomor_kendaraan = input("Nomor kendaraan: ")
    option = int(input("Opsi masukkan waktu, (1) Sistem; (2) Manual: "))
    
    user_date_time = input_date_time(option)
    time_in = user_date_time["in"]
    time_out = user_date_time["out"]
    
    pemilik = Pemilik(nama)
    waktu_masuk = Waktu(
        Dates(time_in.year, time_in.month, time_in.day),
        Time(time_in.hour, time_in.minute, time_in.second)
    )
    waktu_keluar = Waktu(
        Dates(time_out.year, time_out.month, time_out.day),
        Time(time_out.hour, time_out.minute, time_out.second)
    )
    
    if jenis_kendaraan == JenisKendaraan.MOBIL:
        return Mobil(jenis_kendaraan, pemilik, waktu_masuk, waktu_keluar, nomor_kendaraan)
    elif jenis_kendaraan == JenisKendaraan.MOTOR:
        return Motor(jenis_kendaraan, pemilik, waktu_masuk, waktu_keluar, nomor_kendaraan)
    elif jenis_kendaraan == JenisKendaraan.TRUK:
        return Truk(jenis_kendaraan, pemilik, waktu_masuk, waktu_keluar, nomor_kendaraan)


def to_local_date_time(w: Waktu) -> datetime:
    return datetime(
        w.get_dates().get_year(),
        w.get_dates().get_month(),
        w.get_dates().get_day(),
        w.get_time().get_hour(),
        w.get_time().get_minute(),
        w.get_time().get_seconds()
    )


def hitung_biaya_parkir(k: Kendaraan) -> int:
    waktu_masuk = to_local_date_time(k.get_waktu_masuk())
    waktu_keluar = to_local_date_time(k.get_waktu_keluar())
    
    if waktu_keluar.date() != waktu_masuk.date():
        selisih_hari = (waktu_keluar.date() - waktu_masuk.date()).days
        return selisih_hari * k.get_jenis_kendaraan().get_tarif_menginap()
    
    selisih = waktu_keluar - waktu_masuk
    selisih_detik = int(selisih.total_seconds())
    
    if selisih_detik <= 0:
        return 0
    
    total_jam = selisih_detik // 3600
    if selisih_detik % 3600 > 0:
        total_jam += 1
    
    return total_jam * k.get_jenis_kendaraan().get_tarif_per_jam()


def output(k: Kendaraan):
    print(f"\nNo. Kendaraan: {k.get_no_kendaraan()}")
    print(f"Nama Pemilik: {k.get_pemilik().get_name()}")
    print(f"Jenis Kendaraan: {k.get_jenis_kendaraan().name}")
    print(f"Waktu Masuk: {k.get_waktu_masuk().get_dates()}{k.get_waktu_masuk().get_time()}")
    print(f"Waktu Keluar: {k.get_waktu_keluar().get_dates()}{k.get_waktu_keluar().get_time()}")
    print(f"Biaya Parkir: {hitung_biaya_parkir(k)}")


def main():
    n = int(input("Jumlah kendaraan: "))
    
    for i in range(1, n + 1):
        print(f"\nKendaraan ke-{i}")
        daftar_kendaraan.append(input_object())
    
    for i in range(1, n + 1):
        output(daftar_kendaraan[i - 1])


if __name__ == "__main__":
    main()