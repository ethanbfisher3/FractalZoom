package UI;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import GameState.GameState;
import Main.Mathf;

public abstract class UIElement {

	protected MouseListener mouseListener;

	protected int x, y;
	protected int width, height;
	protected boolean visible;
	protected boolean shouldDestroy;
	protected Anchor anchor;

	public UIElement() {
		anchor = Anchor.TOP_LEFT;

		x = y = 0;
		width = height = 100;
		visible = true;
	}

	public void update(float dt) {
	}

	public void draw(Graphics g) {
	}

	public void onAdd(GameState currentState) {
	}

	public void onRemove(GameState currentState) {
	}

	public void setBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void setBounds(Rectangle rect) {
		setBounds(rect.x, rect.y, rect.width, rect.height);
	}

	public void setLocation(int x, int y) {
		setBounds(x, y, width, height);
	}

	public void setLocation(Point p) {
		setLocation(p.x, p.y);
	}

	public void addLocation(Point p) {
		setLocation(x + p.x, y + p.y);
	}

	public void addLocation(int x, int y) {
		setLocation(this.x + x, this.y + y);
	}

	public void mouseMoved(Point p) {

		if (mouseListener != null && visible) {
			Rectangle rect = getBounds();
			boolean inRect = Mathf.pointWithinRect(p, rect);
			boolean mouseEntered = mouseListener.mouseEntered();

			if (!mouseEntered && inRect) {
				mouseListener.setMouseEntered(true);
				mouseListener.onMouseEnter();
			}

			else if (mouseEntered && !inRect) {
				mouseListener.setMouseEntered(false);
				mouseListener.onMouseExit();
			}
		}
	}

	public boolean mouseClick() {
		if (mouseListener != null && visible && mouseListener.mouseEntered()
				&& mouseListener.getOnClickListener() != null) {
			mouseListener.onClick();
			return true;
		}
		return false;
	}

	public void mousePress() {
		if (mouseListener != null && visible && mouseListener.mouseEntered())
			mouseListener.onMousePress();
	}

	public void mouseRelease() {
		if (mouseListener != null && visible && mouseListener.mouseEntered())
			mouseListener.onMouseRelease();
	}

	/**
	 * 
	 * 
	 * 
	 * Basic Getters and Setters
	 * 
	 * 
	 * 
	 */

	public boolean isVisible() {
		return visible;
	}

	public int getX() {
		return x + anchor.toPoint().x;
	}

	public int getY() {
		return y + anchor.toPoint().y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Rectangle getBounds() {
		Point p = anchor.toPoint();
		return new Rectangle(x + p.x, y + p.y, width, height);
	}

	public Point getLocation() {
		return new Point(getX(), getY());
	}

	public void setMouseListener(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}

	public MouseListener getMouseListener() {
		return mouseListener;
	}

	public void setAnchor(Anchor anchor) {
		this.anchor = anchor;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setShouldDestroy(boolean shouldDestroy) {
		this.shouldDestroy = shouldDestroy;
	}

	public boolean shouldDestroy() {
		return shouldDestroy;
	}

}
