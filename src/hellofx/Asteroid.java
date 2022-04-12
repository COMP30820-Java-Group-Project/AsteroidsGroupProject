package hellofx;

public class Asteroid extends Sprite {

    // pass parameters to Polygon constructor
    public Asteroid(String size) {
        super();
        createAsteroid(size);
        this.speed = doubleSetSpeed(0.8);
        this.setTranslateX(this.createXCoOrdinates(1000));
        this.setTranslateY(this.createXCoOrdinates(900));
        // Generating a random angle to fly at
        this.setRotate((Math.random() * (360 - 0)) + 0);
    }

    // 2nd constructor for medium and small asteroids when coordinates already
    // determined
    public Asteroid(String size, double x, double y, double s) {
        super();
        createAsteroid(size);
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

    public double createXCoOrdinates(int width) {
        // x denotes starting coordinate
        double x = Math.random() * width;
        // add 200px buffer around center
        while (x > (width / 2) - 100 && x < (width / 2) + 100) {
            x = Math.random() * width;
        }
        return x;
    }

    public double createYCoOrdinates(int height) {
        // y denotes starting coordinate
        double y = Math.random() * height;
        // add 200px buffer around center
        while (y > (height / 2) - 100 && y < (height / 2) + 100) {
            y = Math.random() * height;
        }
        return y;
    }

    private final void createAsteroid(String sizeCategory) {
        double a;
        double c;

        // using Pythagoras' Theorem to get the rest of the co-ordinates required (a^2 +
        // b^2 == c^2)
        // a and b will be the same for octagon so omit b

        switch (sizeCategory) {
            case "large":
                a = 42.42;
                c = 60;
                break;
            case "medium":
                a = 28.28;
                c = 40;
                break;
            case "small":
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
    };
}