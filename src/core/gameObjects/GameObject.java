package core.gameObjects;

import core.gameLogic.Game;
import core.sprites.Sprite;
import core.sprites.SpriteStore;

import java.awt.*;

/**
 * @author Andrew Lem
 */
public abstract class GameObject {
    protected double x;
    protected double y;
    protected Sprite sprite;
    protected double dx;
    protected double dy;
    protected Game game;
    protected double direction;

    private Rectangle me = new Rectangle();
    private Rectangle him = new Rectangle();

    public GameObject(Game game, String ref, int x, int y) {
        this.game = game;
        sprite = SpriteStore.get().getSprite(ref);
        this.x = x;
        this.y = y;
    }

    public void move(long delta) {
        // update the location of the gameObject based on move speeds
        x += (delta * dx) / 1000;
        y += (delta * dy) / 1000;
    }

    public int getX() {
        return (int) x;
    }
    public int getY() {
        return (int) y;
    }
    public double getHorizontalMovement() {
        return dx;
    }
    public void setHorizontalMovement(double dx) {
        this.dx = dx;
    }
    public double getVerticalMovement() {
        return dy;
    }
    public void setVerticalMovement(double dy) {
        this.dy = dy;
    }

    public void draw(Graphics g) {
        sprite.draw(g, (int) x, (int) y);
    }

    public boolean collidesWith(GameObject other) {
        me.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
        him.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());

        return me.intersects(him);
    }

    public int getImageWidth(){
        return sprite.getWidth();
    }

    public int getImageHeight(){
        return sprite.getHeight();
    }

    public void adjustX(double shift) {
        x += shift;
    }

    public void adjustY(double shift) {
        y += shift;
    }

    public boolean isOffScreen(){
        boolean offScreen = false;
        if (x < 0 - Game.SCREEN_EDGE_OUTER_BUFFER || x > Game.MAX_X + Game.SCREEN_EDGE_OUTER_BUFFER
                || y < 0 - Game.SCREEN_EDGE_OUTER_BUFFER || y > Game.MAX_Y + Game.SCREEN_EDGE_OUTER_BUFFER){
            offScreen = true;
        }
        return offScreen;
    }
}