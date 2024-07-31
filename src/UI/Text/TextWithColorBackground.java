package UI.Text;

import java.awt.Color;
import java.awt.Graphics;

public class TextWithColorBackground extends Text {
	
	private Color backgroundColor;
	private int colorBuffer;

	public TextWithColorBackground(String text, Color background) {
		super(text);

		backgroundColor = background;
		height = 75;
		colorBuffer = 20;
	}

	@Override
	public void draw(Graphics g) {
		width = g.getFontMetrics().stringWidth(getText());
		g.setColor(backgroundColor);
		
		int drawX = getX();
		if (alignment != TextAlign.LEFT) {
			int width = g.getFontMetrics().stringWidth(text.toString());
			
			if (alignment == TextAlign.RIGHT)
			drawX -= width;
			else 
			drawX -= width / 2;
		}
		
		g.fillRect(drawX - colorBuffer - getWidth() / 2, getY() - font.getSize() - 2, getWidth() * 2, height);

		super.draw(g);
	}

	@Override
	public int getWidth() { return super.getWidth() + 2 * colorBuffer; }

}
