package UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class UIImage extends UIElement {

    private BufferedImage image;

    public UIImage(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UIImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, getX(), getY(), width, height, null);
    }

}
