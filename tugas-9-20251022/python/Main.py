from enum import Enum
from abs import ABC, abstractmethod

class JenisKendaraan(Enum):
    MOBIL = 15000
    MOTOR = 7000
    TRUK = 40000
    
class Time:
    def __init__(self, hour, minute, second):
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

class Dates:
    def __init__(self, year, month, day):
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

class Waktu:
    def __init__(self, dates, time):
        self.__dates = dates
        self.__time = time
        
    def get_dates(self):
        return self.__dates
        
    def set_dates(self, dates):
        self.__dates = dates
        
    def get_time(self):
        return self.__time
        
    def set_time(self, time):
        self.__time = time

class Pemilik:
    def __init__(self, name):
        self.__name = name
        
    def get_name(self):
        return self.__name
        
    def set_name(self, name):
        self.__name = name

class Kendaraan:
    def __init__(self, jenis_kendaraan, pemilik, waktu_masuk, waktu_keluar, no_kendaraan):
        self.__jenis_kendaraan = jenis_kendaraan
        self.__pemilik = pemilik
        self.__waktu_masuk = waktu_masuk
        self.__waktu_keluar = waktu_keluar
        self.__no_kendaraan = no_kendaraan

    def get_jenis_kendaraan(self):
        return self.__jenis_kendaraan

    def set_jenis_kendaraan(self, jenis_kendaraan):
        self.__jenis_kendaraan = jenis_kendaraan

    def get_pemilik(self):
        return self.__pemilik

    def set_pemilik(self, pemilik):
        self.__pemilik = pemilik

    def get_waktu_masuk(self):
        return self.__waktu_masuk

    def set_waktu_masuk(self, waktu_masuk):
        self.__waktu_masuk = waktu_masuk

    def get_waktu_keluar(self):
        return self.__waktu_keluar

    def set_waktu_keluar(self, waktu_keluar):
        self.__waktu_keluar = waktu_keluar

    def get_no_kendaraan(self):
        return self.__no_kendaraan

    def set_no_kendaraan(self, no_kendaraan):
        self.__no_kendaraan = no_kendaraan

class Mobil(Kendaraan):
    def __init__(self, jenis_kendaraan, pemilik, waktu_masuk, waktu_keluar, no_kendaraan):
        super().__init__(jenis_kendaraan, pemilik, waktu_masuk, waktu_keluar, no_kendaraan)
        
class Motor(Kendaraan):
    def __init__(self, jenis_kendaraan, pemilik, waktu_masuk, waktu_keluar, no_kendaraan):
        super().__init__(jenis_kendaraan, pemilik, waktu_masuk, waktu_keluar, no_kendaraan)
        
class Truk(Kendaraan):
    def __init__(self, jenis_kendaraan, pemilik, waktu_masuk, waktu_keluar, no_kendaraan):
        super().__init__(jenis_kendaraan, pemilik, waktu_masuk, waktu_keluar, no_kendaraan)

def main():
	print("ohayo sekai")
	
if __name__ == "__main__": main()