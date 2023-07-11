// 1.6

public class Runner {

    public static void main(String[] args) {
        Garage g = new Garage();
        g.addVehicle(new Motorcycle(10, "Yamaha"));
        g.addVehicle(new Car(2023, "Toyota"));
        g.addVehicle(new Car(123, "Ferrari"));
        g.addVehicle(new Motorcycle(10, "BMW"));
        g.addVehicle(new Car(12, "Tesla"));

        for(int i = 0; i < 12; i++) {
            g.driveVehicles();
        }

        g.displayGarage();

        System.out.println("Total Miles: " + g.totalMileage());
    }

}
