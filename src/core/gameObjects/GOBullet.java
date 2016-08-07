package core.gameObjects;

import core.gameLogic.Game;

/**
 * @author Andrew Lem
 */
public class GOBullet extends GameObject {
    public static final int DEFAULT_BULLET_MOVE_SPEED = -300;
    public static final String SPRITES_BULLET_GIF = "sprites/bullet.gif";
    private int uses = 1;

    public GOBullet(Game game, int x, int y) {
        super(game, SPRITES_BULLET_GIF, x, y);
        speed = DEFAULT_BULLET_MOVE_SPEED;
        dy = speed;
    }

    public void bulletHitsEnemy(GOEnemy enemy){
        // prevents double kills, if we've already hit something, don't collide
        if (!isUsed()) {
            enemy.takeDamage(1);
            uses--;
        }
    }

    public boolean isUsed(){
        return uses < 1;
    }
}