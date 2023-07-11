// 1.3

public class Motorcycle extends Vehicle {

    public Motorcycle(int vehicleID, String brand) {
        super(vehicleID, brand);
    }

    @Override
    public void drive() throws Exception {
        if(this.fuel == 0) throw new Exception("Out of fuel");

        this.fuel -= 1;
        this.milesDriven += 50;
    }

    // 1.4
    @Override
    public String toString() {
        return ("Motorcycle - vehicleID: " + getVehicleID() + ", brand: " + getBrand());
    }
}
