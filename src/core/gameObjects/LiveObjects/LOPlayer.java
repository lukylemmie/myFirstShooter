package core.gameObjects.LiveObjects;

import core.gameLogic.Game;
import core.gameObjects.GOBullet;
import core.gameObjects.GOLiveObject;

/**
 * @author Andrew Lem
 */
public class LOPlayer extends GOLiveObject {
    public static final String SPRITES_SHIP_GIF = "sprites/player.gif";
    public static final Double DEFAULT_SHIP_MOVE_SPEED = 200.0;
    public static final Double DEFAULT_PLAYER_HP = 3.0;

    public LOPlayer(Game game, Double x, Double y) {
        super(game, SPRITES_SHIP_GIF, x, y, DEFAULT_PLAYER_HP);

        speed = DEFAULT_SHIP_MOVE_SPEED;
    }

    public void move(long delta) {
        if (((dx < 0) && (x < Game.SCREEN_EDGE_INNER_BUFFER)) || ((dx > 0) && (x > Game.MAX_X - Game.SCREEN_EDGE_INNER_BUFFER))) {
            dx = 0;
        }
        if (((dy < 0) && (y < Game.SCREEN_EDGE_INNER_BUFFER)) || ((dy > 0) && (y > Game.MAX_Y - Game.SCREEN_EDGE_INNER_BUFFER))) {
            dy = 0;
        }

        super.move(delta);
    }
}