package UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageButton extends Button {

	private BufferedImage image;

	public ImageButton(BufferedImage image, String text) {
		super();
		this.image = image;
		if (text != null)
			this.text = new StringBuilder(text);
		width = (int) (image.getWidth());
		height = (int) (image.getHeight());
	}

	public ImageButton(String imagePath) {
		try {
			image = ImageIO.read(new File(imagePath));
			width = (int) (image.getWidth());
			height = (int) (image.getHeight());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ImageButton(BufferedImage image) {
		this(image, null);
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		g.drawImage(image, getX(), getY(), width, height, null);
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}

}
