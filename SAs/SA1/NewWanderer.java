package SA1;

/**
 * A blob that wanders a little more resolutely than the one from class,
 * walking in a straight line for a fixed number of steps before switching.
 */
public class NewWanderer extends Blob {
    private int stepsBetweenVelocityChanges; // instance variable indicating the number of steps between velocity changes.
    private int stepsGone; // instance variables indicating how many of those it has currently gone since the last change.

    public NewWanderer(double x, double y) {
        super(x, y);

        // set the number of steps between velocity changes to be a random number between 4 and 9 (inclusive).
        stepsBetweenVelocityChanges = 4 + (int)(Math.random() * ((9-4) + 1));
        // Min + (int)(Math.random() * ((Max - Min) + 1)) got it from stackoverflow.com

        stepsGone = 0;
    }

    public NewWanderer(double x, double y, double r) {
        super(x, y);

        // set the number of steps between velocity changes to be a random number between 4 and 9 (inclusive).
        stepsBetweenVelocityChanges = 4 + (int)(Math.random() * ((9-4) + 1));
        // Min + (int)(Math.random() * ((Max - Min) + 1)) got it from stackoverflow.com

        stepsGone = 0;
    }

    @Override
    public void step() {
        stepsGone = stepsGone + 1;
        if (stepsGone >= stepsBetweenVelocityChanges) {
            // choose a new step between -1 and +1 in each of x and y
            dx = 2 * (Math.random() - 0.5);
            dy = 2 * (Math.random() - 0.5);
            stepsGone = 0;
        }
        x += dx;
        y += dy;
    }
}


