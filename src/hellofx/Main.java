package hellofx;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
import javafx.scene.text.Text;

public class Main extends Application {
    AtomicInteger points = new AtomicInteger();
    int lives = 0;
    int level = 0;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = new Pane();
        final int SCREENWIDTH = 1000;
        final int SCREENHEIGHT = 900;
        root.setPrefSize(SCREENWIDTH, SCREENHEIGHT);
        primaryStage.setTitle("Hello Asteroids");
        Scene scene = new Scene(root);

        Text pointsDisplay = new Text(20,30, "Points: " + points);
        root.getChildren().add(pointsDisplay);
        // Point2D point2d_1 = new Point2D(20.0f, 150.0f);
        // PlayerShip player = new PlayerShip(point2d_1.getX(), point2d_1.getY());
        PlayerShip player = new PlayerShip(SCREENWIDTH/2, SCREENHEIGHT/2);
        Alien alien = new Alien(SCREENWIDTH/3,SCREENHEIGHT/6);
        List<Asteroid> allAster = new ArrayList<>();
        List<Asteroid> largeAster = new ArrayList<>();
        List<Asteroid> mediumAster = new ArrayList<>();
        List<Asteroid> smallAster = new ArrayList<>();
        List<Bullet> bullets = new ArrayList<>();
        
        // continuous inputs 
        List<String> constantPress = new ArrayList<String>();
        // discrete inputs
        List<String> onePress = new ArrayList<String>();

        root.getChildren().add(player);
        root.getChildren().add(alien);

        int largeAsteroids = 5;
        int mediumAsteroids = 2;
        int smallAsteroids = 2;
        for (int i=0; i<largeAsteroids;i++) {
            Asteroid a  = new Asteroid("large");
            allAster.add(a);
            largeAster.add(a);
            root.getChildren().add(a);
        }

        new AnimationTimer() {
            
        @Override
        public void handle(long now) {
        // call move method initially so that movement is constant
        player.move();
        alien.move();
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
        // this should probably be moved somewhere else
        // loop through list of all asteroids to move them and check if any of them intersect with a bullet
        // if there is an intersection, then check if it is of a certain size
        // if of a certain size, either create more and delete from list, or delete from list if small
        for (int i = 0; i < allAster.size(); i++) {
            Asteroid a = allAster.get(i);
            a.move();
            for (int j = 0; j < bullets.size(); j++) {
                Bullet b = bullets.get(j);
                if (generalIntersects(b, a)) {
                    allAster.remove(a);
                    root.getChildren().remove(a);
                    bullets.remove(b);
                    root.getChildren().remove(b);
                    pointsDisplay.setText("Points: " + points.addAndGet(100));
                    if (largeAster.contains(a)) {
                        largeAster.remove(a);
                        for (int k=0; k<mediumAsteroids;k++) {
                            Asteroid a2  = new Asteroid("medium", a.getTranslateX(), a.getTranslateY(), a.getSpeed());
                            allAster.add(a2);
                            mediumAster.add(a2);
                            root.getChildren().add(a2);
                        }
                    }
                    if (mediumAster.contains(a)) {
                        mediumAster.remove(a);
                        for (int k=0; k<smallAsteroids;k++) {
                            Asteroid a3  = new Asteroid("small", a.getTranslateX(), a.getTranslateY(), a.getSpeed());
                            allAster.add(a3);
                            smallAster.add(a3);
                            root.getChildren().add(a3);
                        }
                    }
                    if (smallAster.contains(a)) {
                        smallAster.remove(a);
                    }
                }
            }
        }
        // for (int i = 0; i < largeAster.size(); i++) {
        //     Asteroid a = largeAster.get(i);
        //     a.move();
        //     for (int j = 0; j < bullets.size(); j++) {
        //         Bullet b = bullets.get(j);
        //     if (generalIntersects(b, a)) {
        //         largeAster.remove(a);
        //         root.getChildren().remove(a);
        //         bullets.remove(b);
        //         root.getChildren().remove(b);
        //         for (int k=0; k<mediumAsteroids;k++) {
        //             Asteroid a2  = new Asteroid("medium");
        //             mediumAster.add(a2);
        //             root.getChildren().add(a2);
        //             a2.move();
        //         }
        //     }
        // }
        

        }}.start();


        //scene.fillText(pointsText);
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

        public boolean generalIntersects(Shape s1, Shape s2) {
                Shape area = Shape.intersect(s1, s2);
                if (area.getBoundsInLocal().getWidth() > 0) {
                    return true;
                }
            
            return false;
            }
    public static void main(String[] args) {
        launch(args);
    }
}
