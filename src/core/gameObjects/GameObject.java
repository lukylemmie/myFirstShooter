package core.gameObjects;

import core.gameLogic.Game;
import core.sprites.Sprite;
import core.sprites.SpriteStore;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author Andrew Lem
 */
public abstract class GameObject {
    protected double x;
    protected double y;
    protected Sprite sprite;
    protected double speed;
    protected double dx;
    protected double dy;
    protected Game game;
    protected double bearing;

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

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void draw(Graphics2D g) {
        AffineTransform gameDirection = g.getTransform();
        g.rotate(bearing, x + sprite.getWidth()/2, y + sprite.getHeight()/2);
        sprite.draw(g, (int) x, (int) y);
        g.setTransform(gameDirection);
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

    public void turnToLookAt(int pX, int pY){
        double deltaX = pX - (x + getImageWidth()/2);
        double deltaY = pY - (y + getImageHeight()/2);

        if (Math.abs(deltaX) > 1 && Math.abs(deltaY) > 1) {
            bearing = Math.atan2(deltaX,-deltaY);
        }
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public void calcVelocity(){
        dx = speed * Math.sin(bearing);
        dy = -speed * Math.cos(bearing);
    }

    public double distanceTo(double pX, double pY){
        double deltaX = pX - (x + getImageWidth()/2);
        double deltaY = pY - (y + getImageHeight()/2);

        return Math.sqrt(Math.pow(deltaX, 2)  + Math.pow(deltaY, 2));
    }

    public void stopMoving(){
        dx = 0;
        dy = 0;
    }
}