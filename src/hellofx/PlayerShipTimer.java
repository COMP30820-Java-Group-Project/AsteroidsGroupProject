package hellofx;

import javafx.animation.AnimationTimer;

public class PlayerShipTimer extends AnimationTimer {
    private PlayerShip player;

    PlayerShipTimer(PlayerShip player) {
        this.player = player;
    }

    @Override
    public void handle(long now) {
        double playerAngle = player.getRotate();
        int playerSpeed = player.getSpeed();
        double currentX = player.getTranslateX();
        double currentY = player.getTranslateY();
        double currentVelocityX = playerSpeed * Math.sin(Math.toRadians(playerAngle));
        double currentVelocityY = -playerSpeed * Math.cos(Math.toRadians(playerAngle));
        player.setTranslateX(currentX + currentVelocityX);
        player.setTranslateY(currentY + currentVelocityY);
    }
}
