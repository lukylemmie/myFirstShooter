package core.gameObjects.LiveObjects.EnemyObjects;

import core.gameLogic.EnemyFormation;
import core.gameLogic.Game;
import core.gameObjects.GOLiveObject;
import core.gameObjects.LiveObjects.LOEnemy;

/**
 * @author Andrew Lem
 */
public class EFormation extends LOEnemy {
    public static final double DEFAULT_ENEMY_MOVE_SPEED_INCREASE = 1.03;

    private EnemyFormation enemyFormation;

    public EFormation(Game game, Double x, Double y, EnemyFormation enemyFormation) {
        super(game, x, y, DEFAULT_ENEMY_HP);

        speed = DEFAULT_ENEMY_MOVE_SPEED;
        dx = -speed;
        this.enemyFormation = enemyFormation;
    }

    public void move(long delta) {
        // if enemy reaches edge of screen, enemyFormation advances and turns around
        if (((dx < 0) && (x < 10)) || ((dx > 0) && (x > Game.MAX_X - Game.SCREEN_EDGE_INNER_BUFFER))) {
            enemyFormation.advanceAndChangeDirection();
        }

        super.move(delta);
    }

    public void increaseMovementSpeed() {
        setDx(getDx() * DEFAULT_ENEMY_MOVE_SPEED_INCREASE);
    }

    public void advance() {
        dx = -dx;
        y += 10;

        if (y > Game.MAX_Y - Game.SCREEN_EDGE_INNER_BUFFER) {
            game.notifyDeath();
        }
    }
}