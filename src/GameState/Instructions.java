package GameState;

import java.awt.Graphics2D;

import UI.Buttons;

public class Instructions extends GameState {
	
	private final String[] strings = {
		"Click to change offset",
		"Scroll to zoom",
		"Up and down arrows to change iteration count",
		"H to hide stats"
	};
	
	private int startX;
	private int startY;
	private int yDist;

	public Instructions(GameStateManager gsm) {
		super(gsm, null);
	}

	@Override
	public void init() {
		
		addUI(Buttons.getBackButton(gsm::back));
		
		startX = 200;
		startY = 200;
		yDist = 80;
	}
	
	@Override
	public void draw(Graphics2D g) {
		
		for (int i = 0; i < strings.length; i++) 
			g.drawString(strings[i], startX, startY + yDist * i);
		
		super.draw(g);
	}

}
