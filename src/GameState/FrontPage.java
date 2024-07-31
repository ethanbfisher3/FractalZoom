package GameState;

import java.awt.Font;
import java.awt.Graphics2D;

import Main.ContentPane;
import Main.GameVariables;
import UI.Anchor;
import UI.Button;
import UI.Text.Text;
import UI.Text.Text.TextAlign;

public class FrontPage extends GameState {

	public FrontPage(GameStateManager gsm) {
		super(gsm, "res/Background2.png");

		Button playButton = new Button("Play");
		playButton.setAnchor(Anchor.CENTER);
		playButton.setBounds(-100, 50, 200, 100);
		playButton.setOnClickListener(() -> gsm.setState(GameStateManager.SELECT_FRACTAL_PAGE));
		playButton.setFont(GameVariables.BIG_FONT);
		addUI(playButton);

		Button exitButton = new Button("Exit");
		exitButton.setAnchor(Anchor.CENTER);
		exitButton.setBounds(-50, 250, 100, 50);
		exitButton.setOnClickListener(() -> System.exit(0));
		exitButton.setFont(GameVariables.DEFAULT_FONT);
		addUI(exitButton);

		Text text = new Text("Fractal Zoomer");
		text.setAnchor(Anchor.TOP_CENTER);
		text.setAlignment(TextAlign.CENTER);
		text.setY(400);
		text.setFont(new Font("Century Gothic", Font.PLAIN, 120));
		addUI(text);
	}

	@Override
	public void init() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(background, 0, -100, ContentPane.WIDTH, ContentPane.WIDTH, null);

		super.draw(g);
	}

}
