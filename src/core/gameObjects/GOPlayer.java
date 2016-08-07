package core.gameObjects;

import core.gameLogic.Game;

/**
 * @author Andrew Lem
 */
public class GOPlayer extends GameObject {
    public static final String SPRITES_SHIP_GIF = "sprites/player.gif";
    public static final int DEFAULT_SHIP_MOVE_SPEED = 300;
    public static final int DEFAULT_FIRING_INTERVAL = 100;
    private long lastFireTime = 0;
    private long firingInterval = DEFAULT_FIRING_INTERVAL;

    public GOPlayer(Game game, int x, int y) {
        super(game, SPRITES_SHIP_GIF, x, y);
        speed = DEFAULT_SHIP_MOVE_SPEED;
    }

    public void move(long delta) {
        // if we're moving left and have reached the left hand side
        // of the screen, don't move
        if ((dx < 0) && (x < Game.SCREEN_EDGE_INNER_BUFFER)) {
            return;
        }
        // if we're moving right and have reached the right hand side
        // of the screen, don't move
        if ((dx > 0) && (x > Game.MAX_X - Game.SCREEN_EDGE_INNER_BUFFER)) {
            return;
        }

        super.move(delta);
    }

    public void moveStop(){
        setHorizontalMovement(0);
    }

    public void moveLeft(){
        setHorizontalMovement(-speed);
    }

    public void moveRight() {
        setHorizontalMovement(speed);
    }

    public void tryToFire() {
        // if too soon after last shot, cannot fire new shot
        if (System.currentTimeMillis() - lastFireTime < firingInterval) {
            return;
        }

        lastFireTime = System.currentTimeMillis();
        GOBullet bullet = new GOBullet(game, getX() + getImageWidth()/2, getY() + getImageWidth()/2);
        bullet.adjustX(-bullet.getImageWidth()/2);
        bullet.adjustY(-bullet.getImageHeight()/2);
        bullet.setDirection(direction);
        bullet.recalcVelocity();
        game.addBullet(bullet);
    }
}