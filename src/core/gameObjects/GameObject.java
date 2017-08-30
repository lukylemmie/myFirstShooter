package core.gameObjects;

import core.gameLogic.Game;
import core.sprites.Sprite;
import core.sprites.SpriteStore;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

/**
 * @author Andrew Lem
 */
public abstract class GameObject {
    protected Double x;
    protected Double y;
    protected Sprite sprite;
    protected double speed;
    protected double dx;
    protected double dy;
    protected Game game;
    protected double bearing;

    private Rectangle myHitBox = new Rectangle();
    private Rectangle otherHitBox = new Rectangle();

    public GameObject(Game game, String ref, Double x, Double y) {
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

    public Double getX() {
        return (Double) x;
    }

    public Double getY() {
        return (Double) y;
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
        Double maxLength = Math.max(getImageWidth(), getImageHeight()) + 3;
        g.setColor(Color.BLUE);
        g.fillRect((int) (x - maxLength/2), (int) (y - maxLength/2),
                maxLength.intValue(), maxLength.intValue());

        AffineTransform gameDirection = g.getTransform();
        g.rotate(bearing, x, y);
        sprite.draw(g, x, y);
        g.setTransform(gameDirection);
    }

    public boolean collidesWith(GameObject other) {
        boolean collision = false;

        Double maxLength = Math.max(getImageWidth(), getImageHeight()) + 3;
        Double otherMaxLength = Math.max(other.getImageWidth(), other.getImageHeight()) + 3;
        myHitBox.setBounds((int) (x - maxLength/2), (int) (y - maxLength/2),
                maxLength.intValue(), maxLength.intValue());
        otherHitBox.setBounds((int) (other.x - otherMaxLength/2),
                (int) (other.y - otherMaxLength/2),
                otherMaxLength.intValue(), otherMaxLength.intValue());

        if (myHitBox.intersects(otherHitBox)){
            // Calculate the collision overlay
            Rectangle bounds = getCollision(myHitBox, otherHitBox);
            if (!bounds.isEmpty()) {
                // Check all the pixels in the collision overlay to determine
                // if there are any non-alpha pixel collisions...
                for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
                    for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
                        if (collision(x, y, other)) {
                            collision = true;
                            break;
                        }
                    }
                }
            }
        }

        return collision;
    }

    protected boolean collision(int x, int y, GameObject other) {
        boolean collision = false;

        int myPixel = getImage().getRGB(x - myHitBox.x, y - myHitBox.y);
        int otherPixel = other.getImage().getRGB(x - otherHitBox.x, y - otherHitBox.y);
        // 255 is completely transparent, you might consider using something
        // a little less absolute, like 225, to give you a sligtly
        // higher hit right, for example...
        if (((myPixel >> 24) & 0xFF) > 0 && ((otherPixel >> 24) & 0xFF) > 0) {
            collision = true;
            System.out.println("Images collide");
        }
        return collision;
    }

    protected Rectangle getCollision(Rectangle rect1, Rectangle rect2) {
        Area a1 = new Area(rect1);
        Area a2 = new Area(rect2);
        a1.intersect(a2);
        return a1.getBounds();
    }

    public Double getImageWidth(){
        return sprite.getWidth();
    }

    public Double getImageHeight(){
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

    public void turnToLookAt(double pX, double pY){
        double deltaX = pX - x;
        double deltaY = pY - y;

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
        double deltaX = pX - x;
        double deltaY = pY - y;

        return Math.sqrt(Math.pow(deltaX, 2)  + Math.pow(deltaY, 2));
    }

    public void stopMoving(){
        dx = 0;
        dy = 0;
    }

    public BufferedImage getImage(){
        return sprite.getImage();
    }
}