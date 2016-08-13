package core.gameObjects;

import core.gameLogic.Game;

/**
 * Created by Andrew on 13/08/2016.
 */
public abstract class GOLiveObject extends GameObject {
    public static final int DEFAULT_ACTION_INTERVAL = 200;

    protected int hp;
    private long lastActionTime = 0;
    private long actionInterval = DEFAULT_ACTION_INTERVAL;

    public GOLiveObject(Game game, String ref, int x, int y, int hp) {
        super(game, ref, x, y);

        this.hp = hp;
    }

    public void takeDamage(int damage){
        hp -= damage;
    }

    public boolean isDead(){
        return hp <= 0;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void tryToFire() {
        // if too soon after last shot, cannot fire new shot
        if (System.currentTimeMillis() - lastActionTime < actionInterval) {
            return;
        }

        lastActionTime = System.currentTimeMillis();
        GOBullet bullet = new GOBullet(game, getX() + getImageWidth()/2, getY() + getImageWidth()/2, this);
        bullet.adjustX(-bullet.getImageWidth()/2);
        bullet.adjustY(-bullet.getImageHeight()/2);
        bullet.setBearing(bearing);
        bullet.calcVelocity();
        game.addBullet(bullet);
    }
}
