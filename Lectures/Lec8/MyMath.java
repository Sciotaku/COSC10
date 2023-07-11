package Lec8;

public class MyMath {
    public static double mysqrt(double x) throws Exception {
        if (x < 0) {
            throw new Exception("Negative number for sqrt");
        }
        return Math.sqrt(x);
    }
}

