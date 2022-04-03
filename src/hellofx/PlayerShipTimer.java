package hellofx;

import javafx.animation.AnimationTimer;

public class PlayerShipTimer extends AnimationTimer {
    private PlayerShip player;

    PlayerShipTimer(PlayerShip player) {
        this.player = player;
    }

    @Override
    public void handle(long now) {
        double newY = player.getTranslateY() + (-0.5 * player.getSpeed());
        player.setTranslateY(newY);
    }
}
