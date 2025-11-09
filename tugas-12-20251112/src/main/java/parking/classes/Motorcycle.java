package parking.classes;

public class Motorcycle extends Vehicle {
    public Motorcycle(String licensePlate, DatesTime entryTime, DatesTime exitTime) {
        super(licensePlate, VehicleType.MOTORCYCLE, entryTime, exitTime);
    }
}
