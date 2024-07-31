package UI.Text;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Main.GameVariables;
import UI.UIElement;

public class Text extends UIElement {

	protected StringBuilder text;
	protected Font font;
	protected Color color;
	protected TextAlign alignment;

	public Text(String text) {
		super();
		if (text == null)
			this.text = null;
		else
			this.text = new StringBuilder(text);

		color = GameVariables.TEXT_COLOR;
		font = new Font("Century Gothic", Font.PLAIN, 16);

		alignment = TextAlign.CENTER;
	}

	public Text() {
		this(null);
	}

	public void draw(Graphics g) {
		width = g.getFontMetrics().stringWidth(getText());
		g.setFont(font);
		g.setColor(color);

		int drawX = getX();
		if (alignment != TextAlign.LEFT) {
			int width = g.getFontMetrics().stringWidth(text.toString());

			if (alignment == TextAlign.RIGHT)
				drawX -= width;
			else
				drawX -= width / 2;
		}

		g.drawString(text.toString(), drawX, getY());
	}

	public enum TextAlign {
		LEFT, CENTER, RIGHT
	}

	/**
	 * 
	 * 
	 * 
	 * Getters and Setters
	 * 
	 * 
	 * 
	 */

	public int getTextWidth(Graphics g) {
		return g.getFontMetrics().stringWidth(text.toString());
	}

	public void deleteCharAt(int index) {
		if (index > -1 && index < text.length())
			text.deleteCharAt(index);
	}

	public int length() {
		return text.length();
	}

	public String getText() {
		return text.toString();
	}

	public void setText(String text) {
		this.text = new StringBuilder(text);
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public TextAlign getAlignment() {
		return alignment;
	}

	public void setAlignment(TextAlign alignment) {
		this.alignment = alignment;
	}

}
