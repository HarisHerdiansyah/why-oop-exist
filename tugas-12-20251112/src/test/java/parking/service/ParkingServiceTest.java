/**
 * Nama             : Raissa Christabel Sebayang - 140810240008
 *                    Abraham Gomes Samosir - 140810240044
 *                    Haris Herdiansyah - 140810240074
 * Program Utama    : Aplikasi Parkir Pelabuhan
 * Modul            : ParkingServiceTest.java
 * Deskripsi        : Kelas untuk melakukan unit test pada ParkingService.
 * Tanggal          : 11 November 2025
 */

package parking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parking.classes.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ParkingService unit tests")
public class ParkingServiceTest {

    @BeforeEach
    public void setUp() {
        // Clear singleton state before each test so tests are isolated
        ParkingService.getInstance().getVehicleList().clear();
    }

    @Test
    @DisplayName("addVehicle_acceptsValidVehicle")
    public void addVehicle_acceptsValidVehicle() {
        ParkingService service = ParkingService.getInstance();
        DatesTime entry = new DatesTime(2023, 1, 1, 10, 0, 0);
        DatesTime exit = new DatesTime(2023, 1, 1, 12, 0, 0);
        Car car = new Car("AB1234", entry, exit);

        assertDoesNotThrow(() -> service.addVehicle(car));
        assertEquals(1, service.getVehicleList().size());
        assertSame(car, service.getVehicleList().get(0));
    }

    @Test
    @DisplayName("addVehicle_rejectsDuplicateLicensePlate")
    public void addVehicle_rejectsDuplicateLicensePlate() {
        ParkingService service = ParkingService.getInstance();
        DatesTime e1 = new DatesTime(2023, 1, 1, 8, 0, 0);
        DatesTime x1 = new DatesTime(2023, 1, 1, 10, 0, 0);
        Car car1 = new Car("DUP123", e1, x1);
        service.addVehicle(car1);

        DatesTime e2 = new DatesTime(2023, 1, 1, 11, 0, 0);
        DatesTime x2 = new DatesTime(2023, 1, 1, 13, 0, 0);
        Car car2 = new Car("DUP123", e2, x2);

        assertThrows(IllegalArgumentException.class, () -> service.addVehicle(car2));
    }

    @Test
    @DisplayName("addVehicle_rejectsExitBeforeOrEqualEntry")
    public void addVehicle_rejectsExitBeforeOrEqualEntry() {
        ParkingService service = ParkingService.getInstance();

        // equal times
        DatesTime e = new DatesTime(2023, 1, 2, 9, 0, 0);
        DatesTime same = new DatesTime(2023, 1, 2, 9, 0, 0);
        Motorcycle m1 = new Motorcycle("MOTO1", e, same);
        assertThrows(IllegalArgumentException.class, () -> service.addVehicle(m1));

        // exit before entry
        DatesTime entry = new DatesTime(2023, 1, 2, 10, 0, 0);
        DatesTime exitBefore = new DatesTime(2023, 1, 2, 9, 59, 59);
        Motorcycle m2 = new Motorcycle("MOTO2", entry, exitBefore);
        assertThrows(IllegalArgumentException.class, () -> service.addVehicle(m2));
    }

    @Test
    @DisplayName("calculateParkingCost_zeroDuration_returnsZero")
    public void calculateParkingCost_zeroDuration_returnsZero() {
        DatesTime dt = new DatesTime(2023, 5, 5, 10, 0, 0);
        Car car = new Car("ZER0", dt, dt);
        ParkingService service = ParkingService.getInstance();

        double cost = service.calculateParkingCost(car);
        assertEquals(0.0, cost, 0.0001);
    }

    @Test
    @DisplayName("calculateParkingCost_hourlyCeil_roundsUpPartialHours")
    public void calculateParkingCost_hourlyCeil_roundsUpPartialHours() {
        // 30 minutes should charge 1 hour for a CAR
        DatesTime entry = new DatesTime(2023, 6, 1, 10, 0, 0);
        DatesTime exit = new DatesTime(2023, 6, 1, 10, 30, 0);
        Car car = new Car("HOUR1", entry, exit);
        ParkingService service = ParkingService.getInstance();

        double expected = VehicleType.CAR.getCostPerHour() * 1;
        assertEquals(expected, service.calculateParkingCost(car), 0.0001);

        // 61 minutes should charge 2 hours
        DatesTime exit2 = new DatesTime(2023, 6, 1, 11, 1, 0);
        Car car2 = new Car("HOUR2", entry, exit2);
        double expected2 = VehicleType.CAR.getCostPerHour() * 2;
        assertEquals(expected2, service.calculateParkingCost(car2), 0.0001);
    }

    @Test
    @DisplayName("calculateParkingCost_multiday_fullDays")
    public void calculateParkingCost_multiday_fullDays() {
        // from 2023-01-01 00:00 to 2023-01-03 00:00 -> 2 days
        DatesTime entry = new DatesTime(2023, 1, 1, 0, 0, 0);
        DatesTime exit = new DatesTime(2023, 1, 3, 0, 0, 0);
        Truck truck = new Truck("TRK1", entry, exit);
        ParkingService service = ParkingService.getInstance();

        double expected = 2 * VehicleType.TRUCK.getCostPerDay();
        assertEquals(expected, service.calculateParkingCost(truck), 0.0001);
    }

    @Test
    @DisplayName("calculateParkingCost_multiday_crossMidnight_smallDiff")
    public void calculateParkingCost_multiday_crossMidnight_smallDiff() {
        // entry 23:00 on day1, exit 01:00 on day2 -> treat as 1 day
        DatesTime entry = new DatesTime(2023, 2, 1, 23, 0, 0);
        DatesTime exit = new DatesTime(2023, 2, 2, 1, 0, 0);
        Truck truck = new Truck("TRK2", entry, exit);
        ParkingService service = ParkingService.getInstance();

        double cost = service.calculateParkingCost(truck);
        assertEquals(50000.0, cost, 0.0001);
    }

    @Test
    @DisplayName("calculateTotalRevenue_sumsAllVehicleCosts")
    public void calculateTotalRevenue_sumsAllVehicleCosts() {
        ParkingService service = ParkingService.getInstance();

        // Car: 2 hours -> 2 * 15000
        DatesTime cEntry = new DatesTime(2023, 7, 1, 8, 0, 0);
        DatesTime cExit = new DatesTime(2023, 7, 1, 10, 0, 0);
        Car car = new Car("REV1", cEntry, cExit);
        service.addVehicle(car);

        // Motorcycle: 30 minutes -> 1 * 10000
        DatesTime mEntry = new DatesTime(2023, 7, 1, 9, 30, 0);
        DatesTime mExit = new DatesTime(2023, 7, 1, 10, 0, 0);
        Motorcycle motor = new Motorcycle("REV2", mEntry, mExit);
        service.addVehicle(motor);

        // Truck: 2 full days -> 2 * 50000
        DatesTime tEntry = new DatesTime(2023, 7, 1, 0, 0, 0);
        DatesTime tExit = new DatesTime(2023, 7, 3, 0, 0, 0);
        Truck truck = new Truck("REV3", tEntry, tExit);
        service.addVehicle(truck);

        double expected = (2 * VehicleType.CAR.getCostPerHour())
                + (1 * VehicleType.MOTORCYCLE.getCostPerHour())
                + (2 * VehicleType.TRUCK.getCostPerDay());

        assertEquals(expected, service.calculateTotalRevenue(), 0.0001);
    }
}
