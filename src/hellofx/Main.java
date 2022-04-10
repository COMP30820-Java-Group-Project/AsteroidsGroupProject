package hellofx;

import java.beans.EventHandler;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.StackPane;

public class Main extends Application {
    AtomicInteger points = new AtomicInteger();
    int lives = 6;
    int level = 1;
    int largeAsteroids = 1;
    List<Asteroid> allAster = new ArrayList<>();
    List<Asteroid> largeAster = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        final int SCREENWIDTH = 1000;
        final int SCREENHEIGHT = 900;
        root.setPrefSize(SCREENWIDTH, SCREENHEIGHT);
        primaryStage.setTitle("Hello Asteroids");
        Scene scene = new Scene(root);

        Text pointsDisplay = new Text(20, 30, "Points: " + points);
        root.getChildren().add(pointsDisplay);
        Text levelDisplay = new Text(20, 45, "Level: " + level);
        root.getChildren().add(levelDisplay);
        Text livesDisplay = new Text(20, 60, "Lives: " + lives);
        root.getChildren().add(livesDisplay);

        // Point2D point2d_1 = new Point2D(20.0f, 150.0f);
        // PlayerShip player = new PlayerShip(point2d_1.getX(), point2d_1.getY());

        Alien alien = new Alien();
        List<PlayerShip> players = new ArrayList<>();
        PlayerShip player = new PlayerShip(SCREENWIDTH / 2, SCREENWIDTH / 2);
        players.add(player);
        List<Asteroid> mediumAster = new ArrayList<>();
        List<Asteroid> smallAster = new ArrayList<>();

        List<Bullet> playerBullets = new ArrayList<>();

        List<Bullet> alienBullets = new ArrayList<>();

        // continuous inputs
        List<String> constantPress = new ArrayList<String>();
        // discrete inputs
        List<String> onePress = new ArrayList<String>();

        root.getChildren().add(player);

        int mediumAsteroids = 2;
        int smallAsteroids = 2;
        for (int i = 0; i < largeAsteroids; i++) {
            Asteroid a = new Asteroid("large");
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
                    Bullet b = player.fireBullet();
                    root.getChildren().add(b);
                    playerBullets.add(b);
                }
                if (onePress.contains("H")) {
                    // call hyperspace method
                    player.hyperspace(SCREENWIDTH, SCREENHEIGHT);
                    // keep hyperspacing while there is an intersection
                    // end product will not actually be checking for intersection of bullets but can
                    // use this for asteroids and alien ship
                    while (asteroidIntersects(allAster, player) || generalIntersects(alien, player)
                            || alienBulletIntersets(alienBullets, player)) {
                        player.hyperspace(SCREENWIDTH, SCREENHEIGHT);
                    }
                }
                // clear list so handled only once
                onePress.clear();

                for (int i = 0; i < playerBullets.size(); i++) {
                    Bullet b = playerBullets.get(i);
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
                if (!player.getInvincible()
                        && (asteroidIntersects(allAster, player) || (generalIntersects(alien, player)))) {
                    player.death();
                    lives -= 1;

                    // game over - to be developed
                    if (lives == 0) {
                        // display game over screen

                    }

                    livesDisplay.setText("Lives: " + lives);
                }

                // if currently invincible (after dying)
                if (player.getInvincible()) {
                    if (System.currentTimeMillis() - player.deathtime > 5000) {
                        player.notinvincible();
                    }
                }

                if (!alien.onScreen) {
                    if (System.currentTimeMillis() > alien.spawnTime) {
                        alien.spawn(SCREENWIDTH, SCREENHEIGHT);
                        root.getChildren().add(alien);

                    }
                }
                if (alien.onScreen) {
                    if (System.currentTimeMillis() - alien.changeTime > alien.directionTime) {
                        alien.changeDirection();
                    }
                    // will need to find more general home for this check but cannot put in with
                    // asteroid check as will not work when no asteroids
                    // check if player bullets hit alien
                    for (int j = 0; j < playerBullets.size(); j++) {
                        Bullet b = playerBullets.get(j);
                        if (generalIntersects(b, alien)) {
                            alien.isHit();
                            root.getChildren().remove(alien);
                            playerBullets.remove(b);
                            root.getChildren().remove(b);
                            pointsDisplay.setText("Points: " + points.addAndGet(100));
                        }
                    }
                    if (System.currentTimeMillis() - alien.fireTime > 1500) {
                        alien.pointToPlayer(player.getBoundsCenterX(), player.getBoundsCenterY());
                        Bullet alienBullet = alien.fireBullet();
                        root.getChildren().add(alienBullet);
                        alienBullets.add(alienBullet);
                    }

                }

                for (int i = 0; i < alienBullets.size(); i++) {
                    Bullet b = alienBullets.get(i);
                    b.move();
                    if (System.currentTimeMillis() - b.startTime > 2000) {
                        // remove from list
                        alienBullets.remove(b);
                        // remove from screen
                        root.getChildren().remove(b);
                    }
                    if (generalIntersects(b, player)) {
                        alienBullets.remove(b);
                        root.getChildren().remove(b);
                        player.death();
                        lives -= 1;
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
                    Asteroid a = allAster.get(i);
                    a.move();
                    for (int j = 0; j < playerBullets.size(); j++) {
                        Bullet b = playerBullets.get(j);

                        if (generalIntersects(b, a)) {
                            allAster.remove(a);
                            root.getChildren().remove(a);
                            playerBullets.remove(b);
                            root.getChildren().remove(b);
                            pointsDisplay.setText("Points: " + points.addAndGet(100));
                            if (largeAster.contains(a)) {
                                largeAster.remove(a);
                                for (int k = 0; k < mediumAsteroids; k++) {
                                    Asteroid a2 = new Asteroid("medium", a.getTranslateX(), a.getTranslateY(),
                                            a.getSpeed());
                                    allAster.add(a2);
                                    mediumAster.add(a2);
                                    root.getChildren().add(a2);
                                }
                            }
                            if (mediumAster.contains(a)) {
                                mediumAster.remove(a);
                                for (int k = 0; k < smallAsteroids; k++) {
                                    Asteroid a3 = new Asteroid("small", a.getTranslateX(), a.getTranslateY(),
                                            a.getSpeed());
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
                    // display levelling up message (so far message never goes away)
                    Text levelUpDisplay = new Text(SCREENWIDTH / 2 - 100, SCREENWIDTH / 2 - 50, "LEVEL UP!");
                    levelUpDisplay.setFont(Font.font(50));
                    root.getChildren().add(levelUpDisplay);
                    level += 1;
                    levelDisplay.setText("Level: " + level);
                    largeAsteroids += 1;
                    for (int j = 0; j < largeAsteroids; j++) {
                        Asteroid aNew = new Asteroid("large");
                        allAster.add(aNew);
                        largeAster.add(aNew);
                        root.getChildren().add(aNew);
                    }
                }
                /*
                 * WORK IN PROGRESS: have level up text blink for a few seconds then go away
                 * // if empty and no alien then level is over and make new asteroids
                 * if (allAster.isEmpty() && !alien.onScreen) {
                 * // display levelling up message
                 * Text levelUpText = new Text(SCREENWIDTH / 2 - 100, SCREENWIDTH / 2 - 50,
                 * "LEVEL UP!");
                 * levelUpText.setFont(Font.font(50));
                 * root.getChildren().add(levelUpText);
                 * 
                 * javafx.event.EventHandler<javafx.event.ActionEvent> lvlEventHandler = new
                 * javafx.event.EventHandler<ActionEvent>() {
                 * public void handle(ActionEvent e) {
                 * if (levelUpText.getText().length() != 0) {
                 * levelUpText.setText("");
                 * } else {
                 * levelUpText.setText("LEVEL UP!");
                 * }
                 * }
                 * };
                 * 
                 * Timeline lvlAnimation = new Timeline(new KeyFrame(Duration.ofMillis(300),
                 * lvlEventHandler));
                 * lvlAnimation.setCycleCount(4);
                 * lvlAnimation.play();
                 * 
                 * level += 1;
                 * levelDisplay.setText("Level: " + level);
                 * largeAsteroids += 1;
                 * for (int j = 0; j < largeAsteroids; j++) {
                 * Asteroid aNew = new Asteroid("large");
                 * allAster.add(aNew);
                 * largeAster.add(aNew);
                 * root.getChildren().add(aNew);
                 * }
                 * }
                 */

                // call move method initially so that movement is constant
                // attempt to create new ship on death
                // for (int i = 0; i < players.size(); i++) {
                // PlayerShip p = players.get(i);
                // p.move();
                // if (asteroidIntersects(allAster, p)) {
                // root.getChildren().remove(player);
                // players.remove(p);
                // PlayerShip player = new PlayerShip(SCREENWIDTH/2, SCREENHEIGHT/2);
                // root.getChildren().add(player);
                // players.add(player);
                // }

                // }
            }
        }.start();

        // scene.fillText(pointsText);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // end product will not actually be checking for intersection of bullets but can
    // use this for asteroids and alien ship
    // need to find a better class for this type of method to be in
    // method that takes list of bullets and check if they intersect with the Shape.
    // Returns true if one bullet intersects, false otherwise
    public boolean asteroidIntersects(List<Asteroid> A, Shape s) {
        for (int i = 0; i < A.size(); i++) {
            Asteroid b = A.get(i);
            Shape area = Shape.intersect(b, s);
            if (area.getBoundsInLocal().getWidth() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean alienBulletIntersets(List<Bullet> B, Shape s) {
        for (int i = 0; i < B.size(); i++) {
            Bullet b = B.get(i);
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
