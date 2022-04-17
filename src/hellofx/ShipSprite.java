package hellofx;

public abstract class ShipSprite extends Sprite {

    long fireTime = 0;

    ShipSprite(double[] points) {
        super(points);
    }

    public Bullet fireBullet(BulletType bType) {
        Bullet newBullet  = new Bullet(this.getNoseX(), this.getNoseY(), this.getRotate()-90, 6, bType); // AlienTesting – this.speed;
        this.fireTime = System.currentTimeMillis();
        return newBullet;
    }

    protected double getNoseX() {
        double halfWidth = this.getLayoutBounds().getWidth()/2;
        double squareCenter = this.getTranslateX() + halfWidth;
        double noseX = squareCenter + halfWidth * Math.sin(Math.toRadians(180 - this.getRotate()));
        return noseX; 
    }
 
    protected double getNoseY() {
        double halfWidth = this.getLayoutBounds().getWidth()/2;
        double squareCenter = this.getTranslateY() + halfWidth;
        double noseY = squareCenter + halfWidth * Math.cos(Math.toRadians(180 - this.getRotate()));
        return noseY;
    }
}
