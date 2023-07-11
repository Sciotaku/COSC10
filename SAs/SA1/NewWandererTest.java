package SA1;

public class NewWandererTest {
    public static void main(String[] args) {
        NewWanderer blob = new NewWanderer(100, 100);
        for (int i = 0; i < 100; i++) {
            blob.step();
            System.out.println("Blob at (" + blob.getX() + ", " + blob.getY() + ") " +
                    "with velocity (" + blob.dx + ", " + blob.dy + ")");
        }

    }
}



