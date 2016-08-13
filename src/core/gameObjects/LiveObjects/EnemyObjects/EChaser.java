package core.gameObjects.LiveObjects.EnemyObjects;

import core.gameLogic.Game;
import core.gameObjects.LiveObjects.LOEnemy;

/**
 * Created by Andrew on 13/08/2016.
 */
public class EChaser extends LOEnemy {
    public EChaser(Game game, int x, int y, int hp) {
        super(game, x, y, hp);
    }

    public void goTo(int x, int y){
        turnToLookAt(x, y);
        calcVelocity();
    }
}
