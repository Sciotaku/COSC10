package Lec1;
public class Ball {
    public int x;
    public int y;

    Ball(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public void printme() {
        System.out.println(this.x);
        System.out.println(this.y);
    }

    /* Python:
    def __init__(self):
        self.x = 10
     */

}