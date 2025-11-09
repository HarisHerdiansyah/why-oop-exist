package parking.service;

import parking.classes.Vehicle;

import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingService {
    private final List<Vehicle> vehicleList;

    public ParkingService() {
        this.vehicleList = new ArrayList<>();
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void addVehicle(Vehicle v) throws IllegalArgumentException {
        if (vehicleList.stream().anyMatch(vl -> vl.getLicensePlate().equals(v.getLicensePlate()))) {
            throw new IllegalArgumentException("Kendaraan dengan plat nomor yang sama sudah ada di dalam list.");
        }

        LocalDateTime entryTime = v.getEntryTime().toLocalDateTime();
        LocalDateTime exitTime = v.getExitTime().toLocalDateTime();

        if (exitTime.isBefore(entryTime) || exitTime.isEqual(entryTime)) {
            throw new IllegalArgumentException("Waktu keluar harus lebih besar dari waktu masuk.");
        }

        vehicleList.add(v);
    }

    public double calculateParkingCost(Vehicle v) {
        LocalDateTime entryTime = v.getEntryTime().toLocalDateTime();
        LocalDateTime exitTime = v.getExitTime().toLocalDateTime();

        if (!exitTime.toLocalDate().equals(entryTime.toLocalDate())) {
            long daysBetween = ChronoUnit.DAYS.between(entryTime, exitTime);
            return daysBetween * v.getVehicleType().getCostPerDay();
        }

        Duration duration = Duration.between(entryTime, exitTime);
        long secondsBetween = duration.getSeconds();

        if (secondsBetween == 0) return 0.0;

        int hoursBetween = (int) Math.ceil(secondsBetween / 3600.0);
        return hoursBetween * v.getVehicleType().getCostPerHour();
    }

    public double calculateTotalRevenue() {
        double total = 0.0;
        for (Vehicle v : vehicleList) {
            total += calculateParkingCost(v);
        }
        return total;
    }
}
