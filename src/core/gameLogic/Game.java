package core.gameLogic;

import core.gameObjects.GOBullet;
import core.gameObjects.LiveObjects.EChaser;
import core.gameObjects.LiveObjects.EFormation;
import core.gameObjects.LiveObjects.LOEnemy;
import core.gameObjects.LiveObjects.LOPlayer;

import java.util.ArrayList;

/**
 * @author Andrew Lem
 */
public class Game {
    public static final int MAX_X = 800;
    public static final int MAX_Y = 600;
    public static final int SCREEN_EDGE_INNER_BUFFER = 50;
    public static final int SCREEN_EDGE_OUTER_BUFFER = 100;
    public static final int MOVEMENT_BUFFER = 1;

    private boolean gameRunning = true;
    private long lastLoopTime = System.currentTimeMillis();
    private ArrayList<LOEnemy> enemies = new ArrayList<>();
    private ArrayList<LOEnemy> removeEnemies = new ArrayList<>();
    private ArrayList<GOBullet> bullets = new ArrayList<>();
    private ArrayList<GOBullet> removeBullets = new ArrayList<>();
    private LOPlayer player;
    private UserInput userInput;
    private GameView gameView;

    public Game() {
        userInput = new UserInput(this);
        gameView = new GameView(this, userInput);
    }

    public static void main(String argv[]) {
        Game g = new Game();
        g.gameLoop();
    }

    public void initGameObjects() {
        enemies.clear();
        bullets.clear();

        player = new LOPlayer(this, MAX_X / 2, MAX_Y / 2);
        LOEnemy enemy = new EChaser(this, SCREEN_EDGE_INNER_BUFFER, SCREEN_EDGE_INNER_BUFFER, 1);

        enemies.add(enemy);
    }


    public void startGame() {
        initGameObjects();

        userInput.clearPressed();
    }

    public void gameLoop() {
        while (gameRunning) {
            processEnemyMovement();
            moveGameObjects();
            gameView.drawGameObjects(player, enemies, bullets);
            checkForCollisions();
            processUserInput();
            sleepForFPS();
        }
    }

    private void processEnemyMovement() {
        for (LOEnemy enemy : enemies){
            if (enemy instanceof EChaser){
                ((EChaser) enemy).goTo(player.getX(), player.getY());
            }
        }
    }

    public void moveGameObjects(){
        long delta = System.currentTimeMillis() - lastLoopTime;
        lastLoopTime = System.currentTimeMillis();

        if (!userInput.isWaitingForKeyPress()) {
            player.move(delta);
            for (LOEnemy enemy : enemies) {
                enemy.move(delta);
            }
            for (GOBullet bullet : bullets) {
                bullet.move(delta);
                if (bullet.isOffScreen()) {
                    removeBullets.add(bullet);
                }
            }
        }

    }

    private void sleepForFPS() {
        try {
            Thread.sleep(10);
        } catch (Exception e) {

        }
    }

    private void processUserInput() {
        player.turnToLookAt(userInput.getMouseX(), userInput.getMouseY());

        if (player.distanceTo(userInput.getMouseX(), userInput.getMouseY()) > MOVEMENT_BUFFER){
            player.calcVelocity();
        } else {
            player.stopMoving();
        }

        if (userInput.isMouseClick()){
            player.tryToFire();
        }
    }

    private void checkForCollisions() {
        for (LOEnemy enemy : enemies){
            for (GOBullet bullet : bullets){
                if (bullet.collidesWith(enemy)) {
                    bullet.bulletHits(enemy);
                }
                if (enemy.isDead()){
                    if (!removeEnemies.contains(enemy)) {
                        removeEnemies.add(enemy);
                    }
                }
                if (bullet.isUsed()){
                    if (!removeBullets.contains(bullet)) {
                        removeBullets.add(bullet);
                    }
                }
            }

            if(player.collidesWith(enemy)) {
                player.takeDamage(1);
                if (player.isDead()) {
                    notifyDeath();
                }
            }
        }

        enemies.removeAll(removeEnemies);
        removeEnemies.clear();
        bullets.removeAll(removeBullets);
        removeBullets.clear();
    }

    public void notifyDeath() {
        gameView.setMessage("Oh no! They got you, try again?");
        userInput.waitForKeyPress();
    }

    public void notifyWin() {
        gameView.setMessage("Well done! You Win!");
        userInput.waitForKeyPress();
    }

    public void notifyEnemyKilled() {
    }

    public long getLastLoopTime() {
        return lastLoopTime;
    }

    public void addBullet(GOBullet bullet){
        bullets.add(bullet);
    }

    public void addEnemy(EFormation enemy){
        enemies.add(enemy);
    }

}
