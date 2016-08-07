package core.sprites;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Andrew Lem
 */
public class SpriteStore {
    private static SpriteStore single = new SpriteStore();

    private HashMap sprites = new HashMap();

    public static SpriteStore get() {
        return single;
    }

    public Sprite getSprite(String ref) {
        // if sprite exists return the sprite
        if (sprites.get(ref) != null) {
            return (Sprite) sprites.get(ref);
        }

        BufferedImage sourceImage = null;

        try {
            // url for when using web start or web location
//			URL url = this.getClass().getClassLoader().getResource(ref);

            // file for local game
            File file = new File(ref);

            if (file == null) {
                fail("Can't find ref: " + ref);
            }

            sourceImage = ImageIO.read(file);
        } catch (IOException e) {
            fail("Failed to load: " + ref);
        }

        // create an accelerated image of the right size to store our sprite in
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image image = gc.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(), Transparency.BITMASK);

        // draw our source image into the accelerated image
        image.getGraphics().drawImage(sourceImage, 0, 0, null);

        // create a sprite, add it the cache then return it
        Sprite sprite = new Sprite(image);
        sprites.put(ref, sprite);

        return sprite;
    }

    private void fail(String message) {
        System.err.println(message);
        System.exit(0);
    }
}