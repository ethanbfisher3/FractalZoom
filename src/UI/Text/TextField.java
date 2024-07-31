package UI.Text;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Main.GameVariables;
import UI.Button;
import UI.MouseListener;

public class TextField extends Button {

	private StringBuilder text;
	private Document document;
	private boolean selected;
	private float cursorTimer;
	private Color borderColor;
	private Color backgroundColor;

	public TextField(String text) {
		super();
		this.text = new StringBuilder(text);
		document = null;
		color = GameVariables.TEXT_COLOR;
		borderColor = Color.black;
		backgroundColor = new Color(0x444444);

		mouseListener = new MouseListener();

		cursorTimer = 0;
	}

	@Override
	public void update(float dt) {
		if (!selected)
			return;

		cursorTimer += dt * 3;
	}

	@Override
	public void draw(Graphics g) {
		g.setFont(font);
		g.setColor(borderColor);
		g.fillRect(getX(), getY(), width, height);
		g.setColor(backgroundColor);
		g.fillRect(getX() + 1, getY() + 1, width - 2, height - 2);

		g.setColor(color);
		g.drawString(text.toString(), getX() + 5, getY() + (height + g.getFont().getSize()) / 2 - 5);

		if (selected && ((int) cursorTimer) % 2 == 0) {
			int width = g.getFontMetrics().stringWidth(text.toString());
			g.drawLine(getX() + width + 6, getY() + 7, getX() + width + 6, getY() + height - 7);
		}
	}

	@Override
	public boolean mouseClick() {
		selected = mouseListener.mouseEntered();
		return selected;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public void append(char c) {
		text.append(c);
	}

	public void keyPressed(int code) {
		if (selected) {

			if (code == KeyEvent.VK_BACK_SPACE && text.length() >= 1) {
				text.deleteCharAt(text.length() - 1);
				document.charRemoved();
			} else if (document != null)
				document.insertChar((char) code);
		}
	}

	public void setText(String text) {
		this.text = new StringBuilder(text);
	}

	public void setColor(Color c) {
		color = c;
	}

	public String getText() {
		return text.toString();
	}

}
