package core.gameObjects;

import core.gameLogic.EnemyFormation;
import core.gameLogic.Game;

/**
 * @author Andrew Lem
 */
public class GOEnemy extends GameObject {
    public static final String SPRITES_ENEMY_GIF = "sprites/enemy.gif";
    public static final int DEFAULT_ENEMY_MOVE_SPEED = 75;
    public static final double DEFAULT_ENEMY_MOVE_SPEED_INCREASE = 1.03;

    private EnemyFormation enemyFormation;
    private int hp = 1;

    public GOEnemy(Game game, int x, int y, EnemyFormation enemyFormation) {
        super(game, SPRITES_ENEMY_GIF, x, y);

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

    //TODO refactor
    public void collidedWith(GameObject other) {
        // collisions with enemies are handled elsewhere
    }

    public void increaseMovementSpeed() {
        setHorizontalMovement(getHorizontalMovement() * DEFAULT_ENEMY_MOVE_SPEED_INCREASE);
    }

    public void advance() {
        dx = -dx;
        y += 10;

        if (y > Game.MAX_Y - Game.SCREEN_EDGE_INNER_BUFFER) {
            game.notifyDeath();
        }
    }

    public void takeDamage(int damage){
        hp -= damage;
    }

    public boolean isDead(){
        return hp <= 0;
    }
}