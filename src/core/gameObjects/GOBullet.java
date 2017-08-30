package core.gameObjects;

import core.gameLogic.Game;
import core.gameObjects.LiveObjects.EnemyObjects.EFormation;
import core.gameObjects.LiveObjects.LOEnemy;
import core.gameObjects.LiveObjects.LOPlayer;
import core.sprites.SpriteStore;

/**
 * @author Andrew Lem
 */
public class GOBullet extends GameObject {
    public static final Double DEFAULT_BULLET_MOVE_SPEED = 300.0;
    public static final String SPRITES_NEUTRAL_BULLET_GIF = "sprites/neutralBullet.gif";
    public static final String SPRITES_PLAYER_BULLET_GIF = "sprites/playerBullet.gif";
    public static final String SPRITES_ENEMY_BULLET_GIF = "sprites/enemyBullet.gif";

    private Double uses = 1.0;
    private GameObject owner;

    public GOBullet(Game game, Double x, Double y, GameObject owner) {
        super(game, SPRITES_NEUTRAL_BULLET_GIF, x, y);
        if (owner instanceof LOPlayer){
            sprite = SpriteStore.get().getSprite(SPRITES_PLAYER_BULLET_GIF);
        } else if (owner instanceof LOEnemy) {
            sprite = SpriteStore.get().getSprite(SPRITES_ENEMY_BULLET_GIF);
        }

        speed = DEFAULT_BULLET_MOVE_SPEED;
        this.owner = owner;
    }

    @Override
    public boolean collidesWith(GameObject other) {
        if ((other instanceof LOEnemy && owner instanceof LOEnemy) || (other instanceof LOPlayer && owner instanceof LOPlayer)){
            return false;
        } else {
            return super.collidesWith(other);
        }
    }

    public void bulletHits(GOLiveObject target){

        // prevents double kills, if we've already hit something, don't collide
        if (!isUsed()) {
            target.takeDamage(1.0);
            uses--;
        }
    }

    public boolean isUsed(){
        return uses < 1;
    }

    public GameObject getOwner() {
        return owner;
    }

    public void setOwner(GameObject owner) {
        this.owner = owner;
    }
}