package parking.classes;

public class Car extends Vehicle {
    public Car(String licensePlate, DatesTime entryTime, DatesTime exitTime) {
        super(licensePlate, VehicleType.CAR, entryTime, exitTime);
    }
}
