package hellofx;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class Main extends Application {

    // Lets us add children
    private Pane root = new Pane();

    private PlayerShip player = new PlayerShip(475, 600);

    private Parent createContent() {
        root.setPrefSize(1000, 900);

        root.getChildren().add(player);
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello Asteroids");
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(e -> {
            switch(e.getCode()) {
                case W:
                    player.changeSpeed(1);
                    break;
                case S:
                    player.changeSpeed(-1);
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread shipFlightThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    double newY = player.getTranslateY() + (-0.25 * player.getSpeed());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            player.setTranslateY(newY);
                        }
                    });
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } 
            }
        });

        shipFlightThread.start();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
