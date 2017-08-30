package core.gameObjects.LiveObjects.EnemyObjects;

import core.gameLogic.Game;
import core.gameObjects.LiveObjects.LOEnemy;

/**
 * Created by Andrew on 14/08/2016.
 */
public class EChaseNShoot extends LOEnemy {
    public EChaseNShoot(Game game, Double x, Double y, Double hp) {
        super(game, x, y, hp);

        actionInterval = EShooter.DEFAULT_SHOOTER_ACTION_INTERVAL;
    }

    public void goTo(Double x, Double y){
        turnToLookAt(x, y);
        calcVelocity();
    }

    public void shootAt(Double x, Double y){
        turnToLookAt(x, y);
        tryToFire();
    }
}
