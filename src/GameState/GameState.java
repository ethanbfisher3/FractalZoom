package GameState;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import Main.Game;
import UI.UIElement;
import UI.Text.TextField;

public abstract class GameState {

	protected GameStateManager gsm;
	protected BufferedImage background;

	private ArrayList<UIElement> uiElements;

	public GameState(GameStateManager gsm, String backgroundImagePath) {
		super();
		this.gsm = gsm;
		uiElements = new ArrayList<>();

		try {
			background = ImageIO.read(new File(backgroundImagePath));
		} catch (Exception e) {
			System.err.println("Image background not found in: " + getClass().toString());
		}
	}

	public abstract void init();

	public void onStateChanged() {
	}

	public void mouseWheelMoved(int rotation) {
	}

	public void keyPressed(int code) {
		ArrayList<UIElement> uiCopy = new ArrayList<>(uiElements);
		for (Iterator<UIElement> elements = uiCopy.iterator(); elements.hasNext();) {
			UIElement element = elements.next();
			if (element instanceof TextField)
				((TextField) element).keyPressed(code);
		}
	}

	public void update(float dt) {
		ArrayList<UIElement> uiCopy = new ArrayList<>(uiElements);
		for (Iterator<UIElement> elements = uiCopy.iterator(); elements.hasNext();) {
			UIElement element = elements.next();
			if (element.shouldDestroy()) {
				element.onRemove(this);
				elements.remove();
			} else if (element.isVisible())
				element.update(dt);
		}
		uiElements = uiCopy;
	}

	public void draw(Graphics2D g) {
		ArrayList<UIElement> uiCopy = new ArrayList<>(uiElements);
		for (UIElement element : uiCopy)
			if (element.isVisible())
				element.draw(g);
	}

	// mouse functions
	public void mouseMoved(Point p) {
		boolean mouseEntered = false;
		ArrayList<UIElement> uiCopy = new ArrayList<>(uiElements);
		for (UIElement element : uiCopy)
			if (element.isVisible()) {
				element.mouseMoved(p);
				if (element.getMouseListener() != null && element.getMouseListener().mouseEntered())
					mouseEntered = true;
			}

		if (!mouseEntered)
			Game.setCursor(Cursor.getDefaultCursor());
	}

	public boolean mouseClick(Point point) {
		boolean clicked = false;
		ArrayList<UIElement> uiCopy = new ArrayList<>(uiElements);
		for (UIElement element : uiCopy)
			if (element.isVisible() && element.mouseClick())
				clicked = true;
		return clicked;
	}

	public void mousePress() {
		ArrayList<UIElement> uiCopy = new ArrayList<>(uiElements);
		for (UIElement element : uiCopy)
			if (element.isVisible())
				element.mousePress();
	}

	public void mouseRelease() {
		ArrayList<UIElement> uiCopy = new ArrayList<>(uiElements);
		for (UIElement element : uiCopy)
			if (element.isVisible())
				element.mouseRelease();
	}

	public void addUI(UIElement element) {
		if (!uiElements.contains(element)) {
			uiElements.add(element);
			element.onAdd(this);
		}
	}

	public void addUI(ArrayList<? extends UIElement> elements) {
		for (UIElement element : elements)
			addUI(element);
	}

	public void addUI(UIElement... elements) {
		for (UIElement element : elements)
			addUI(element);
	}

	public void remove(UIElement element) {
		ArrayList<UIElement> uiCopy = new ArrayList<>(uiElements);
		uiCopy.remove(element);
		element.onRemove(this);
		uiElements = uiCopy;
	}

	public boolean contains(UIElement element) {
		return uiElements.contains(element);
	}

	public void remove(UIElement... elements) {
		for (UIElement element : elements)
			remove(element);
	}

	public void removeAllUIElements() {
		for (UIElement element : uiElements)
			element.onRemove(this);
		uiElements = new ArrayList<>();
	}

	public void setState(int state) {
		gsm.setState(state);
	}
}
