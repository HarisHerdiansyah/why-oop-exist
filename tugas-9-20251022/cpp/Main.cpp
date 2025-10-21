#include <iostream>
#include <string>

class JenisKendaraan {
private:
	int tarif_per_jam;

public:
	static const JenisKendaraan MOBIL;
	static const JenisKendaraan MOTOR;
	static const JenisKendaraan TRUK;
	
	JenisKendaraan(int tarif_per_jam): tarif_per_jam(tarif_per_jam) {}
	
	int get_tarif_per_jam () const {
		return this->tarif_per_jam;
	}
};

const JenisKendaraan JenisKendaraan::MOBIL(15000);
const JenisKendaraan JenisKendaraan::MOTOR(7000);
const JenisKendaraan JenisKendaraan::TRUK(40000);

class Time {
private:
	int hour, minute, seconds;
	
public:
	Time(int hour, int minute, int seconds): hour(hour), minute(minute), seconds(seconds) {}
	
	int get_hour() {
		return hour;
	}
	
	void set_hour(int hour) {
		this->hour = hour;
	}
		
	int get_minute() {
		return minute;
	}
	
	void set_minute(int minute) {
		this->minute = minute;
	}
		
	int get_seconds() {
		return seconds;
	}
	
	void set_seconds(int seconds) {
		this->seconds = seconds;
	}
};

class Dates {
private:
	int year, month, day;
	
public:
	Dates(int year, int month, int day): year(year), month(month), day(day) {}
	
	int get_year() {
		return year;
	}
	
	void set_year(int year) {
		this->year = year;
	}
		
	int get_month() {
		return month;
	}
	
	void set_month(int month) {
		this->month = month;
	}
		
	int get_day() {
		return day;
	}
	
	void set_day(int day) {
		this->day = day;
	}
};

class Waktu {
private:
	Dates dates;
	Time time;
	
public:
	Waktu(Dates dates, Time time): dates(dates), time(time) {}
	
	Dates get_dates() {
		return dates;
	}
	
	void set_dates(Dates dates) {
		this->dates = dates;
	}
	
	Time get_time() {
		return time;
	}
	
	void set_time(Time time) {
		this->time = time;
	}
};

class Pemilik {
private:
	std::string name;

public:
	Pemilik(std::string name): name(name) {}
	
	std::string get_name() {
		return name;
	}
	
	void set_name(std::string name) {
		this->name = name;
	}
};

class Kendaraan {
private:
	JenisKendaraan jenis_kendaraan;
	Pemilik pemilik;
	Waktu waktu_masuk, waktu_keluar;
	std::string no_kendaraan;
	
public:
	Kendaraan(JenisKendaraan jk, Pemilik p, Waktu wm, Waktu wk, std::string nk):
		jenis_kendaraan(jk), pemilik(p), waktu_masuk(wm), waktu_keluar(wk), no_kendaraan(nk) {}
	
	JenisKendaraan get_jenis_kendaraan() {
		return jenis_kendaraan;
	}
	
	void set_jenis_kendaraan(JenisKendaraan jenis_kendaraan) {
		this->jenis_kendaraan = jenis_kendaraan;
	}
	
	Pemilik get_pemilik() {
		return pemilik;
	}
	
	void set_pemilik(Pemilik pemilik) {
		this->pemilik = pemilik;
	}
	
	Waktu get_waktu_masuk() {
		return waktu_masuk;
	}
	
	void set_waktu_masuk(Waktu waktu_masuk) {
		this->waktu_masuk = waktu_masuk;
	}
	
	Waktu get_waktu_keluar() {
		return waktu_keluar;
	}
	
	void set_waktu_keluar(Waktu waktu_keluar) {
		this->waktu_keluar = waktu_keluar;
	}
	
	std::string get_no_kendaraan() {
		return no_kendaraan;
	}
	
	void set_no_kendaraan(std::string no_kendaraan) {
		this->no_kendaraan = no_kendaraan;
	}
};

class Mobil: public Kendaraan {
public:
	Mobil(JenisKendaraan jk, Pemilik p, Waktu wm, Waktu wk, std::string nk):
		Kendaraan(jk, p, wm, wk, nk) {}
};

class Motor: public Kendaraan {
public:
	Motor(JenisKendaraan jk, Pemilik p, Waktu wm, Waktu wk, std::string nk):
		Kendaraan(jk, p, wm, wk, nk) {}
};

class Truk: public Kendaraan {
public:
	Truk(JenisKendaraan jk, Pemilik p, Waktu wm, Waktu wk, std::string nk):
		Kendaraan(jk, p, wm, wk, nk) {}
};

int main() {
	return 0;
}