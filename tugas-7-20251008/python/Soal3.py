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

    def set_hour(self, hour):
        self.__hour = hour

    def get_minute(self):
        return self.__minute

    def set_minute(self, minute):
        self.__minute = minute

    def get_second(self):
        return self.__second

    def set_second(self, second):
        self.__second = second

    def __str__(self):
        return f"{self.__hour:02d}:{self.__minute:02d}:{self.__second:02d}"

class Date:
    def __init__(self, year=0, month=0, day=0):
        self.__year = year
        self.__month = month
        self.__day = day

    def get_year(self):
        return self.__year

    def set_year(self, year):
        self.__year = year

    def get_month(self):
        return self.__month

    def set_month(self, month):
        self.__month = month

    def get_day(self):
        return self.__day

    def set_day(self, day):
        self.__day = day

    def __str__(self):
        return f"{self.__year:04d}/{self.__month:02d}/{self.__day:02d}"

class Waktu:
    def __init__(self, time=None, date=None):
        self.__time = time
        self.__date = date

    def get_time(self):
        return self.__time

    def set_time(self, time):
        self.__time = time

    def get_date(self):
        return self.__date

    def set_date(self, date):
        self.__date = date

class WaktuHelper:
    @staticmethod
    def is_leap_year(year):
        return (year % 4 == 0 and year % 100 != 0) or (year % 400 == 0)

    @staticmethod
    def days_in_month(year, month):
        days = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
        if month == 2 and WaktuHelper.is_leap_year(year):
            return 29
        return days[month - 1]

    @staticmethod
    def calculate_time_difference(datang, pulang):
        d1 = datang.get_date()
        t1 = datang.get_time()
        d2 = pulang.get_date()
        t2 = pulang.get_time()

        yearD, monthD, dayD = d1.get_year(), d1.get_month(), d1.get_day()
        hourD, minuteD, secondD = t1.get_hour(), t1.get_minute(), t1.get_second()

        yearP, monthP, dayP = d2.get_year(), d2.get_month(), d2.get_day()
        hourP, minuteP, secondP = t2.get_hour(), t2.get_minute(), t2.get_second()

        if secondP < secondD:
            secondP += 60
            minuteP -= 1
        secondDiff = secondP - secondD

        if minuteP < minuteD:
            minuteP += 60
            hourP -= 1
        minuteDiff = minuteP - minuteD

        if hourP < hourD:
            hourP += 24
            dayP -= 1
        hourDiff = hourP - hourD

        if dayP < dayD:
            monthP -= 1
            if monthP < 1:
                monthP = 12
                yearP -= 1
            dayP += WaktuHelper.days_in_month(yearP, monthP)
        dayDiff = dayP - dayD

        if monthP < monthD:
            monthP += 12
            yearP -= 1
        monthDiff = monthP - monthD

        yearDiff = yearP - yearD

        return Waktu(Time(hourDiff, minuteDiff, secondDiff), Date(yearDiff, monthDiff, dayDiff))

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


def print_vehicle_summary(pv, diff, total_hari, total_jam, pay):
    print("\n--- RINCIAN KENDARAAN ---")
    print("Nomor Kendaraan :", pv.get_no_kendaraan())
    print("Jenis Kendaraan :", pv.get_kendaraan())
    print("Status Parkir   :", pv.get_status())
    print("Tanggal Datang  :", pv.get_datang().get_date())
    print("Tanggal Pulang  :", pv.get_pulang().get_date())
    print("Jam Datang      :", pv.get_datang().get_time())
    print("Jam Pulang      :", pv.get_pulang().get_time())
    print("Selisih Tanggal :", diff.get_date())
    print("Selisih Waktu   :", diff.get_time())
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
        diff = WaktuHelper.calculate_time_difference(pv.get_datang(), pv.get_pulang())
        time_diff = diff.get_time()
        date_diff = diff.get_date()

        total_hari = date_diff.get_day()
        total_jam = time_diff.get_hour()
        if time_diff.get_minute() > 0 or time_diff.get_second() > 0:
            total_jam += 1

        pay = ParkedVehicleHelper.get_pay(pv, total_jam, total_hari)
        total_pay += pay

        print_vehicle_summary(pv, diff, total_hari, total_jam, pay)

    print("\nTotal Bayar Keseluruhan :", total_pay)


if __name__ == "__main__":
    main()
