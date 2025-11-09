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
}
