package hellofx;

public class Asteroid extends Sprite {
    
    // pass parameters to Polygon constructor
    public Asteroid(String size) {
        super();
        createShip(size);
        this.speed = doubleSetSpeed(0.8);
        this.setTranslateX(this.createXCoOrdinates(1000));
        this.setTranslateY(this.createXCoOrdinates(900));
        // Generating a random angle to fly at
        this.setRotate((Math.random() * (360 - 0)) + 0);
    }

    // 2nd constructor for medium and small asteroids when coordinates already determined
    public Asteroid(String size, double x, double y, double s) {
        super();
        createShip(size);
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
        while (x>(width/2)-100 && x<(width/2)+100) {
            x = Math.random() * width;
        }
        return x;
    }
    public double createYCoOrdinates(int height) {
        // y denotes starting coordinate
        double y = Math.random() * height;
        // add 200px buffer around center
        while (y>(height/2)-100 && y<(height/2)+100) {
            y = Math.random() * height;
        }
        return y;
    }
        
    public void createShip(String sizeCategory) { 
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
           .addAll(new Double[]
           { this.getTranslateX(), this.getTranslateY(), this.getTranslateX() + c, this.getTranslateY(), this.getTranslateX() + a + c, this.getTranslateY() + a,
            this.getTranslateX() + a + c, this.getTranslateY() + a + c, this.getTranslateX() + c, this.getTranslateY() + a + c + a, this.getTranslateX(), this.getTranslateY() + a + c + a,
            this.getTranslateX() - a, this.getTranslateY() + a + c, this.getTranslateX() - a, this.getTranslateY() + a });
        };
}