package Lec3;

public class TestBall {
    public static void main(String[] args) {
        int x = 5;
        double y = 3.4;

        Ball myBall = new FallingBall(5, 5 );

        myBall.WhoAmI();
        myBall.step();
    }
}
