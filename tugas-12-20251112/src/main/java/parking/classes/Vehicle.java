/**
 * Nama             : Raissa Christabel Sebayang - 140810240008
 *                    Abraham Gomes Samosir - 140810240044
 *                    Haris Herdiansyah - 140810240074
 * Program Utama    : Aplikasi Parkir Pelabuhan
 * Modul            : Vehicle.java
 * Deskripsi        : Kelas abstrak yang merepresentasikan kendaraan umum yang diparkirkan.
 * Tanggal          : 11 November 2025
 */

package parking.classes;

public abstract class Vehicle {
    private String licensePlate;
    private VehicleType vehicleType;
    private DatesTime entryTime, exitTime;

    public Vehicle(String licensePlate, VehicleType vehicleType, DatesTime entryTime, DatesTime exitTime) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public DatesTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(DatesTime exitTime) {
        this.exitTime = exitTime;
    }

    public DatesTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(DatesTime entryTime) {
        this.entryTime = entryTime;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
}
