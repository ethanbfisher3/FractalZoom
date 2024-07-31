package GameState;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Main.GameVariables;
import Main.Input;

public class GameStateManager {

	private ArrayList<GameState> gameStates;
	private ArrayList<Integer> prevStates;
	private boolean initComplete;
	private GameState currentState;

	public static final int FRONT_PAGE = 0;
	public static final int SELECT_FRACTAL_PAGE = 1;
	public static final int RENDERING_PAGE = 2;
	public static final int VIEW_SAVED_FRACTALS = 3;

	public GameStateManager() {

		gameStates = new ArrayList<>();
		gameStates.add(FRONT_PAGE, new FrontPage(this));
		gameStates.add(SELECT_FRACTAL_PAGE, new SelectFractalPage(this));
		gameStates.add(RENDERING_PAGE, new RenderingPage(this));
		gameStates.add(VIEW_SAVED_FRACTALS, new ViewSavedFractalsPage(this));

		prevStates = new ArrayList<>();
	}

	public void setState(int state) {
		if (currentState != null) {
			currentState.onStateChanged();
		}
		currentState = gameStates.get(state);
		GameVariables.getInstance().setCurrentGameState(currentState);

		initComplete = false;
		prevStates.add(state);
		currentState.init();
		initComplete = true;
	}

	public void back() {
		if (prevStates.size() < 2) {
			if (prevStates.size() == 1)
				setState(prevStates.get(0));
			else
				setState(FRONT_PAGE);
			prevStates.clear();
			return;
		}
		int state = prevStates.remove(prevStates.size() - 2);
		setState(state);
		prevStates.remove(prevStates.size() - 2);
	}

	public void update(float dt) {
		if (Input.getKey(KeyEvent.VK_ESCAPE))
			back();
		if (initComplete) {
			currentState.update(dt);
		}
	}

	public void draw(Graphics2D g) {
		if (initComplete)
			currentState.draw(g);
	}

	public void mouseClick(Point point) {
		if (initComplete)
			currentState.mouseClick(point);
	}

	public void mouseWheelMoved(int rotation) {
		if (initComplete)
			currentState.mouseWheelMoved(rotation);
	}

	public void mouseMoved(Point p) {
		if (initComplete)
			currentState.mouseMoved(p);
	}

	public void mousePressed() {
		if (initComplete)
			currentState.mousePress();
	}

	public void mouseReleased() {
		if (initComplete)
			currentState.mouseRelease();
	}

	public void keyPressed(int code) {
		currentState.keyPressed(code);
	}

}
