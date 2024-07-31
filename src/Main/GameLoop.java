package Main;

import GameState.GameStateManager;

public class GameLoop implements Runnable {

	// game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;

	private GameStateManager gsm;
	private ContentPane contentPane;

	public GameLoop(GameStateManager gsm, ContentPane contentPane) {
		this.gsm = gsm;
		this.contentPane = contentPane;
		
		thread = new Thread(this);
		thread.setName("Game Loop Thread");
	}

	public void startThread() { thread.start(); }

	@Override
	public void run() {
		
		running = true;

		Time gameStateTime = new Time();
		Time waitTime = new Time();
		long wait;

		// game loop
		while (running) {
			
			gsm.update(gameStateTime.getElapsed());
			gameStateTime.reset();
			Input.update();
			
			contentPane.draw();

			wait = targetTime - (int) (waitTime.getElapsed() * 1000);
			if (wait > 0)
				try {
					Thread.sleep(wait);
				} catch (Exception e) {
					e.printStackTrace();
				}
			waitTime.reset();
		}
	}
}
