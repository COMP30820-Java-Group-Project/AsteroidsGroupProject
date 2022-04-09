package hellofx;
import java.util.Random;

public class Alien extends ShipSprite {

    private static double[] alienCoordinates = { 
        // New coords
        30.0, 0.0,
        40.0, 20.0,
        45.0, 10.0,
        60.0, 25.0,
        55.0, 30.0,
        60.0, 35.0,
        45.0, 50.0,
        40.0, 40.0,
        30.0, 60.0,
        20.0, 40.0,
        15.0, 50.0,
        0.0, 35.0,
        5.0, 30.0,
        0.0, 25.0,
        15.0, 10.0,
        20.0, 20.0,
        30.0, 0.0,
        };
        
    private double angleChange;
    
    Alien(int x, int y) {
        super(Alien.alienCoordinates);
        setTranslateX(x);
        setTranslateY(y);
    }
    
    public double[] getLocation(){
        double xCord = this.getTranslateX();
        double yCord = this.getTranslateY();
        double[] coordinates = {xCord, yCord};
        return coordinates;
    }

    public double getAngleToTraverse(double playerX, double playerY) {
        double opposite = playerX - this.getTranslateX();
        double adjacent = playerY - this.getTranslateY();
        double angleToTraverse = Math.toDegrees(Math.atan(opposite/adjacent));
        return angleToTraverse;
    }

    public void pointToPlayer(double playerX, double playerY) {
        double alienX = this.getNoseX();
        double alienY = this.getNoseY();
        double coordY = playerY - alienY;
        double coordX = playerX - alienX;
        double angleOfDiff = Math.toDegrees(Math.atan2(coordY, coordX));

        // Getting ranNum from -10 to 10, makes alien shoot behind player sometimes
        Random alienTestRan = new Random();
        int angleChange = alienTestRan.nextInt(40-0) + 0;
        angleChange -= 20;
        this.setRotate(angleOfDiff+90.0+angleChange);
    }
   
    public void changeSpeed(int speedChange) {
        this.speed += speedChange;
    }
    
    public void changeAngle(String direction) {
        double currentAngle = getRotate();
        if (direction == "left") setRotate(currentAngle - angleChange);
        else if (direction == "right") setRotate(currentAngle + angleChange);
    }
    public void isHit(){

    }
}
// method to get centre of player ship for aiming bullets
//     public void getPlayerShip(){
//         PlayerShip.get
//         // get location of playership
//         // shoot at location
//     }
//     Bullet shoot() {
//         Bullet b = null;
//         if(cooldown == 0) {
//           b = new Bullet(x,y, player, 5);
//         } 
//         return b;
//       }
      
//       boolean isHit(Bullet b) {
//         if(b.x >= x && b.x <= x + 10 &&
//             b.y >= y && b.y <= y + 10) {
//           return true;
//         }
//         return false;
//       }
// }

// bullet