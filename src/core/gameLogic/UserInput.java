package core.gameLogic;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Andrew on 06/08/2016.
 */
public class UserInput {
    private boolean waitingForKeyPress = true;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean firePressed = false;
    private int mouseX;
    private int mouseY;
    private boolean mouseClick = false;
    private Game game;
    private KeyInputHandler keyInputHandler;
    private MouseInputHandler mouseInputHandler;

    public UserInput(Game game){
        this.game = game;
        this.keyInputHandler = new KeyInputHandler();
        this.mouseInputHandler = new MouseInputHandler();
    }

    public void reset(){
        waitingForKeyPress = true;
        clearPressed();
    }

    public void clearPressed(){
        leftPressed = false;
        rightPressed = false;
        firePressed = false;
        mouseClick = false;
    }

    public boolean isWaitingForKeyPress() {
        return waitingForKeyPress;
    }

    public KeyInputHandler getKeyInputHandler() {
        return keyInputHandler;
    }

    public MouseInputHandler getMouseInputHandler() {
        return mouseInputHandler;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isFirePressed() {
        return firePressed;
    }

    public boolean isMouseClick(){
        return mouseClick;
    }

    public void waitForKeyPress() {
        waitingForKeyPress = true;
    }

    public int getMouseX(){
        return mouseX;
    }

    public int getMouseY(){
        return mouseY;
    }

    public class KeyInputHandler extends KeyAdapter {
        public static final int ESC_KEY_VALUE = 27;
        private int pressCount = 1;

        public void keyPressed(KeyEvent e) {
            if (waitingForKeyPress) {
                return;
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                firePressed = true;
            }
        }

        public void keyReleased(KeyEvent e) {
            if (waitingForKeyPress) {
                return;
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                firePressed = false;
            }
        }

        public void keyTyped(KeyEvent e) {
            if (waitingForKeyPress) {
                if (pressCount == 1) {
                    waitingForKeyPress = false;
                    game.startGame();
                    pressCount = 0;
                } else {
                    pressCount++;
                }
            }

            if (e.getKeyChar() == ESC_KEY_VALUE) {
                System.exit(0);
            }
        }
    }

    public class MouseInputHandler extends MouseAdapter {

        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }

        public void mouseDragged(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }

        public void mousePressed(MouseEvent e) {
            mouseClick = true;
        }

        public void mouseReleased(MouseEvent e){
            mouseClick = false;
        }
    }
}
