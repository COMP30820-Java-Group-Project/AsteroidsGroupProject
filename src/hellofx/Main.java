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
        root.setPrefSize(1000, 900);
        primaryStage.setTitle("Hello Asteroids");
        Scene scene = new Scene(root);
        PlayerShip player = new PlayerShip(900, 600);
        List<Bullet> bullets = new ArrayList<>();
        root.getChildren().add(player);

        new AnimationTimer() {
            
        @Override
        public void handle(long now) {
        // call move function initially so that movement is constant
        player.move();
        scene.setOnKeyPressed(e -> {
            switch(e.getCode()) {
                case UP:
                    player.changeSpeed(1);
                    break;
                    // dont think this should be an allowed key
                // case DOWN:
                //     player.changeSpeed(-1);
                //     break;
                case LEFT:
                    player.changeAngle("left");
                    break;
                case RIGHT:
                    player.changeAngle("right");
                    break;
                case DOWN:
                    Bullet b  = new Bullet(player.getTranslateX(), player.getTranslateY());
                    root.getChildren().add(b);
                    bullets.add(b);
                    break;
            }
        });
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
