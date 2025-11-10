/**
 * Nama             : Raissa Christabel Sebayang - 140810240008
 *                    Abraham Gomes Samosir - 140810240044
 *                    Haris Herdiansyah - 140810240074
 * Program Utama    : Aplikasi Parkir Pelabuhan
 * Modul            : VehicleType.java
 * Deskripsi        : Enum yang merepresentasikan jenis kendaraan beserta tarif parkirnya.
 * Tanggal          : 11 November 2025
 */

package parking.classes;

public enum VehicleType {
    CAR(15000, 30000),
    MOTORCYCLE(10000, 20000),
    TRUCK(25000, 50000);

    private final double costPerHour, costPerDay;

    VehicleType(double costPerHour, double costPerDay) {
        this.costPerHour = costPerHour;
        this.costPerDay = costPerDay;
    }

    public double getCostPerHour() {
        return costPerHour;
    }

    public double getCostPerDay() {
        return costPerDay;
    }

    @Override
    public String toString() {
        return switch (this) {
            case CAR -> "Mobil";
            case MOTORCYCLE -> "Motor";
            case TRUCK -> "Truk";
        };
    }
}
