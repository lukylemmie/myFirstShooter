package core.gameLogic;

import core.gameObjects.GOBullet;
import core.gameObjects.GOEnemy;
import core.gameObjects.GOPlayer;

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
    private ArrayList<GOEnemy> enemies = new ArrayList<>();
    private ArrayList<GOEnemy> removeEnemies = new ArrayList<>();
    private ArrayList<GOBullet> bullets = new ArrayList<>();
    private ArrayList<GOBullet> removeBullets = new ArrayList<>();
    private EnemyFormation enemyFormation;
    private GOPlayer player;
    private UserInput userInput;
    private GameView gameView;
    private boolean mouseControls = true;
    private boolean keyboardControls = false;

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

        player = new GOPlayer(this, MAX_X / 2, MAX_Y - SCREEN_EDGE_INNER_BUFFER);
//        enemyFormation = new EnemyFormation(this, 1);
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
            for (GOEnemy enemy : enemies) {
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
        player.moveStopHorizontal();

        if (mouseControls) {
            if (userInput.getMouseX() - (player.getX() + player.getImageWidth()/2) < -MOVEMENT_BUFFER) {
                player.moveLeft();
            } else if (userInput.getMouseX() - (player.getX() + player.getImageWidth()/2) > MOVEMENT_BUFFER) {
                player.moveRight();
            } else {
                player.moveStopHorizontal();
            }

            if (userInput.getMouseY() - (player.getY() + player.getImageHeight()/2) < -MOVEMENT_BUFFER){
                player.moveUp();
            } else if (userInput.getMouseY() - (player.getY() + player.getImageHeight()/2) > MOVEMENT_BUFFER){
                player.moveDown();
            } else {
                player.moveStopVertical();
            }

            if (userInput.isFirePressed() || userInput.isMouseClick()) {
                player.tryToFire();
            }
        }

        if (keyboardControls) {
            if ((userInput.isLeftPressed()) && (!userInput.isRightPressed())) {
                player.moveLeft();
            } else if ((userInput.isRightPressed()) && (!userInput.isLeftPressed())) {
                player.moveRight();
            }
            if ((userInput.isUpPressed()) && (!userInput.isDownPressed())){
                player.moveUp();
            } else if ((userInput.isDownPressed()) && (!userInput.isUpPressed())){
                player.moveDown();
            }

            if (userInput.isFirePressed() || userInput.isMouseClick()) {
                player.tryToFire();
            }
        }

        player.turnToLookAt(userInput.getMouseX(), userInput.getMouseY());
    }

    private void checkForCollisions() {
        for (GOEnemy enemy : enemies){
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
        for (GOEnemy enemy : removeEnemies){
            enemyFormation.remove(enemy);
            notifyEnemyKilled();
        }
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
        if (enemyFormation.isEmpty()) {
            notifyWin();
        }
        enemyFormation.increaseMovementSpeed();
    }

    public long getLastLoopTime() {
        return lastLoopTime;
    }

    public void addBullet(GOBullet bullet){
        bullets.add(bullet);
    }

    public void addEnemy(GOEnemy enemy){
        enemies.add(enemy);
    }
}
