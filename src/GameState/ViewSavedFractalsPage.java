package GameState;

import java.awt.Graphics2D;

import Main.ContentPane;
import Main.GameVariables;
import UI.Anchor;
import UI.Buttons;
import UI.UI;
import UIComponents.ViewSavedFractalsComponent;

public class ViewSavedFractalsPage extends GameState {

	private ViewSavedFractalsComponent viewSavedFractals;

	public ViewSavedFractalsPage(GameStateManager gsm) {
		super(gsm, "res/Background1.png");

		viewSavedFractals = new ViewSavedFractalsComponent(this);
		viewSavedFractals.setAnchor(Anchor.TOP_LEFT);
		viewSavedFractals.setBounds(100, 100, 300, 300);
		viewSavedFractals.setFractalRenderSize(200);
		viewSavedFractals.setDistanceBetweenFractals(225);
		viewSavedFractals.setImageIconSize(40);
		viewSavedFractals.setRows(4);
		viewSavedFractals.setColumns(7);
		viewSavedFractals.setDrawTitle(false);
		viewSavedFractals.setDrawBackground(false);
		addUI(viewSavedFractals);

		addUI(Buttons.getBackButton(() -> gsm.setState(GameStateManager.SELECT_FRACTAL_PAGE)));
	}

	@Override
	public void init() {
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(background, 0, 0, ContentPane.WIDTH, ContentPane.WIDTH, null);

		super.draw(g);

		g.setFont(GameVariables.BIG_FONT);
		g.setColor(GameVariables.TEXT_COLOR);

		UI.drawCenteredText(g, "Saved Fractals", 75);

		g.setFont(GameVariables.DEFAULT_FONT);
	}

}
