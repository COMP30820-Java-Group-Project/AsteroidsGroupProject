package hellofx;

public class Asteroid extends Sprite {

    public void createCoOrdinates(String sizeCategory) {
        // x and y denote starting postitions
        double firstX;
        double firstY;
        double randomPick = Math.random();

        // use a random number picker to determine x and y starting point
        // either x or y must be 0.0 or 1000.0/900.0 respectively to begin for asteroid
        // to appear at edge of screen
        // will adjust this later so that they are not visible at exact spawn time and
        // move into screen instead

        // starting co-ordinates decided based on magnitude of random number
        if (randomPick >= 0.49) {
            firstX = Math.round(Math.random() * 1000);
            if (randomPick >= 0.74) {
                firstY = 0.0;
            } else {
                firstY = 900.0;
            }
        } else {
            if (randomPick >= 0.24) {
                firstX = 0.0;
            } else {
                firstX = 1000.0;
            }
            firstY = Math.round(Math.random() * 900);
        }

        // using Pythagoras' Theorem to get the rest of the co-ordinates required (a^2 +
        // b^2 == c^2)
        // a and b will be the same for octagon so omit b
        double a;
        double c;

        switch (sizeCategory) {
            case "large":
                a = 56.57;
                c = 80;
                break;
            case "medium":
                a = 35.78;
                c = 50;
                break;
            case "small":
                a = 14.14;
                c = 20;
                break;
            default:
                a = 56.57;
                c = 80;
                break;
        }

        this.getPoints()
                .addAll(new Double[] { firstX, firstY, firstX + c, firstY, firstX + a + c, firstY + a,
                        firstX + a + c, firstY + a + c, firstX + c, firstY + a + c + a, firstX, firstY + a + c + a,
                        firstX - a, firstY + a + c, firstX - a, firstY + a});
    }

    // pass parameters to Polygon constructor
    public Asteroid() {
        super();
        this.speed = 3;

        // Generating a random angle to fly at
        this.setRotate((Math.random() * (360 - 0)) + 0);
    }

    // *** getters and setters ***

    public double getSpeed() {
        // returns the speed of the asteroid
        return this.speed;
    }

    public void changeSpeed(int diffSpeed) {
        // sets the speed of the asteroid
        this.speed += diffSpeed;
    }

}