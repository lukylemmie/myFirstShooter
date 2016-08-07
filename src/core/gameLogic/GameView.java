package core.gameLogic;

import core.gameObjects.GOBullet;
import core.gameObjects.GOEnemy;
import core.gameObjects.GOPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created by Andrew on 06/08/2016.
 */
public class GameView extends Canvas {
    public static final String USER_INPUT_PROMPT = "Press any key to start, Press ESC to quit";

    /**
     * The strategy that allows us to use accelerate page flipping
     */
    private BufferStrategy strategy;
    private Game game;
    private UserInput userInput;

    /**
     * The message to display while waiting for a key press
     */
    private String message = "";

    public GameView(Game game, UserInput userInput){
        this.game = game;
        this.userInput = userInput;

        JFrame container = new JFrame("My First Top Down Shooter");

        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(Game.MAX_X, Game.MAX_Y));
        panel.setLayout(null);

        setBounds(0, 0, Game.MAX_X, Game.MAX_Y);
        panel.add(this);

        // Tell AWT not to bother repainting our canvas since we're going to do that our self in accelerated mode
        setIgnoreRepaint(true);

        // make the window visible
        container.pack();
        container.setResizable(false);
        container.setVisible(true);


        // add a listener to respond to the user closing the window. If they do we'd like to exit the game
        container.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        addKeyListener(userInput.getKeyInputHandler());
        addMouseListener(userInput.getMouseInputHandler());
        addMouseMotionListener(userInput.getMouseInputHandler());

        requestFocus();

        // create the buffering strategy which will allow AWT to manage our accelerated graphics
        createBufferStrategy(2);
        strategy = getBufferStrategy();

        game.initGameObjects();
    }



    public void drawGameObjects(GOPlayer ship, ArrayList<GOEnemy> enemies, ArrayList<GOBullet> bullets) {
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, Game.MAX_X, Game.MAX_Y);

        ship.draw(g);
        for (GOEnemy enemy : enemies){
            enemy.draw(g);
        }
        for (GOBullet bullet : bullets){
            bullet.draw(g);
        }

        if (userInput.isWaitingForKeyPress()) {
            g.setColor(Color.white);
            g.drawString(message,
                    (Game.MAX_X - g.getFontMetrics().stringWidth(message)) / 2,
                    Game.MAX_Y / 2 - Game.SCREEN_EDGE_INNER_BUFFER);
            g.drawString(USER_INPUT_PROMPT,
                    (Game.MAX_X - g.getFontMetrics().stringWidth(USER_INPUT_PROMPT)) / 2,
                    Game.MAX_Y / 2);
        }

        // clear up the graphics and flip the buffer over
        g.dispose();
        strategy.show();
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
