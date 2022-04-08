package hellofx;

public class Alien extends ShipSprite {
    double startX = 0.0;
    double startY = 20.0;

    private static double[] alienCoordinates = { 
        0.0, 20.0, 
        20.0, 0.0, 
        40.0, 0.0,
        60.0, 20.0,
        40.0, 40.0,
        30.0, 60.0,
        20.0, 40.0,
        0.0, 20.0,
        };
        
    private double angleChange;
    
    private double alienHealth;
    
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