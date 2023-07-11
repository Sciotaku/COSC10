package Lec3;

public class Ball {
    protected double x;
    protected double y;

    public Ball(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void WhoAmI(){
        System.out.println("I am a ball");
    }

    public void step(){
        System.out.println("Stepped");
    }
}
