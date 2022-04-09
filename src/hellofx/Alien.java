package hellofx;

public class Alien extends ShipSprite {

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
    
    double directionTime;
    long changeTime;
        
    Alien() {
        super(Alien.alienCoordinates);
        super.speed = 4;
        this.setRotate(90);
        this.setTranslateX(100);
        this.setTranslateY(100);
        this.directionTime = 200 + Math.random()* 3000;
        this.changeTime = System.currentTimeMillis();
    }
    
    public double[] getLocation(){
        double xCord = this.getTranslateX();
        double yCord = this.getTranslateY();
        double[] coordinates = {xCord, yCord};
        return coordinates;
    }

    public void spawn(int x, int y) {

    }

    public void changeDirection() {
        this.directionTime = 500 + Math.random()* 3500;
        this.changeTime = System.currentTimeMillis();
        if (this.getRotate()==90) {
            this.setRotate(270);
        }
        else {
            this.setRotate(90);
        }
    }
   
    public void changeSpeed(int speedChange) {
        this.speed += speedChange;
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