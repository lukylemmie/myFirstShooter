package core.gameObjects.LiveObjects;

import core.gameLogic.Game;
import core.gameObjects.GOLiveObject;

/**
 * Created by Andrew on 13/08/2016.
 */
public abstract class LOEnemy extends GOLiveObject {
    public static final String SPRITES_ENEMY_GIF = "sprites/enemy.gif";
    public static final int DEFAULT_ENEMY_MOVE_SPEED = 75;
    public static final int DEFAULT_ENEMY_HP = 1;


    public LOEnemy(Game game, int x, int y, int hp) {
        super(game, SPRITES_ENEMY_GIF, x, y, hp);

        speed = DEFAULT_ENEMY_MOVE_SPEED;
    }
}
