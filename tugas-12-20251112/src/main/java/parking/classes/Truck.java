package parking.classes;

public class Truck extends Vehicle {
    public Truck(String licensePlate, DatesTime entryTime, DatesTime exitTime) {
        super(licensePlate, VehicleType.TRUCK, entryTime, exitTime);
    }
}
