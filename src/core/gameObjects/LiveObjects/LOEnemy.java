package core.gameObjects.LiveObjects;

import core.gameLogic.Game;
import core.gameObjects.GOLiveObject;

/**
 * Created by Andrew on 13/08/2016.
 */
public abstract class LOEnemy extends GOLiveObject {
    public static final String SPRITES_ENEMY_GIF = "sprites/enemy.gif";
    public static final Double DEFAULT_ENEMY_MOVE_SPEED = 75.0;
    public static final Double DEFAULT_ENEMY_HP = 1.0;


    public LOEnemy(Game game, Double x, Double y, Double hp) {
        super(game, SPRITES_ENEMY_GIF, x, y, hp);

        speed = DEFAULT_ENEMY_MOVE_SPEED;
    }
}
