package Lec3;

public class FallingBall extends Ball {
    public FallingBall(double x, double y){
        super(x, y);
    }

    @Override
    public void step(){
        System.out.println("FallingBall step called");
        this.y += 1;
    }
}

