/**
 * Nama             : Raissa Christabel Sebayang - 140810240008
 *                    Abraham Gomes Samosir - 140810240044
 *                    Haris Herdiansyah - 140810240074
 * Program Utama    : Aplikasi Parkir Pelabuhan
 * Modul            : Car.java
 * Deskripsi        : Kelas turunan dari Vehicle yang merepresentasikan kendaraan jenis mobil.
 * Tanggal          : 11 November 2025
 */

package parking.classes;

public class Car extends Vehicle {
    public Car(String licensePlate, DatesTime entryTime, DatesTime exitTime) {
        super(licensePlate, VehicleType.CAR, entryTime, exitTime);
    }
}
