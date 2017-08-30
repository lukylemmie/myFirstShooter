package core.gameObjects;

import core.gameLogic.Game;

import java.awt.*;

/**
 * Created by Andrew on 13/08/2016.
 */
public abstract class GOLiveObject extends GameObject {
    public static final long DEFAULT_ACTION_INTERVAL = 200;

    protected Double hp;
    protected Double maxHp;
    protected long lastActionTime = 0;
    protected long actionInterval = DEFAULT_ACTION_INTERVAL;

    public GOLiveObject(Game game, String ref, Double x, Double y, Double hp) {
        super(game, ref, x, y);

        this.hp = hp;
        this.maxHp = hp;
    }

    public void takeDamage(Double damage){
        hp -= damage;
    }

    public boolean isDead(){
        return hp <= 0;
    }

    public Double getHp() {
        return hp;
    }

    public void setHp(Double hp) {
        this.hp = hp;
    }

    public void tryToFire() {
        // if too soon after last shot, cannot fire new shot
        if (System.currentTimeMillis() - lastActionTime < actionInterval) {
            return;
        }

        lastActionTime = System.currentTimeMillis();
        GOBullet bullet = new GOBullet(game, getX(), getY(), this);
        bullet.setBearing(bearing);
        bullet.calcVelocity();
        game.addBullet(bullet);
    }

    public void drawHP(Graphics2D g){
        g.setColor(Color.GREEN);
        g.fillRect((int) Math.ceil(x - getImageWidth()/2), (int) Math.ceil(y - getImageHeight()/2) - 5,
                getImageWidth().intValue(), 2);
        g.setColor(Color.RED);
        double redLength = (maxHp-hp)/(double)maxHp*getImageWidth();
        double redStart = x + Math.ceil((double)getImageWidth()/2) - redLength;
        g.fillRect((int) redStart, (int) Math.ceil(y - getImageHeight()/2) - 5, (int) redLength, 2);
    }
}
