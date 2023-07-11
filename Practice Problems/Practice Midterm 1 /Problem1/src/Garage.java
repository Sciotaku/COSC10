import java.util.ArrayList;

// 1.5

public class Garage {

    private ArrayList<Vehicle> vehicles;

    public Garage() {
        this.vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    public void driveVehicles() {
        for(Vehicle v : vehicles) {
            try {
                v.drive();
            } catch(Exception e) {
                System.err.println(e);
            }
        }
    }

    public void displayGarage() {
        for(Vehicle v : vehicles) {
            System.out.println(v);
        }
    }

    public int totalMileage() {
        int total = 0;

        for(Vehicle v : vehicles) {
            total += v.getMilesDriven();
        }

        return total;
    }

}
