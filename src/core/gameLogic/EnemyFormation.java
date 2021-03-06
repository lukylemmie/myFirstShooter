package core.gameLogic;

import core.gameObjects.LiveObjects.EnemyObjects.EFormation;

import java.util.ArrayList;

/**
 * Created by Andrew on 05/08/2016.
 */
public class EnemyFormation {
    public static final int DEFAULT_ENEMIES_PER_ROW = 12;
    public static final int DEFAULT_ENEMIES_ROWS = 5;
    public static final Double DEFAULT_ENEMY_LEFT_EDGE_X = 100.0;
    public static final Double DEFAULT_ENEMY_GAP_X = 50.0;
    public static final Double DEFAULT_ENEMY_GAP_Y = 30.0;
    public static final Double DEFAULT_ENEMY_TOP_EDGE_Y = 50.0;

    private Game game;
    private ArrayList<EFormation> enemies = new ArrayList<>();
    private long directionChangeTime = 0;

    public EnemyFormation(Game game, int level){
        this.game = game;

        // create a block of enemies (5 rows, by 12 enemies, spaced evenly)
        for (int row = 0; row < DEFAULT_ENEMIES_ROWS; row++) {
            for (int x = 0; x < DEFAULT_ENEMIES_PER_ROW; x++) {
                EFormation enemy = new EFormation(game,
                        DEFAULT_ENEMY_LEFT_EDGE_X + (x * DEFAULT_ENEMY_GAP_X),
                        DEFAULT_ENEMY_TOP_EDGE_Y + row * DEFAULT_ENEMY_GAP_Y,
                        this);
                game.addEnemy(enemy);
                enemies.add(enemy);
            }
        }
    }

    public void remove(EFormation enemy) {
        enemies.remove(enemy);
    }

    public boolean isEmpty() {
        return enemies.isEmpty();
    }

    public void increaseMovementSpeed() {
        for(EFormation enemy : enemies){
            enemy.increaseMovementSpeed();
        }
    }

    public void advanceAndChangeDirection(){
        if(directionChangeTime != game.getLastLoopTime()){
            directionChangeTime = game.getLastLoopTime();

            for(EFormation enemy : enemies){
                enemy.advance();
            }
        }
    }
}
