package UI;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.SwingUtilities;

import Main.ContentPane;
import Main.Mathf;

public class ProgressBar extends UIElement {

	private float progress;
	private int fillWidth;
	private Color fillColor;
	private Color backgroundColor;
	private Color handleColor;

	private boolean mouseMovingHandle;
	private int handleX;
	private int handleSize;

	public ProgressBar() {
		super();
		height = 20;

		progress = 0;
		fillWidth = 5;
		fillColor = Color.red;
		backgroundColor = Color.black;
		handleColor = Color.red;

		mouseMovingHandle = false;
		handleSize = 30;

		mouseListener = new MouseListener() {
			@Override
			public void onMousePress() {
				mouseMovingHandle = true;
			}

			@Override
			public void onMouseRelease() {
				mouseMovingHandle = false;
			}
		};
	}

	@Override
	public void update(float dt) {
		if (mouseMovingHandle) {
			Point p = MouseInfo.getPointerInfo().getLocation();
			SwingUtilities.convertPointFromScreen(p, ContentPane.getInstance());
			Point p2 = ContentPane.getInstance().screenToImagePoint(p);

			handleX = p2.x - getX() - handleSize / 2;
			progress = Mathf.clamp(0f, 1f, (float) (handleX + handleSize / 2) / width);
			handleX = (int) Mathf.clamp(0f, width - handleSize, handleX);
		}
	}

	@Override
	public void draw(Graphics g) {
		GradientPaint gp = new GradientPaint(getX(), getY(), Color.black, getX() + width, getY() + height,
				fillColor);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(gp);
		g2d.fillRect(getX(), getY(), width, height);

		int drawWidth = (int) ((width - fillWidth * 2) * progress);
		if (drawWidth < 5)
			drawWidth = 5;

		// g2d.setColor(fillColor);
		// g2d.fillRect(getX() + fillWidth, getY() + 3, drawWidth, height - 6);

		g.setColor(Mathf.lerpColor(Color.black, fillColor, progress));
		g.fillOval(getX() + handleX, getY() - 5, handleSize, handleSize);
	}

	public float getProgress() {
		return progress;
	}

	public int getFillWidth() {
		return fillWidth;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setProgress(float progress) {
		if (progress <= 1 && progress >= 0) {
			this.progress = progress;
			handleX = (int) (progress * width - handleSize);
			handleX = (int) Mathf.clamp(0f, width - handleSize, handleX);
		}
	}

	public void setFillWidth(int fillWidth) {
		this.fillWidth = fillWidth;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getHandleColor() {
		return handleColor;
	}

	public void setHandleColor(Color handleColor) {
		this.handleColor = handleColor;
	}

}
