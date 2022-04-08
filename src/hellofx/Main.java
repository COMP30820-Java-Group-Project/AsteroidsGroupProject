package hellofx;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;

public class Main extends Application {

    // Lets us add children
    //private Pane root = new Pane();

    // private PlayerShip player = new PlayerShip(900, 600);
    // List<Bullet> bullets = new ArrayList<>();


    // private Parent createContent() {
    //     root.setPrefSize(1000, 900);

    //     root.getChildren().add(player);
  
    //     return root;
    // }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = new Pane();
        final int SCREENWIDTH = 1000;
        final int SCREENHEIGHT = 900;
        root.setPrefSize(SCREENWIDTH, SCREENHEIGHT);
        primaryStage.setTitle("Hello Asteroids");
        Scene scene = new Scene(root);
        // Point2D point2d_1 = new Point2D(20.0f, 150.0f);
        // PlayerShip player = new PlayerShip(point2d_1.getX(), point2d_1.getY());
        PlayerShip player = new PlayerShip(SCREENWIDTH/2, SCREENHEIGHT/2);
        List<Bullet> bullets = new ArrayList<>();
        // continuous inputs 
        List<String> constantPress = new ArrayList<String>();
        // discrete inputs
        List<String> onePress = new ArrayList<String>();

        root.getChildren().add(player);

        new AnimationTimer() {
            
        @Override
        public void handle(long now) {
        // call move method initially so that movement is constant
        player.move();
        scene.setOnKeyPressed(e -> {
                    String keyName = e.getCode().toString();
                    if (!constantPress.contains(keyName)){
                        // add only if not in list already
                        constantPress.add(keyName);
                        // will never already contain given it is cleared after handling
                        onePress.add(keyName);
                    }
                });

        scene.setOnKeyReleased(e -> {
                    String keyName = e.getCode().toString();
                    if (constantPress.contains(keyName)){
                        constantPress.remove(keyName);
                    }
                });
        // scene.setOnKeyPressed(e -> {
            if (constantPress.contains("UP")) {
                player.changeSpeed(0.1);
            }
            if (constantPress.contains("DOWN")) {
                player.changeSpeed(-0.1);
            }
            if (constantPress.contains("LEFT")) {
                player.changeAngle("left");
            }
            if (constantPress.contains("RIGHT")) {
                player.changeAngle("right");
            }
            if (onePress.contains("SPACE")) {
                Bullet b  = player.fireBullet();
                root.getChildren().add(b);
                bullets.add(b);
            }
            if (onePress.contains("H")) {
                // call hyperspace method
                player.hyperspace(SCREENWIDTH, SCREENHEIGHT);
                // keep hyperspacing while there is an intersection
                 // end product will not actually be checking for intersection of bullets but can use this for asteroids and alien ship  
                while (bulletIntersects(bullets, player)){
                    player.hyperspace(SCREENWIDTH, SCREENHEIGHT);
                }
            }
            // clear list so handled only once
            onePress.clear();
            
            // switch(e.getCode()) {
            //     case UP:
            //         player.changeSpeed(1);
            //         break;
            //     case DOWN:
            //         player.changeSpeed(-1);
            //         break;
            //     case LEFT:
            //         player.changeAngle("left");
            //         break;
            //     case RIGHT:
            //         player.changeAngle("right");
            //         break;
            //     case SPACE:
            //         Bullet b  = new Bullet(player.getNoseX(), player.getNoseY(), player.getRotate(), player.getSpeed());
            //         root.getChildren().add(b);
            //         bullets.add(b);
            //         break;
            // }
        // });
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            b.move();
            if (System.currentTimeMillis() - b.startTime > 2000){
                // remove from list
                bullets.remove(b);
                // remove from screen
                root.getChildren().remove(b);
            }
        }
        }}.start();
        //AnimationTimer timer = new PlayerShipTimer(player);
        //timer.start();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    // end product will not actually be checking for intersection of bullets but can use this for asteroids and alien ship  
    // need to find a better class for this type of method to be in    
    // method that takes list of bullets and check if they intersect with the Shape. Returns true if one bullet intersects, false otherwise
    public boolean bulletIntersects(List<Bullet> L, Shape s) {
        for (int i = 0; i < L.size(); i++) {
            Bullet b = L.get(i);    
            Shape area = Shape.intersect(b, s);
            if (area.getBoundsInLocal().getWidth() > 0) {
                return true;
            }
        }
        return false;
        }
    public static void main(String[] args) {
        launch(args);
    }
}
