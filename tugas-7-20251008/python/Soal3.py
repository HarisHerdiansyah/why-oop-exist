"""
Nama - NPM    : ~ Raissa Christabel Sebayang - 140810240008
                ~ Abraham Gomes Samosir - 140810240044
                ~ Haris Herdiansyah - 140810240074
Kelas         : B
Nama File     : Soal3.py
Deskripsi     : Membuat program untuk menghitung biaya parkir kendaraan
                berdasarkan waktu datang dan pulang menggunakan array.
"""

class Time:
    def __init__(self, hour=0, minute=0, second=0):
        self.hour = hour
        self.minute = minute
        self.second = second

    @staticmethod
    def parse(s):
        h, m, sec = map(int, s.split(":"))
        return Time(h, m, sec)

    def total_seconds(self):
        return self.hour * 3600 + self.minute * 60 + self.second

    def __str__(self):
        return f"{self.hour:02d}:{self.minute:02d}:{self.second:02d}"

    def __format__(self, fmt):
        return str(self)


class Date:
    def __init__(self, year=0, month=0, day=0):
        self.year = year
        self.month = month
        self.day = day

    @staticmethod
    def parse(s):
        y, m, d = map(int, s.split("/"))
        return Date(y, m, d)

    def __str__(self):
        return f"{self.year:04d}/{self.month:02d}/{self.day:02d}"

    def __format__(self, fmt):
        return str(self)


class Waktu:
    def __init__(self, time, date):
        self.time = time
        self.date = date


def is_leap_year(year):
    return (year % 4 == 0 and year % 100 != 0) or (year % 400 == 0)


def days_in_month(year, month):
    days = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    if month == 2 and is_leap_year(year):
        return 29
    return days[month - 1]


def calculate_time_difference(datang, pulang):
    year = pulang.date.year - datang.date.year
    month = pulang.date.month - datang.date.month
    day = pulang.date.day - datang.date.day
    hour = pulang.time.hour - datang.time.hour
    minute = pulang.time.minute - datang.time.minute
    second = pulang.time.second - datang.time.second

    if second < 0:
        second += 60
        minute -= 1
    if minute < 0:
        minute += 60
        hour -= 1
    if hour < 0:
        hour += 24
        day -= 1
    if day < 0:
        month -= 1
        prev_month = pulang.date.month - 1
        year_prev_month = pulang.date.year
        if prev_month <= 0:
            prev_month += 12
            year_prev_month -= 1
        day += days_in_month(year_prev_month, prev_month)
    if month < 0:
        month += 12
        year -= 1

    return Waktu(Time(hour, minute, second), Date(year, month, day))


class ParkedVehicle:
    def __init__(self, no_kendaraan, jenis, status, datang, pulang):
        self.no_kendaraan = no_kendaraan
        self.jenis = jenis
        self.status = status
        self.datang = datang
        self.pulang = pulang


def get_pay(vehicle, diff):
    # Behavior preserved from original: Menginap = per hari (diff.date.day)
    if vehicle.status == "MENGINAP":
        multiplier = 15000 if vehicle.jenis == "MOTOR" else 25000
        pay = multiplier * diff.date.day
    else:
        multiplier = 2000 if vehicle.jenis == "MOTOR" else 3000
        jam = diff.time.hour
        if diff.time.minute > 0 or diff.time.second > 0:
            jam += 1
        pay = multiplier * jam
    return pay


def input_vehicle(i):
    print(f"\n--- Data Kendaraan ke-{i + 1} ---")
    no = input("Nomor Kendaraan: ")
    jenis = "MOTOR" if input("Jenis Kendaraan (0: Motor, 1: Mobil): ") == "0" else "MOBIL"
    status = "REGULAR" if input("Status Parkir (0: Regular, 1: Menginap): ") == "0" else "MENGINAP"
    waktu_kedatangan = input("Waktu Kedatangan (yyyy/MM/dd-HH:mm:ss): ").split("-")
    waktu_kepulangan = input("Waktu Kepulangan (yyyy/MM/dd-HH:mm:ss): ").split("-")
    datang = Waktu(Time.parse(waktu_kedatangan[1]), Date.parse(waktu_kedatangan[0]))
    pulang = Waktu(Time.parse(waktu_kepulangan[1]), Date.parse(waktu_kepulangan[0]))
    return ParkedVehicle(no, jenis, status, datang, pulang)


def seconds_to_hms_str(total_seconds):
    hours = total_seconds // 3600
    rem = total_seconds % 3600
    minutes = rem // 60
    seconds = rem % 60
    return f"{hours:02d}:{minutes:02d}:{seconds:02d}"


def print_vehicle_summary(vehicles):
    # column widths
    widths = [15, 15, 10, 15, 15, 12, 12, 10, 12, 12]
    # alignment: True => left, False => right
    aligns = [True, True, True, True, True, True, True, False, False, False]

    # build format tokens
    tokens = []
    for w, left in zip(widths, aligns):
        if left:
            tokens.append(f"{{:<{w}}}")
        else:
            tokens.append(f"{{:>{w}}}")

    row_fmt = "| " + " | ".join(tokens) + " |"
    border = "+" + "+".join("-" * (w + 2) for w in widths) + "+"

    headers = [
        "No Kendaraan", "Jenis Kendaraan", "Status", "Tanggal Datang", "Tanggal Pulang",
        "Jam Datang", "Jam Pulang", "Lama Hari", "Lama Jam", "Biaya"
    ]

    print("\n" + border)
    print(row_fmt.format(*headers))
    print(border)

    for v in vehicles:
        diff = calculate_time_difference(v.datang, v.pulang)

        # total seconds across days + time
        total_seconds = diff.date.day * 24 * 3600 + diff.time.total_seconds()
        lama_jam_str = seconds_to_hms_str(total_seconds)

        # lama hari as integer
        lama_hari_str = str(diff.date.day)

        pay = get_pay(v, diff)

        row_values = [
            v.no_kendaraan,
            v.jenis,
            v.status,
            str(v.datang.date),
            str(v.pulang.date),
            str(v.datang.time),
            str(v.pulang.time),
            lama_hari_str,
            lama_jam_str,
            str(pay)
        ]

        print(row_fmt.format(*row_values))
        print(border)


def main():
    try:
        n = int(input("Masukkan jumlah kendaraan: "))
    except ValueError:
        print("Input jumlah kendaraan harus bilangan bulat.")
        return
    vehicles = [input_vehicle(i) for i in range(n)]
    print_vehicle_summary(vehicles)


if __name__ == "__main__":
    main()
