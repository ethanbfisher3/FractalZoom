package Main;

import java.awt.Cursor;

import javax.swing.JFrame;

import Fractals.SavedFractal;
import GameState.GameStateManager;

public class Game {

	private static Game instance;

	private JFrame window;

	public Game() {
		instance = this;

		new GameVariables();
		SavedFractal.GetAllSavedFractals();

		GameStateManager gsm = new GameStateManager();
		ContentPane contentPane = new ContentPane(gsm);
		GameLoop gameLoop = new GameLoop(gsm, contentPane);

		window = new JFrame("Game");
		window.setContentPane(contentPane);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setSize(GameVariables.START_SCREEN_SIZE);
		window.setVisible(true);

		gsm.setState(GameVariables.START_SCREEN);
		gameLoop.startThread();
	}

	public static void main(String[] args) {
		new Game();
	}

	public static void setCursor(Cursor cursor) {
		instance.window.setCursor(cursor);
	}
}
