package hellofx;

import javafx.scene.shape.Polyline;

public class PlayerShip extends Polyline {
    private static double[] shipPoints = {
        25.0, 0.0,
        0.0, 50.0,
        25.0, 25.0,
        25.0, 0.0,
        25.0, 25.0,
        50.0, 50.0,
        25.0, 0.0,
    };
    private int speed = 0;
    private double angleFactor = 14.4;

    PlayerShip(int x, int y) {
        super(PlayerShip.shipPoints);
        setTranslateX(x);
        setTranslateY(y);
    }

    public int getSpeed() {
        return this.speed;
    
    }

     public void wrap() {
        //Rectangle2D screenBounds = Screen.getPrimary().getBounds();
         if (this.getTranslateX() + this.getLayoutBounds().getWidth()/2<0)
         this.setTranslateX(1000+this.getLayoutBounds().getWidth()/2);
         if (this.getTranslateX() > 1000 + this.getLayoutBounds().getWidth()/2)
         this.setTranslateX(-this.getLayoutBounds().getWidth()/2);
         if (this.getTranslateY() + this.getLayoutBounds().getHeight()/2<0)
         this.setTranslateY(900+this.getLayoutBounds().getWidth()/2);
         if (this.getTranslateY() > 900 + this.getLayoutBounds().getHeight()/2)
         this.setTranslateY(-this.getLayoutBounds().getHeight()/2);
     }

    public void changeSpeed(int speedChange) {
        this.speed += speedChange;
        // setting arbitrary max speed - feel free to change
        if (this.getSpeed() > 6) {
            this.speed = 6;
        }
    }

    public double getNoseX() {
        
        // returns x coordinate of nose position
        // using methods inherited from shape class
        // 
        // double angle = Math.atan2(this.getTranslateY(), this.getTranslateX());
    
        double halfHeight = this.getLayoutBounds().getWidth()/2;
        // return this.getTranslateX() + Math.cos(Math.toRadians(angle))*halfHeight - Math.cos(Math.toRadians(angle))*halfHeight;
        //return this.getTranslateX();//+this.getLayoutBounds().getWidth()/2; //+ halfHeight;
        //return (this.getTranslateX() + 2* halfHeight * Math.cos(Math.toRadians(this.getRotate())));

        double halfWidth = this.getLayoutBounds().getWidth()/2;
        double squareCenter = this.getTranslateX() + halfWidth;
        double noseX = squareCenter + halfWidth * Math.sin(Math.toRadians(180 - this.getRotate()));
        return noseX; 

    }
 
    public double getNoseY() {
        // returns y coordinate of nose position
        // using methods inherited from shape class
        // double angle = Math.atan2(this.getTranslateY(), this.getTranslateX());
    
        //double halfHeight = this.getLayoutBounds().getMinY();
        // return this.getTranslateY() + Math.sin(Math.toRadians(angle))*halfHeight + Math.cos(Math.toRadians(angle))*halfHeight;
        //return this.getTranslateY()+this.getLayoutBounds().getHeight()/2; //+this.getLayoutBounds().getMinY();  
        //return (this.getTranslateX() - 2*halfHeight * Math.sin(Math.toRadians(this.getRotate())));

        double halfWidth = this.getLayoutBounds().getWidth()/2;
        double squareCenter = this.getTranslateY() + halfWidth;
        double noseY = squareCenter + halfWidth * Math.cos(Math.toRadians(180 - this.getRotate()));
        return noseY;
    }

    public void changeAngle(String direction) {
        double currentAngle = getRotate();
        if (direction == "left") setRotate(currentAngle - angleFactor);
        else if (direction == "right") setRotate(currentAngle + angleFactor);
    }

    public void move() {
        double playerAngle = this.getRotate();
        int playerSpeed = this.getSpeed();
        double currentX = this.getTranslateX();
        double currentY = this.getTranslateY();
        double currentVelocityX = playerSpeed * Math.sin(Math.toRadians(playerAngle));
        double currentVelocityY = -playerSpeed * Math.cos(Math.toRadians(playerAngle));
        this.setTranslateX(currentX + currentVelocityX);
        this.setTranslateY(currentY + currentVelocityY);
        this.wrap();
    }
}
