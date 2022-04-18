package hellofx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

public class Main extends Application {
    AtomicInteger points = new AtomicInteger();
    int lives = 6;
    int level = 1;
    int largeAsteroids = 1;
    List<Sprite> allAster = new ArrayList<>();
    List<Sprite> largeAster = new ArrayList<>();

    // constants for screen dimensions
    final static int SCREENWIDTH = 1000;
    final static int SCREENHEIGHT = 900;

    public void start(Stage openingStage) {
        Pane openingRoot = new Pane();
        openingRoot.setPrefSize(SCREENWIDTH, SCREENHEIGHT);
        openingRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        openingStage.setTitle("Hello Asteroids");
        Text titleText = new Text(0, 300, "Welcome to Asteroids");
        titleText.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 50));
        titleText.setFill(Color.WHITE);
        // bind the text to the centre of the X plane
        titleText.layoutXProperty().bind(openingRoot.widthProperty().subtract(titleText.prefWidth(-1)).divide(2));

        openingRoot.getChildren().addAll(titleText);
        Scene scene = new Scene(openingRoot);
        openingStage.setScene(scene);
        openingStage.show();

        Button startButton = new Button("START");
        startButton.setId("button");
        // String css = getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        // startButton.setFont(Font.font("Monospaced", 50));
        // startButton.setBackground(new Background(new BackgroundFill(Color.BLACK,
        // CornerRadii.EMPTY, Insets.EMPTY)));
        // startButton.setTextFill(Color.WHITE);
        startButton.setOnAction(event -> {
            try {
                gamePlay(openingStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        VBox vbox = new VBox(50, startButton);
        vbox.setTranslateX(400);
        vbox.setTranslateY(400);

        Button instructionsButton = new Button("INSTRUCTIONS");
        instructionsButton.setId("button");
        instructionsButton.setOnAction(event -> {
            try {
                instructions(openingStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        VBox vbox2 = new VBox(50, instructionsButton);
        vbox2.setTranslateX(310);
        vbox2.setTranslateY(500);

        // add high scores button
        Button scoresButton = new Button("HIGH SCORES");
        scoresButton.setFont(Font.font("Monospaced", 50));
        scoresButton.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        scoresButton.setTextFill(Color.WHITE);
        scoresButton.setOnAction(event -> {
            // display high scores
            try {
                highScores(openingStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        VBox vbox3 = new VBox(50, scoresButton);
        vbox3.setTranslateX(400);
        vbox3.setTranslateY(600);

        // display all buttons
        openingRoot.getChildren().addAll(vbox, vbox2, vbox3);
    }

    public void instructions(Stage openingStage) {
        Pane openingRoot = new Pane();
        openingRoot.setPrefSize(SCREENWIDTH, SCREENHEIGHT);
        openingRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        openingStage.setTitle("Hello Asteroids");

        Text instructionsText = new Text(0, 350,
                "UP key to accelerate \nDOWN key to decellerate \nLEFT and RIGHT to steer \nSPACEBAR to shoot \nH to jump into hyperspace");
        instructionsText.setFont(Font.font("Monospaced", 30));
        instructionsText.setFill(Color.WHITE);
        instructionsText.layoutXProperty()
                .bind(openingRoot.widthProperty().subtract(instructionsText.prefWidth(-1)).divide(2));

        openingRoot.getChildren().addAll(instructionsText);
        Scene scene = new Scene(openingRoot);
        openingStage.setScene(scene);
        openingStage.show();

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Button backButton = new Button("MAIN MENU");
        backButton.setId("button");

        backButton.setOnAction(event -> {
            try {
                start(openingStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        VBox vbox = new VBox(50, backButton);
        vbox.setTranslateX(325);
        vbox.setTranslateY(600);

        openingRoot.getChildren().add(vbox);

    }

    public void highScores(Stage openingStage) {
        Pane openingRoot = new Pane();
        openingRoot.setPrefSize(SCREENWIDTH, SCREENHEIGHT);
        openingRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        openingStage.setTitle("Hello Asteroids");

        Text scoresTitle = new Text(0, 350, "HIGH SCORES");
        scoresTitle.setFont(Font.font("Monospaced", 40));
        scoresTitle.setFill(Color.WHITE);
        scoresTitle.layoutXProperty()
                .bind(openingRoot.widthProperty().subtract(scoresTitle.prefWidth(-1)).divide(2));

        Text scoresText = new Text(400, 400, "");
        scoresText.setFont(Font.font("Monospaced", 30));
        scoresText.setFill(Color.WHITE);

        // read High Scores file and create string of the contents
        // code from W3Schools
        try {
            File scoresFile = new File("HighScores.txt");
            Scanner myReader = new Scanner(scoresFile);
            String displayScores = "";
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                displayScores += data + "\n";
                System.out.println(data);
            }
            myReader.close();
            // set the high scores scene text to the contents of the file
            scoresText.setText(displayScores);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        openingRoot.getChildren().addAll(scoresText);
        Scene scene = new Scene(openingRoot);
        openingStage.setScene(scene);
        openingStage.show();

        Button backButton = new Button("MAIN MENU");
        backButton.setFont(Font.font("Monospaced", 50));
        backButton.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        backButton.setTextFill(Color.WHITE);

        backButton.setOnAction(event -> {
            try {
                start(openingStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        VBox vbox = new VBox(50, backButton);
        vbox.setTranslateX(325);
        vbox.setTranslateY(600);

        openingRoot.getChildren().add(vbox);

    }

    public void gameOver(Stage openingStage) {
        Pane closingRoot = new Pane();
        closingRoot.setPrefSize(SCREENWIDTH, SCREENHEIGHT);
        closingRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        openingStage.setTitle("Hello Asteroids");

        Scene scene = new Scene(closingRoot);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Text gameOverText = new Text(0, 200, "Game Over!");
        gameOverText.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 50));
        gameOverText.setFill(Color.WHITE);

        gameOverText.layoutXProperty().bind(closingRoot.widthProperty().subtract(gameOverText.prefWidth(-1)).divide(2));

        String score = "Score: " + points.get();
        Text scoreText = new Text(0, 350, score);
        scoreText.setFont(Font.font("Monospaced", FontPosture.ITALIC, 30));
        scoreText.setFill(Color.WHITE);
        scoreText.layoutXProperty().bind(closingRoot.widthProperty().subtract(gameOverText.prefWidth(-1)).divide(1.8));

        // username input
        Text promptText = new Text(0, 380, "Enter your name to be added to the leaderboard:");
        promptText.setFont(Font.font("Monospaced", 20));
        promptText.setFill(Color.rgb(150, 255, 168));
        promptText.layoutXProperty().bind(closingRoot.widthProperty().subtract(promptText.prefWidth(-1)).divide(2));

        // create message to display when username is provided
        Text doneMessage = new Text(450, 420, "Added!");
        doneMessage.setFont(Font.font("Monospaced", 20));
        doneMessage.setFill(Color.rgb(150, 255, 168));

        // create textfield - enter key triggers event
        TextField userInput = new TextField();
        userInput.setTranslateX(380);
        userInput.setTranslateY(420);
        userInput.setStyle("-fx-background-color: rgb(150, 255, 168); -fx-font-size: 20;");
        userInput.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // formulate HighScores entry
                System.out.println("text entered: " + userInput.getText());
                String username = userInput.getText();
                String scoreEntry = username + " " + points.get();
                System.out.println(scoreEntry);

                // now add entry to the file
                // code from W3Schools
                try {
                    FileWriter addHighScore = new FileWriter("HighScores.txt", true);
                    addHighScore.write("\n" + scoreEntry);
                    addHighScore.flush();
                    addHighScore.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    doneMessage.setText("An error occurred.");
                    e.printStackTrace();
                }

                // replace input box with message
                closingRoot.getChildren().remove(userInput);
                closingRoot.getChildren().addAll(doneMessage);
            }
        });

        closingRoot.getChildren().addAll(gameOverText, scoreText, promptText, userInput);

        openingStage.setScene(scene);
        openingStage.show();

        Button startButton = new Button("RESTART");
        startButton.setId("button");

        startButton.setOnAction(event -> {
            try {
                points.set(0);
                lives = 6;
                level = 1;
                gamePlay(openingStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        VBox vbox = new VBox(50, startButton);
        vbox.setTranslateX(395);
        vbox.setTranslateY(600);

        closingRoot.getChildren().add(vbox);

        Button exitGame = new Button("EXIT");
        exitGame.setId("button");

        exitGame.setOnAction(event -> {
            try {
                openingStage.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        VBox vbox2 = new VBox(50, exitGame);
        vbox2.setTranslateX(430);
        vbox2.setTranslateY(700);

        closingRoot.getChildren().add(vbox2);
    }

    public void gamePlay(Stage openingStage) throws Exception {
        Pane root = new Pane();
        root.setPrefSize(SCREENWIDTH, SCREENHEIGHT);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        openingStage.setTitle("Hello Asteroids");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Text pointsDisplay = new Text(20, 30, "Points: " + points);
        pointsDisplay.setFill(Color.WHITE);
        pointsDisplay.setFont(Font.font("Monospaced"));
        root.getChildren().add(pointsDisplay);
        Text levelDisplay = new Text(20, 45, "Level: " + level);
        levelDisplay.setFill(Color.WHITE);
        levelDisplay.setFont(Font.font("Monospaced"));
        root.getChildren().add(levelDisplay);
        Text livesDisplay = new Text(20, 60, "Lives: " + lives);
        livesDisplay.setFill(Color.WHITE);
        livesDisplay.setFont(Font.font("Monospaced"));
        root.getChildren().add(livesDisplay);
        // Point2D point2d_1 = new Point2D(20.0f, 150.0f);
        // PlayerShip player = new PlayerShip(point2d_1.getX(), point2d_1.getY());

        Alien alien = new Alien();
        List<PlayerShip> players = new ArrayList<>();
        PlayerShip player = new PlayerShip(SCREENWIDTH / 2, SCREENWIDTH / 2);
        players.add(player);
        List<Sprite> mediumAster = new ArrayList<>();
        List<Sprite> smallAster = new ArrayList<>();

        List<Sprite> playerBullets = new ArrayList<>();

        List<Sprite> alienBullets = new ArrayList<>();

        // continuous inputs
        List<String> constantPress = new ArrayList<String>();
        // discrete inputs
        List<String> onePress = new ArrayList<String>();

        root.getChildren().add(player);

        int mediumAsteroids = 2;
        int smallAsteroids = 2;
        for (int i = 0; i < largeAsteroids; i++) {
            Asteroid a = new Asteroid(AsteroidSizes.LARGE);
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
                    if (!constantPress.contains(keyName)) {
                        // add only if not in list already
                        constantPress.add(keyName);
                        // will never already contain given it is cleared after handling
                        onePress.add(keyName);
                    }
                });

                scene.setOnKeyReleased(e -> {
                    String keyName = e.getCode().toString();
                    if (constantPress.contains(keyName)) {
                        constantPress.remove(keyName);
                    }
                });
                if (constantPress.contains("UP")) {
                    player.thrust();
                }
                if (constantPress.contains("LEFT")) {
                    player.changeAngle("left");
                }
                if (constantPress.contains("RIGHT")) {
                    player.changeAngle("right");
                }
                if (onePress.contains("SPACE")) {
                    Bullet b = player.fireBullet(BulletType.PLAYER);
                    root.getChildren().add(b);
                    playerBullets.add(b);
                }
                if (onePress.contains("H")) {
                    // call hyperspace method
                    player.hyperspace(SCREENWIDTH, SCREENHEIGHT);
                    // keep hyperspacing while there is an intersection
                    // end product will not actually be checking for intersection of bullets but can
                    // use this for asteroids and alien ship
                    while (Sprite.listHasIntersection(allAster, player)
                            || Sprite.shapesHaveIntersection(alien, player)
                            || Sprite.listHasIntersection(alienBullets, player)) {
                        player.hyperspace(SCREENWIDTH, SCREENHEIGHT);
                    }
                }
                // clear list so handled only once
                onePress.clear();

                for (int i = 0; i < playerBullets.size(); i++) {
                    Bullet b = (Bullet) playerBullets.get(i);
                    b.move();
                    if (System.currentTimeMillis() - b.startTime > 2000) {
                        // remove from list
                        playerBullets.remove(b);
                        // remove from screen
                        root.getChildren().remove(b);
                    }
                }
                // update for alien and alien bullets
                // if intersection and player not invincible
                if (!player.getInvincible() && (Sprite.listHasIntersection(allAster, player)
                        || (Sprite.shapesHaveIntersection(alien, player) && alien.onScreen))) {
                    player.death();
                    lives -= 1;
                    if (lives < 1) {
                        // reinitailise asteroids
                        largeAsteroids = 1;
                        // end game
                        this.stop();
                        gameOver(openingStage);
                    }
                    livesDisplay.setText("Lives: " + lives);
                }
                // call method to check if invincible
                player.checkInvincible();

                if (!alien.onScreen) {
                    if (System.currentTimeMillis() > alien.spawnTime) {
                        alien.spawn(SCREENWIDTH, SCREENHEIGHT);
                        root.getChildren().add(alien);
                    }
                }

                if (alien.checkOnScreen()) {
                    // will need to find more general home for this check but cannot put in with
                    // asteroid check as will not work when no asteroids
                    // check if player bullets hit alien
                    for (int j = 0; j < playerBullets.size(); j++) {
                        Bullet b = (Bullet) playerBullets.get(j);
                        if (Sprite.shapesHaveIntersection(b, alien)) {
                            alien.isHit();
                            root.getChildren().remove(alien);
                            playerBullets.remove(b);
                            root.getChildren().remove(b);
                            pointsDisplay.setText("Points: " + points.addAndGet(100));
                        }
                    }
                    if (System.currentTimeMillis() - alien.fireTime > 1500) {
                        alien.pointToPlayer(player.getBoundsCenterX(), player.getBoundsCenterY());
                        Bullet alienBullet = alien.fireBullet(BulletType.ALIEN);
                        root.getChildren().add(alienBullet);
                        alienBullets.add(alienBullet);
                    }
                }

                for (int i = 0; i < alienBullets.size(); i++) {
                    Bullet b = (Bullet) alienBullets.get(i);
                    b.move();
                    if (System.currentTimeMillis() - b.startTime > 2000) {
                        // remove from list
                        alienBullets.remove(b);
                        // remove from screen
                        root.getChildren().remove(b);
                    }
                    if (Sprite.shapesHaveIntersection(b, player) && !player.getInvincible()) {
                        alienBullets.remove(b);
                        root.getChildren().remove(b);
                        player.death();
                        lives -= 1;
                        if (lives < 1) {
                            // reinitailise asteroids
                            largeAsteroids = 1;
                            // end game
                            this.stop();
                            gameOver(openingStage);
                        }
                        livesDisplay.setText("Lives: " + lives);
                    }
                }

                // this should probably be moved somewhere else
                // loop through list of all asteroids to move them and check if any of them
                // intersect with a bullet
                // if there is an intersection, then check if it is of a certain size
                // if of a certain size, either create more and delete from list, or delete from
                // list if small
                for (int i = 0; i < allAster.size(); i++) {
                    Asteroid a = (Asteroid) allAster.get(i);
                    a.move();
                    for (int j = 0; j < playerBullets.size(); j++) {
                        Bullet b = (Bullet) playerBullets.get(j);

                        if (Sprite.shapesHaveIntersection(b, a)) {
                            allAster.remove(a);
                            root.getChildren().remove(a);
                            playerBullets.remove(b);
                            root.getChildren().remove(b);
                            pointsDisplay.setText("Points: " + points.addAndGet(100));
                            if (largeAster.contains(a)) {
                                largeAster.remove(a);
                                for (int k = 0; k < mediumAsteroids; k++) {
                                    Asteroid a2 = new Asteroid(AsteroidSizes.MEDIUM, a.getTranslateX(),
                                            a.getTranslateY(), a.getSpeed());
                                    allAster.add(a2);
                                    mediumAster.add(a2);
                                    root.getChildren().add(a2);
                                }
                            }
                            if (mediumAster.contains(a)) {
                                mediumAster.remove(a);
                                for (int k = 0; k < smallAsteroids; k++) {
                                    Asteroid a3 = new Asteroid(AsteroidSizes.SMALL, a.getTranslateX(),
                                            a.getTranslateY(), a.getSpeed());
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
                // if empty and no alien then level is over and make new asteroids
                if (allAster.isEmpty() && !alien.onScreen) {
                    level += 1;
                    levelDisplay.setText("Level: " + level);
                    levelDisplay.setFill(Color.WHITE);
                    largeAsteroids += 1;
                    for (int j = 0; j < largeAsteroids; j++) {
                        Asteroid aNew = new Asteroid(AsteroidSizes.LARGE);
                        allAster.add(aNew);
                        largeAster.add(aNew);
                        root.getChildren().add(aNew);
                    }
                }
            }
        }.start();

        // scene.fillText(pointsText);
        openingStage.setScene(scene);
        openingStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
