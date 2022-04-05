package hellofx;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        PlayerShip player = new PlayerShip(SCREENWIDTH/2, SCREENHEIGHT/2);
        List<Bullet> bullets = new ArrayList<>();

        // Test bounds code
        Polyline testBounds = new Polyline();

        // Coordinates
        double playerMinX = player.getLayoutBounds().getMinX();
        double playerMaxX = player.getLayoutBounds().getMaxX();
        double playerMinY = player.getLayoutBounds().getMinY();
        double playerMaxY = player.getLayoutBounds().getMaxY();
        double boundsWidth = player.getLayoutBounds().getWidth();
        double boundsHeight = player.getLayoutBounds().getHeight();


        testBounds.getPoints().addAll(new Double[]{
            playerMinX, playerMinY,
            playerMinX+boundsWidth, playerMinY,
            playerMaxX, playerMaxY,
            playerMinX, playerMinY + boundsHeight,
            playerMinX, playerMinY,
        });

        testBounds.setTranslateX(SCREENWIDTH/2);
        testBounds.setTranslateY(SCREENHEIGHT/2);


        root.getChildren().add(player);
        root.getChildren().add(testBounds);

        new AnimationTimer() {
            
        @Override
        public void handle(long now) {
        // call move method initially so that movement is constant
        player.move();
        scene.setOnKeyPressed(e -> {
            switch(e.getCode()) {
                case UP:
                    player.changeSpeed(1);
                    break;
                case DOWN:
                    player.changeSpeed(-1);
                    break;
                case LEFT:
                    player.changeAngle("left");
                    break;
                case RIGHT:
                    player.changeAngle("right");
                    break;
                case SPACE:
                    Bullet b  = new Bullet(player.getNoseX(), player.getNoseY(), player.getRotate());
                    root.getChildren().add(b);
                    bullets.add(b);
                    break;
            }
        });
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

    public static void main(String[] args) {
        launch(args);
    }
}
