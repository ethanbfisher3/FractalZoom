package UIComponents;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Fractals.FractalInfo;
import GameState.RenderingPage;
import Main.GameVariables;
import UI.ColorField;
import UI.Component;

public class FractalColoring extends Component {

	private static final int COLORS_DISPLAYED = 15;

	private ArrayList<ColorField> colorFields;
	private ColorField activeColorField;
	private ColorField activeColorFieldEditor;
	private FractalInfo info;

	private RenderingPage renderingPage;
	private int colorSize = 72;
	private int rows = 2;
	private int columns = 5;

	private int innerBuffer = 10;

	public FractalColoring(RenderingPage renderingPage) {
		super(renderingPage);

		this.renderingPage = renderingPage;

		colorFields = new ArrayList<>();
		activeColorField = null;
		info = GameVariables.getInstance().getFractalInfo();
		Color[] startColors = GameVariables.COLORS_PAGE_START_COLORS;
		backgroundColor = Color.white;

		for (int i = 0; i < startColors.length; i++) {
			final int num = i;
			final ColorField colorField = new ColorField(startColors[i], false);
			colorField.setDisplayProgressBars(false);
			colorField.setColorChangedListener((color) -> {
				info.setColor(num, color);
			});
			colorField.setOnPreviewClickedListener(() -> {
				toggleActiveColorFieldEditor(colorField);
			});
			children.add(colorField);
			colorFields.add(colorField);
		}

		activeColorFieldEditor = new ColorField(Color.green, false);
		activeColorFieldEditor.getPreview().setWidth(150);
		activeColorFieldEditor.getPreview().setHeight(150);
		activeColorFieldEditor.setDisplayProgressBars(true);
		activeColorFieldEditor.setVisible(false);

		children.add(activeColorFieldEditor);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);

		int startY = getY() + height - (buffer + rows * (colorSize + innerBuffer) + colorSize);
		for (int i = 0; i < Math.min(COLORS_DISPLAYED, colorFields.size()); i++) {
			colorFields.get(i).setPreviewBounds(x + buffer + (colorSize + innerBuffer) * (i % columns),
					startY + ((i / columns) * (colorSize + innerBuffer)), colorSize, colorSize);
		}

		activeColorFieldEditor.setBounds(x + buffer, y + 70, 440, 100);
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);

		g.setFont(GameVariables.BIG_FONT);
		g.setColor(GameVariables.TEXT_COLOR);
		g.drawString("Color Scheme", getX() + 20, getY() + 50);
	}

	public void setColors(Color[] colors) {
		for (int i = 0; i < colorFields.size(); i++) {
			colorFields.get(i).setColor(colors[i]);
		}
		if (activeColorField != null)
			activeColorFieldEditor.setColor(activeColorField.getColor());
	}

	private void toggleActiveColorFieldEditor(ColorField colorField) {
		if (activeColorField != colorField) {
			activeColorField = colorField;
			activeColorFieldEditor.setVisible(true);
			activeColorFieldEditor.setColor(colorField.getColor());
			activeColorFieldEditor.setColorChangedListener((color) -> {
				colorField.setColor(color);
				Color[] colors = new Color[colorFields.size()];
				for (int i = 0; i < colorFields.size(); i++) {
					colors[i] = colorFields.get(i).getColor();
				}
				GameVariables.getInstance().getFractalInfo().setColors(colors);
				renderingPage.updateImageColors();
			});
		} else {
			activeColorField = null;
			activeColorFieldEditor.setVisible(false);
		}
	}

	public void setColorSize(int size) {
		colorSize = size;
	}
}
