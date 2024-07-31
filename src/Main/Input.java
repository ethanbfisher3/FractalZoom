package Main;

import java.awt.event.KeyEvent;

public class Input {

	private static boolean[] keysHeld = new boolean[100];
	private static boolean[] keysPressed = new boolean[100];

	private static boolean mousePressed;

	static void update() {
		for (int i = 0; i < 100; i++)
			keysPressed[i] = false;
	}

	static void keyPressed(int code) {
		if (code == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		if (code < 100) {
			if (!keysHeld[code])
				keysPressed[code] = true;
			keysHeld[code] = true;
		}
	}

	static void keyReleased(int code) {
		if (code < 100) {
			keysHeld[code] = false;
			keysPressed[code] = false;
		}
	}

	public static boolean getKey(int code) {
		return keysHeld[code];
	}

	public static boolean getKeyDown(int code) {
		return keysPressed[code];
	}

	public static void mouseDown() {
		mousePressed = true;
	}

	public static void mouseUp() {
		mousePressed = false;
	}

	public static boolean mouseIsDown() {
		return mousePressed;
	}

}
