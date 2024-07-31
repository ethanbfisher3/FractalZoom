package UIComponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import Fractals.SavedFractal;
import GameState.GameState;
import GameState.GameStateManager;
import GameState.RenderingPage;
import Main.ContentPane;
import Main.GameVariables;
import Main.Mathf;
import UI.ImageButton;

public class ViewSavedFractalsComponent extends Component {

	private int imageIconSize = 25;
	private boolean drawTitle = true;

	private ImageButton showButton;
	private ImageButton deleteButton;

	private SavedFractal activeFractal;

	private int fractalRenderSize;
	private int distanceBetweenFractals;
	private int rows;
	private int columns;

	public ViewSavedFractalsComponent(final GameState state) {
		super(state);

		rows = 2;
		columns = 2;

		fractalRenderSize = 200;

		backgroundColor = Color.white;
		showButton = new ImageButton("res/icons/eye.png");
		showButton.setVisible(false);
		deleteButton = new ImageButton("res/icons/trash-can.png");
		deleteButton.setVisible(false);

		showButton.setOnClickListener(() -> {
			if (activeFractal == null)
				return;

			GameVariables.getInstance().getFractalInfo().setValues(activeFractal.getFractalInfo());
			if (state instanceof RenderingPage) {
				RenderingPage page = (RenderingPage) state;
				page.updateColorScheme();
				if (page.canUpdateImage())
					page.updateImage();
			} else {
				state.setState(GameStateManager.RENDERING_PAGE);
			}
		});

		deleteButton.setOnClickListener(() -> {
			if (activeFractal == null)
				return;
			activeFractal.deleteFiles();
			mouseMoved(ContentPane.getInstance().getMousePosition());

			setBounds(x, y, width, height);
		});

		children.add(showButton);
		children.add(deleteButton);
	}

	@Override
	public void mouseMoved(Point p) {
		super.mouseMoved(p);

		boolean fractalFound = false;
		for (int i = 0; i < SavedFractal.savedFractals.size(); i++) {
			Rectangle rect = getImageBounds(i);
			if (Mathf.pointWithinRect(p, rect)) {
				rect.x -= anchor.toPoint().x;
				rect.y -= anchor.toPoint().y;
				activeFractal = SavedFractal.savedFractals.get(i);
				fractalFound = true;
				showButton.setVisible(true);
				deleteButton.setVisible(true);
				showButton.setBounds(rect.x + 10, rect.y + fractalRenderSize - (imageIconSize + 10), imageIconSize,
						imageIconSize);
				deleteButton.setBounds(rect.x + rect.width - (imageIconSize + 10),
						rect.y + fractalRenderSize - (imageIconSize + 10),
						imageIconSize, imageIconSize);
				break;
			}
		}
		if (!fractalFound) {
			activeFractal = null;
			showButton.setVisible(false);
			deleteButton.setVisible(false);
		}
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);

		if (drawTitle) {
			g.setFont(GameVariables.BIG_FONT);
			g.setColor(GameVariables.TEXT_COLOR);

			g.drawString("Saved Fractals", getX() + buffer, getY() + 50);
		}

		for (int i = 0; i < Math.min(SavedFractal.savedFractals.size(), rows *
				columns); i++) {
			SavedFractal sf = SavedFractal.savedFractals.get(i);
			Rectangle r = getImageBounds(i);
			g.drawImage(sf.getImage(), r.x, r.y, r.width, r.height, null);
		}

	}

	private Rectangle getImageBounds(int index) {
		return new Rectangle(getX() + buffer + distanceBetweenFractals * (index % columns),
				getY() + buffer * 2 + GameVariables.BIG_FONT.getSize() - 15
						+ distanceBetweenFractals * (index / columns),
				fractalRenderSize, fractalRenderSize);
	}

	public void setFractalRenderSize(int size) {
		fractalRenderSize = size;
	}

	public int getDistanceBetweenFractals() {
		return distanceBetweenFractals;
	}

	public void setDistanceBetweenFractals(int distanceBetweenFractals) {
		this.distanceBetweenFractals = distanceBetweenFractals;
		setBounds(x, y, width, height);
	}

	public void setRows(int rows) {
		this.rows = rows;
		setBounds(x, y, width, height);
	}

	public void setColumns(int columns) {
		this.columns = columns;
		setBounds(x, y, width, height);
	}

	public void setImageIconSize(int size) {
		imageIconSize = size;
	}

	public void setDrawTitle(boolean drawTitle) {
		this.drawTitle = drawTitle;
	}

}
