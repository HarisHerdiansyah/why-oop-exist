/**
 * Nama             : Raissa Christabel Sebayang - 140810240008
 *                    Abraham Gomes Samosir - 140810240044
 *                    Haris Herdiansyah - 140810240074
 * Program Utama    : Aplikasi Parkir Pelabuhan
 * Modul            : Truck.java
 * Deskripsi        : Kelas turunan dari Vehicle yang merepresentasikan kendaraan jenis truk.
 * Tanggal          : 11 November 2025
 */

package parking.classes;

public class Truck extends Vehicle {
    public Truck(String licensePlate, DatesTime entryTime, DatesTime exitTime) {
        super(licensePlate, VehicleType.TRUCK, entryTime, exitTime);
    }
}
