// 1.1

public class Vehicle {

    private String brand;
    private int vehicleID;

    protected int fuel;
    protected int milesDriven;

    // 1.2
    public Vehicle(int vehicleID, String brand) {
        this.vehicleID = vehicleID;
        this.brand = brand;
        this.fuel = 10;
        this.milesDriven = 0;
    }

    public int getVehicleID() {
        return this.vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getMilesDriven() {
        return this.milesDriven;
    }

    public void drive() throws Exception {

    }

}
