package UI;

import java.awt.Color;
import java.awt.Graphics;

import GameState.GameState;
import Main.GameVariables;

public class ColorField extends UIElement {

	public static final Color[] defaultHandleColors = { new Color(150, 0, 0), new Color(0, 150, 0),
			new Color(0, 0, 150) };

	public interface ColorChangedListener {
		void colorChanged(Color newColor);
	}

	private final Color[] colors = { Color.red, Color.green, Color.blue };
	private final String[] letters = { "R", "G", "B" };

	private ColorChangedListener ccl;
	private ProgressBar[] progressBars;
	private Button preview;
	private Button deleteButton;
	private boolean displayProgressBars;
	private int yDist;

	public ColorField(Color startColor, boolean hasDeleteButton) {
		super();

		progressBars = new ProgressBar[3];
		for (int i = 0; i < 3; i++) {
			ProgressBar bar = new ProgressBar();
			bar.setHeight(10);
			bar.setFillColor(colors[i]);
			bar.setHandleColor(defaultHandleColors[i]);
			progressBars[i] = bar;
		}

		if (hasDeleteButton) {
			deleteButton = new Button("Delete");
			deleteButton.setOnClickListener(() -> {
				shouldDestroy = true;
				deleteButton.shouldDestroy = true;
				for (UIElement field : progressBars)
					field.shouldDestroy = true;
			});
		}

		preview = new Button();
		preview.setColors(startColor, startColor, startColor);

		yDist = 55;

		setBounds(0, 0, 200, 200);
	}

	@Override
	public void update(float dt) {
		if (!displayProgressBars)
			return;

		int[] colors = new int[3];
		for (int i = 0; i < 3; i++)
			colors[i] = (int) (255 * progressBars[i].getProgress());

		Color newColor = new Color(colors[0], colors[1], colors[2]);
		preview.setColors(newColor, newColor, newColor);
		if (ccl != null)
			ccl.colorChanged(preview.getColor());
	}

	@Override
	public void draw(Graphics g) {
		g.setFont(GameVariables.DEFAULT_FONT);

		if (displayProgressBars)
			for (int i = 0; i < 3; i++) {
				g.setColor(colors[i]);
				g.drawString(letters[i], getX(), getY() + 29 + yDist * i);
			}

		super.draw(g);
	}

	@Override
	public void onAdd(GameState state) {
		state.addUI(progressBars);
		if (deleteButton != null)
			state.addUI(deleteButton);
		state.addUI(preview);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		for (int i = 0; i < 3; i++)
			progressBars[i].setVisible(visible);
		if (deleteButton != null)
			deleteButton.setVisible(visible);
		preview.setVisible(visible);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);

		for (int i = 0; i < progressBars.length; i++) {
			progressBars[i].setBounds(x + 20, y + 10 + yDist * i, width - preview.width - 30, 20);
		}

		if (deleteButton != null)
			deleteButton.setBounds(x + 75, y, 100, 50);

		preview.setBounds(x + width - preview.width, y, preview.width, preview.height);
	}

	@Override
	public void setAnchor(Anchor anchor) {
		super.setAnchor(anchor);

		for (int i = 0; i < progressBars.length; i++)
			progressBars[i].setAnchor(anchor);

		if (deleteButton != null)
			deleteButton.setAnchor(anchor);

		preview.setAnchor(anchor);
	}

	public void setPreviewBounds(int x, int y, int width, int height) {
		preview.setBounds(x, y, width, height);
	}

	public void setOnPreviewClickedListener(OnClickListener ocl) {
		preview.setOnClickListener(ocl);
	}

	public Color getColor() {
		return preview.getColor();
	}

	public void setColorChangedListener(ColorChangedListener ccl) {
		this.ccl = ccl;
	}

	public void setColor(Color color) {
		preview.setColor(color);
		preview.setColors(color, color, color);
		progressBars[0].setProgress(color.getRed() / 255f);
		progressBars[1].setProgress(color.getGreen() / 255f);
		progressBars[2].setProgress(color.getBlue() / 255f);
	}

	public boolean shouldDisplayTextFields() {
		return displayProgressBars;
	}

	public void setDisplayProgressBars(boolean displayTextFields) {
		this.displayProgressBars = displayTextFields;
		for (UIElement field : progressBars)
			field.setVisible(displayTextFields);
	}

	public Button getPreview() {
		return preview;
	}
}
