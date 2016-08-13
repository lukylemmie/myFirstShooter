package core.gameLogic;

import core.gameObjects.GOBullet;
import core.gameObjects.LiveObjects.LOFormationEnemy;
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
    private ArrayList<LOFormationEnemy> enemies = new ArrayList<>();
    private ArrayList<LOFormationEnemy> removeEnemies = new ArrayList<>();
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
    }


    public void startGame() {
        initGameObjects();

        userInput.clearPressed();
    }

    public void gameLoop() {
        while (gameRunning) {
            moveGameObjects();
            gameView.drawGameObjects(player, enemies, bullets);
            checkForCollisions();
            processUserInput();
            sleepForFPS();
        }
    }

    public void moveGameObjects(){
        long delta = System.currentTimeMillis() - lastLoopTime;
        lastLoopTime = System.currentTimeMillis();

        if (!userInput.isWaitingForKeyPress()) {
            player.move(delta);
            for (LOFormationEnemy enemy : enemies) {
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
        for (LOFormationEnemy enemy : enemies){
            for (GOBullet bullet : bullets){
                if (bullet.collidesWith(enemy)) {
                    bullet.bulletHitsEnemy(enemy);
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
                notifyDeath();
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

    public void addEnemy(LOFormationEnemy enemy){
        enemies.add(enemy);
    }

}
