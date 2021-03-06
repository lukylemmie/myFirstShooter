package core.gameObjects.LiveObjects.EnemyObjects;

import core.gameLogic.Game;
import core.gameObjects.LiveObjects.LOEnemy;

/**
 * Created by Andrew on 13/08/2016.
 */
public class EShooter extends LOEnemy {

    public static final int DEFAULT_SHOOTER_ACTION_INTERVAL = 1000;

    public EShooter(Game game, Double x, Double y, Double hp) {
        super(game, x, y, hp);

        speed = 0;
        actionInterval = DEFAULT_SHOOTER_ACTION_INTERVAL;
    }

    public void shootAt(Double x, Double y){
        turnToLookAt(x, y);
        tryToFire();
    }
}
