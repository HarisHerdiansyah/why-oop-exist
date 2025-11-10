/**
 * Nama             : Raissa Christabel Sebayang - 140810240008
 *                    Abraham Gomes Samosir - 140810240044
 *                    Haris Herdiansyah - 140810240074
 * Program Utama    : Aplikasi Parkir Pelabuhan
 * Modul            : ParkingService.java
 * Deskripsi        : Kelas untuk menangani logika bisnis aplikasi parkir pelabuhan.
 * Tanggal          : 11 November 2025
 */

package parking.service;

import parking.classes.DatesTime;
import parking.classes.Vehicle;
import parking.classes.VehicleType;
import parking.utils.DatesTimeValidator;

import java.time.Duration;
import java.time.Period;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class ParkingService {
    private static ParkingService instance;
    private final List<Vehicle> vehicleList;

    private ParkingService() {
        this.vehicleList = new ArrayList<>();
    }

    public static ParkingService getInstance() {
        if (instance == null) {
            instance = new ParkingService();
        }
        return instance;
    }

    public DatesTime datesTimeParser(String dateTimeStr) throws IllegalArgumentException {
        String[] parts = dateTimeStr.split(" ");
        String[] dateParts = parts[0].split("/");
        String[] timeParts = parts[1].split(":");

        int year = Integer.parseInt(dateParts[0]);
        DatesTimeValidator.validateYear(year);

        int month = Integer.parseInt(dateParts[1]);
        DatesTimeValidator.validateMonth(month);

        int day = Integer.parseInt(dateParts[2]);
        DatesTimeValidator.validateDay(year, month, day);

        int hour = Integer.parseInt(timeParts[0]);
        DatesTimeValidator.validateHours(hour);

        int minute = Integer.parseInt(timeParts[1]);
        DatesTimeValidator.validateMinutes(minute);

        int second = Integer.parseInt(timeParts[2]);
        DatesTimeValidator.validateSeconds(second);

        return new DatesTime(year, month, day, hour, minute, second);
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
            Period period = Period.between(entryTime.toLocalDate(), exitTime.toLocalDate());
            return period.getDays() * v.getVehicleType().getCostPerDay();
        }

        Duration duration = Duration.between(entryTime, exitTime);
        long secondsBetween = duration.toSeconds();

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

    public DatesTime datesTimeDiff(Vehicle v) {
        LocalDateTime entryTime = v.getEntryTime().toLocalDateTime();
        LocalDateTime exitTime = v.getExitTime().toLocalDateTime();

        Period period = Period.between(entryTime.toLocalDate(), exitTime.toLocalDate());

        LocalDateTime temp = entryTime.plus(period);
        Duration duration = Duration.between(temp, exitTime);

        int hour = duration.toHoursPart() < 0 ? duration.toHoursPart() + 24 : duration.toHoursPart();
        int min = duration.toMinutesPart() < 0 ? duration.toMinutesPart() + 60 : duration.toMinutesPart();
        int sec = duration.toSecondsPart() < 0 ? duration.toSecondsPart() + 60 : duration.toSecondsPart();

        return new DatesTime(
                period.getYears(),
                period.getMonths(),
                period.getDays(),
                hour,
                min,
                sec);
    }

    public String getMappedVehicleList() {
        StringBuilder sb = new StringBuilder();
        for (Vehicle v : vehicleList) {
            sb.append(String.format("\n%s\t %s\t %s\t %s\t %s\t %s",
                    v.getLicensePlate(),
                    v.getVehicleType().toString(),
                    v.getEntryTime().toString(),
                    v.getExitTime().toString(),
                    datesTimeDiff(v).toString(),
                    calculateParkingCost(v)));
        }
        return sb.toString();
    }

    public String getMappedVehicleList(VehicleType type) {
        StringBuilder sb = new StringBuilder();
        for (Vehicle v : vehicleList) {
            if (v.getVehicleType().equals(type)) {
                sb.append(String.format("\n%s\t %s\t %s\t %s\t %s\t %s",
                        v.getLicensePlate(),
                        v.getVehicleType().toString(),
                        v.getEntryTime().toString(),
                        v.getExitTime().toString(),
                        datesTimeDiff(v).toString(),
                        calculateParkingCost(v)));
            }
        }
        return sb.toString();
    }
}
