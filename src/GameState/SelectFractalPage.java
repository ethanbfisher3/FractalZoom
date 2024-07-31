package GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import Fractals.*;
import Main.ContentPane;
import Main.GameVariables;
import UI.*;
import UIComponents.SetFractalInfo;

public class SelectFractalPage extends GameState {

	private SetFractalInfo setFractalInfo;
	private Button[] fractalButtons;

	public SelectFractalPage(GameStateManager gsm) {
		super(gsm, "res/Background3.png");

		setFractalInfo = new SetFractalInfo(this);
		setFractalInfo.setBackgroundColor(new Color(0x444444));
		setFractalInfo.setAnchor(Anchor.CENTER_RIGHT);
		setFractalInfo.setBuffer(20);
		setFractalInfo.setBounds(-440, -332, 390, 665);
		addUI(setFractalInfo);

		final FractalInfo info = GameVariables.getInstance().getFractalInfo();
		FractalInfo miniInfo = new FractalInfo();
		miniInfo.setImageSize(200);
		miniInfo.setZoomPower(.25);

		fractalButtons = new Button[Fractal.NUM_FRACTALS];
		for (int i = 0; i < fractalButtons.length; i++) {

			final int num = i;

			Button button = new ImageButton(Fractal.getFractal(i).toImage(miniInfo), Fractal.FRACTAL_NAMES[i]);
			button.setBounds(200, 100 + (400 * i), 300, 300);
			button.setOnClickListener(() -> {
				setFractalInfo.checkInputs();
				info.setFractalType(num);
				gsm.setState(GameStateManager.RENDERING_PAGE);
			});
			addUI(button);

			fractalButtons[i] = button;
		}

		Button viewImages = new Button("View Saved Fractals");
		viewImages.setFont(GameVariables.KINDA_BIG_FONT);
		viewImages.setBounds(150, ContentPane.HEIGHT - 200, 350, 75);
		viewImages.setOnClickListener(() -> gsm.setState(GameStateManager.VIEW_SAVED_FRACTALS));
		addUI(viewImages);

		addUI(Buttons.getBackButton(gsm::back));
	}

	@Override
	public void init() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		g.drawImage(background, 0, -(ContentPane.WIDTH - ContentPane.HEIGHT), ContentPane.WIDTH, ContentPane.WIDTH,
				null);
		g.setColor(new Color(255, 255, 255, 150));
		g.fillRect(0, 0, ContentPane.WIDTH, ContentPane.HEIGHT);

		super.draw(g);
	}
}
