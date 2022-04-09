package hellofx;

public abstract class ShipSprite extends Sprite {
    ShipSprite(double[] points) {
        super(points);
    }

    public Bullet fireBullet() {
        Bullet newBullet  = new Bullet(this.getNoseX(), this.getNoseY(), this.getRotate(), 2); //testing alien – this.speed 
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
