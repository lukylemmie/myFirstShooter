package core.sprites;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Andrew Lem
 */
public class Sprite {
    private BufferedImage image;

    public Sprite(BufferedImage image) {
        this.image = image;
    }

    public Double getWidth() {
        return (double) image.getWidth(null);
    }

    public Double getHeight() {
        return (double) image.getHeight(null);
    }

    public void draw(Graphics g, Double x, Double y) {
        g.drawImage(image, x.intValue() - image.getWidth(null) / 2, y.intValue() - image.getHeight(null) / 2, null);
    }

    public BufferedImage getImage() {
        return image;
    }
}