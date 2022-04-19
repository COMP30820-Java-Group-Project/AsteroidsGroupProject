package asteroidsgame;

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

    // constants for small and medium, large is changed for each level
    final static int MEDIUM_ASTEROID_COUNT = 2;
    final static int SMALL_ASTEROID_COUNT = 2;
    int largeAsteroids = 1;
    List<Sprite> allAster = new ArrayList<>();
    List<Sprite> largeAster = new ArrayList<>();

    public void start(Stage openingStage) {
        Pane openingRoot = new Pane();
        openingRoot.setPrefSize(GAMEDIMENSIONS.SCREENWIDTH.getVal(), GAMEDIMENSIONS.SCREENHEIGHT.getVal());
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
        startButton.getStyleClass().add("button");

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
       
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
        instructionsButton.getStyleClass().add("button");
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
        scoresButton.getStyleClass().add("button");
        scoresButton.setOnAction(event -> {
            // display high scores
            try {
                highScores(openingStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        VBox vbox3 = new VBox(50, scoresButton);
        vbox3.setTranslateX(330);
        vbox3.setTranslateY(600);

        // display all buttons
        openingRoot.getChildren().addAll(vbox, vbox2, vbox3);
    }

    public void instructions(Stage openingStage) {
        Pane openingRoot = new Pane();
        openingRoot.setPrefSize(GAMEDIMENSIONS.SCREENWIDTH.getVal(), GAMEDIMENSIONS.SCREENHEIGHT.getVal());
        openingRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        openingStage.setTitle("Hello Asteroids");

        Text instructionsText = new Text(0, 350,
                "UP key to thrust \nLEFT and RIGHT keys to steer \nSPACEBAR to shoot \nH to jump into hyperspace");
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
        backButton.getStyleClass().add("button");

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
        openingRoot.setPrefSize(GAMEDIMENSIONS.SCREENWIDTH.getVal(), GAMEDIMENSIONS.SCREENHEIGHT.getVal());
        openingRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        openingStage.setTitle("Hello Asteroids");

        Text scoresTitle = new Text(0, 200, "HIGH SCORES");
        scoresTitle.setFont(Font.font("Monospaced", 40));
        scoresTitle.setFill(Color.WHITE);
        scoresTitle.layoutXProperty()
                .bind(openingRoot.widthProperty().subtract(scoresTitle.prefWidth(-1)).divide(2));

        Text scoresText = new Text(400, 400, "");
        scoresText.setFont(Font.font("Monospaced", 30));
        scoresText.setFill(Color.WHITE);

        // read High Scores file and create string of the contents
        // then analyse the score values to extract the top 5 only
        // code from W3Schools
        try {
            File scoresFile = new File("HighScores.txt");
            Scanner myReader = new Scanner(scoresFile);
            String displayScores = "";
            String data = "";
            int lineCount = 0;
            // create a string of file contents separated by newlines
            while (myReader.hasNextLine()) {
                data += (myReader.nextLine() + "\n");
                lineCount += 1;
            }

            // create string array of lines
            String[] scoreLines = data.split("\n");
            // repeat the following block 5 times (number of high scores we want to display)
            for (int high = 0; high < 5; high++) {
                int topScoreSoFar = 0;
                String topLine = "";
                int topScoreIndex = 0;

                // iterate through line array
                for (int line = 0; line < lineCount; line++) {
                    // split up the current line
                    String[] lineItems = scoreLines[line].split(" ");
                    // extract score from line
                    int thisScore = Integer.parseInt(lineItems[lineItems.length - 1]);
                    System.out.println(thisScore);

                    // compare and update topScore of iteration
                    if (thisScore > topScoreSoFar) {
                        topScoreSoFar = thisScore;
                        topLine = scoreLines[line];
                        // keep track of index of the highest score to remove later
                        topScoreIndex = line;
                    }
                }
                // add the line featuring the top score of iteration & username to the score
                // display
                displayScores += (topLine.trim() + "\n");
                // alter line to a blank string (quicker than to convert to ArrayList then
                // delete)
                // so that the next highest score can be retrieved next iteration
                scoreLines[topScoreIndex] = "deletedUser 0";
            }

            // System.out.println(data);
            myReader.close();
            // set the high scores scene text to the contents of the file
            scoresText.setText(displayScores);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        openingRoot.getChildren().addAll(scoresText, scoresTitle);
        Scene scene = new Scene(openingRoot);
        openingStage.setScene(scene);
        openingStage.show();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Button backButton = new Button("MAIN MENU");
        backButton.getStyleClass().add("button");

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
        closingRoot.setPrefSize(GAMEDIMENSIONS.SCREENWIDTH.getVal(), GAMEDIMENSIONS.SCREENHEIGHT.getVal());
        closingRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        openingStage.setTitle("Asteroids");

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
                String username = userInput.getText();

                // validate username isn't an empty string or just spaces
                boolean validUsername = false;
                for (int i = 0; i < username.length(); i++) {
                    // we want to check that there is at least one character that isn't a whitespace
                    if (!Character.isWhitespace(username.charAt(i))) {
                        validUsername = true;
                        break;
                    }
                }
                // if user leaves blank we assign anon
                if (!validUsername) {
                    username = "anon";
                }
                // formulate HighScores entry
                System.out.println("text entered: " + userInput.getText());

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
        startButton.getStyleClass().add("button");

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
        vbox.setTranslateX(360);
        vbox.setTranslateY(500);

        Button mainMenu = new Button("MAIN MENU");
        mainMenu.setId("button");

        mainMenu.setOnAction(event -> {
            try {
                start(openingStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        VBox vbox2 = new VBox(50, mainMenu);
        vbox2.setTranslateX(325);
        vbox2.setTranslateY(600);

        Button exitGame = new Button("EXIT");
        exitGame.getStyleClass().add("button");

        exitGame.setOnAction(event -> {
            try {
                openingStage.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        VBox vbox3 = new VBox(50, exitGame);
        vbox3.setTranslateX(400);
        vbox3.setTranslateY(700);

        closingRoot.getChildren().addAll(vbox, vbox2, vbox3);
    }

    public void gamePlay(Stage openingStage) throws Exception {
        Pane root = new Pane();
        root.setPrefSize(GAMEDIMENSIONS.SCREENWIDTH.getVal(), GAMEDIMENSIONS.SCREENHEIGHT.getVal());
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

        Alien alien = new Alien();
        List<PlayerShip> players = new ArrayList<>();
        PlayerShip player = new PlayerShip(GAMEDIMENSIONS.SCREENWIDTH.getVal() / 2, GAMEDIMENSIONS.SCREENHEIGHT.getVal() / 2);
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
                    player.hyperspace();
                    // keep hyperspacing while there is an intersection
                    while (Sprite.listHasIntersection(allAster, player)
                            || Sprite.shapesHaveIntersection(alien, player)
                            || Sprite.listHasIntersection(alienBullets, player)) {
                        player.hyperspace();
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
                        alien.spawn();
                        while (Sprite.shapesHaveIntersection(alien, player)) {
                            alien.spawn();
                        }
                        root.getChildren().add(alien);
                    }
                }

                if (alien.checkOnScreen()) {
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
                    if (System.currentTimeMillis() - b.startTime > 1000) {
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
                                for (int k = 0; k < MEDIUM_ASTEROID_COUNT; k++) {
                                    Asteroid a2 = new Asteroid(AsteroidSizes.MEDIUM, a.getTranslateX(),
                                            a.getTranslateY(), a.getSpeed());
                                    allAster.add(a2);
                                    mediumAster.add(a2);
                                    root.getChildren().add(a2);
                                }
                            }
                            if (mediumAster.contains(a)) {
                                mediumAster.remove(a);
                                for (int k = 0; k < SMALL_ASTEROID_COUNT; k++) {
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

        openingStage.setScene(scene);
        openingStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
