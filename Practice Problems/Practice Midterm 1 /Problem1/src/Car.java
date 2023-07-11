// 1.3

public class Car extends Vehicle {

    public Car(int vehicleID, String brand) {
        super(vehicleID, brand);
    }

    @Override
    public void drive() throws Exception {
        if(this.fuel == 0) throw new Exception("Out of fuel");

        this.fuel -= 1;
        this.milesDriven += 25;
    }

    // 1.4
    @Override
    public String toString() {
        return ("Car - vehicleID: " + getVehicleID() + ", brand: " + getBrand());
    }
}
