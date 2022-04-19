package asteroidsgame;


import javafx.scene.paint.Color;

public class Asteroid extends Sprite {

    // using enums for sizes
    private AsteroidSizes asteroidSize;

    // pass parameters to Polygon constructor
    public Asteroid(AsteroidSizes asteroidSize) {
        super();
        this.asteroidSize = asteroidSize;
        createAsteroid(this.asteroidSize);
        this.speed = doubleSetSpeed(0.8);
        this.setTranslateX(this.createXCoOrdinates());
        this.setTranslateY(this.createYCoOrdinates());
        // Generating a random angle to fly at
        this.setRotate((Math.random() * (360 - 0)) + 0);
    }

    // 2nd constructor for medium and small asteroids when coordinates already
    // determined
    public Asteroid(AsteroidSizes asteroidSize, double x, double y, double s) {
        super();
        this.asteroidSize = asteroidSize;
        createAsteroid(this.asteroidSize);
        this.speed = doubleSetSpeed(s);
        this.setTranslateX(x);
        this.setTranslateY(y);
        // Generating a random angle to fly at
        this.setRotate((Math.random() * (360 - 0)) + 0);
    }

    // return value for speed based on a seed value
    public double doubleSetSpeed(double seed) {
        double speed = (Math.random() * seed) + seed;
        return speed;
    }

    // final as it is used in constructor
    private final double createXCoOrdinates() {
        // x denotes starting coordinate
        double x = Math.random() * GAMEDIMENSIONS.SCREENWIDTH.getVal();
        // add 200px buffer around center
        while (x > (GAMEDIMENSIONS.SCREENWIDTH.getVal() / 2) - 100 && x < (GAMEDIMENSIONS.SCREENWIDTH.getVal() / 2) + 100) {
            x = Math.random() * GAMEDIMENSIONS.SCREENWIDTH.getVal();
        }
        return x;
    }

    // final as it is used in constructor
    private final double createYCoOrdinates() {
        // y denotes starting coordinate
        double y = Math.random() * GAMEDIMENSIONS.SCREENHEIGHT.getVal();
        // add 200px buffer around center
        while (y > (GAMEDIMENSIONS.SCREENHEIGHT.getVal() / 2) - 100 && y < (GAMEDIMENSIONS.SCREENHEIGHT.getVal() / 2) + 100) {
            y = Math.random() * GAMEDIMENSIONS.SCREENHEIGHT.getVal();
        }
        return y;
    }

    // method takes enum size
    // final as it is used in constructor
    private final void createAsteroid(AsteroidSizes asize) {
        double a;
        double c;

        // using Pythagoras' Theorem to get the rest of the co-ordinates required (a^2 +
        // b^2 == c^2)
        // a and b will be the same for octagon so omit b

        switch (asize) {
            case LARGE:
                a = 42.42;
                c = 60;
                break;
            case MEDIUM:
                a = 28.28;
                c = 40;
                break;
            case SMALL:
                a = 14.14;
                c = 20;
                break;
            default:
                a = 56.57;
                c = 40;
                break;
        }

        // b value used to add variability to asteroid side length (more like asteroid
        // than regular octagon)
        double b = c * 0.75;
        double firstRand = Math.random() * b;
        double secondRand = Math.random() * b;
        double thirdRand = Math.random() * b;
        double fourthRand = Math.random() * b;

        this.getPoints()
                .addAll(new Double[] { this.getTranslateX(), this.getTranslateY(), this.getTranslateX() + c + firstRand,
                        this.getTranslateY() + firstRand, this.getTranslateX() + a + c, this.getTranslateY() + a,
                        this.getTranslateX() + a + c + secondRand, this.getTranslateY() + a + c + secondRand,
                        this.getTranslateX() + c,
                        this.getTranslateY() + a + c + a, this.getTranslateX() - thirdRand,
                        this.getTranslateY() + a + c + a - thirdRand,
                        this.getTranslateX() - a, this.getTranslateY() + a + c, this.getTranslateX() - a + fourthRand,
                        this.getTranslateY() + a + fourthRand, this.getTranslateX(), this.getTranslateY() });
        this.setFill(Color.GRAY);
    }

}