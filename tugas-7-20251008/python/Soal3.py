from datetime import datetime

class JenisKendaraan:
    MOTOR = "MOTOR"
    MOBIL = "MOBIL"

class Status:
    REGULAR = "REGULAR"
    MENGINAP = "MENGINAP"

class Time:
    def __init__(self, hour=0, minute=0, second=0):
        self.__hour = hour
        self.__minute = minute
        self.__second = second

    def get_hour(self):
        return self.__hour

    def get_minute(self):
        return self.__minute

    def get_second(self):
        return self.__second

    def __str__(self):
        return f"{self.__hour:02d}:{self.__minute:02d}:{self.__second:02d}"

class Date:
    def __init__(self, year=0, month=0, day=0):
        self.__year = year
        self.__month = month
        self.__day = day

    def get_year(self):
        return self.__year

    def get_month(self):
        return self.__month

    def get_day(self):
        return self.__day

    def __str__(self):
        return f"{self.__year:04d}/{self.__month:02d}/{self.__day:02d}"

class Waktu:
    def __init__(self, time=None, date=None):
        self.__time = time
        self.__date = date

    def get_time(self):
        return self.__time

    def get_date(self):
        return self.__date

class ParkedVehicle:
    def __init__(self, no_kendaraan, kendaraan, status, datang, pulang):
        self.__no_kendaraan = no_kendaraan
        self.__kendaraan = kendaraan
        self.__status = status
        self.__datang = datang
        self.__pulang = pulang

    def get_no_kendaraan(self):
        return self.__no_kendaraan

    def get_kendaraan(self):
        return self.__kendaraan

    def get_status(self):
        return self.__status

    def get_datang(self):
        return self.__datang

    def get_pulang(self):
        return self.__pulang

class ParkedVehicleHelper:
    @staticmethod
    def get_pay(vehicle, total_jam, total_hari):
        if vehicle.get_status() == Status.MENGINAP:
            multiplier = 15000 if vehicle.get_kendaraan() == JenisKendaraan.MOTOR else 25000
            return multiplier * max(total_hari, 1)
        else:
            multiplier = 2000 if vehicle.get_kendaraan() == JenisKendaraan.MOTOR else 3000
            return multiplier * total_jam

def parse_time(s):
    h, m, s = map(int, s.split(":"))
    return Time(h, m, s)

def parse_date(s):
    y, m, d = map(int, s.split("/"))
    return Date(y, m, d)

def calculate_total_time(datang, pulang):
    datang_dt = datetime(
        datang.get_date().get_year(),
        datang.get_date().get_month(),
        datang.get_date().get_day(),
        datang.get_time().get_hour(),
        datang.get_time().get_minute(),
        datang.get_time().get_second()
    )
    pulang_dt = datetime(
        pulang.get_date().get_year(),
        pulang.get_date().get_month(),
        pulang.get_date().get_day(),
        pulang.get_time().get_hour(),
        pulang.get_time().get_minute(),
        pulang.get_time().get_second()
    )

    diff = pulang_dt - datang_dt
    total_detik = diff.total_seconds()
    if total_detik < 0:
        total_detik += 86400

    total_hari = total_detik // 86400 
    sisa_detik = total_detik % 86400
    total_jam = sisa_detik // 3600
    if sisa_detik % 3600 > 0:
        total_jam += 1
    total_jam += total_hari * 24
    if total_hari >= 1 and sisa_detik == 0:
        total_jam = int(total_hari * 24)

    return int(total_hari), int(total_jam)

def print_vehicle_summary(pv, total_hari, total_jam, pay):
    print("\n--- RINCIAN KENDARAAN ---")
    print("Nomor Kendaraan :", pv.get_no_kendaraan())
    print("Jenis Kendaraan :", pv.get_kendaraan())
    print("Status Parkir   :", pv.get_status())
    print("Tanggal Datang  :", pv.get_datang().get_date())
    print("Tanggal Pulang  :", pv.get_pulang().get_date())
    print("Jam Datang      :", pv.get_datang().get_time())
    print("Jam Pulang      :", pv.get_pulang().get_time())
    print("Total Hari      :", total_hari)
    print("Total Jam       :", total_jam)
    print("Biaya           : Rp", pay)

def main():
    parked_vehicles = []
    total_pay = 0

    n = int(input("Masukkan jumlah kendaraan: "))
    for i in range(n):
        print(f"\n--- Data Kendaraan ke-{i+1} ---")
        no = input("Nomor Kendaraan: ")
        jenis = int(input("Jenis Kendaraan (0: Motor, 1: Mobil): "))
        stat = int(input("Status Parkir (0: Regular, 1: Menginap): "))

        print("\nWaktu Kedatangan")
        datang_tgl = input("Tanggal (yyyy/MM/dd): ")
        datang_jam = input("Jam (HH:mm:ss): ")

        print("\nWaktu Kepulangan")
        pulang_tgl = input("Tanggal (yyyy/MM/dd): ")
        pulang_jam = input("Jam (HH:mm:ss): ")

        datang = Waktu(parse_time(datang_jam), parse_date(datang_tgl))
        pulang = Waktu(parse_time(pulang_jam), parse_date(pulang_tgl))

        kendaraan = JenisKendaraan.MOTOR if jenis == 0 else JenisKendaraan.MOBIL
        status = Status.REGULAR if stat == 0 else Status.MENGINAP

        pv = ParkedVehicle(no, kendaraan, status, datang, pulang)
        parked_vehicles.append(pv)

    for pv in parked_vehicles:
        total_hari, total_jam = calculate_total_time(pv.get_datang(), pv.get_pulang())
        pay = ParkedVehicleHelper.get_pay(pv, total_jam, total_hari)
        total_pay += pay

        print_vehicle_summary(pv, total_hari, total_jam, pay)

    print("\nTotal Bayar Keseluruhan :", total_pay)

if __name__ == "__main__":
    main()
