package UIComponents;

import java.awt.Graphics;

import GameState.GameState;
import Main.GameVariables;
import UI.Component;
import UI.UI;

public class InstructionsComponent extends Component {

    public InstructionsComponent(final GameState state) {
        super(state);
    }

    @Override
    public void draw(Graphics g) {
        g.setFont(GameVariables.VERY_VERY_BIG_FONT);
        g.setColor(GameVariables.TEXT_COLOR);
        UI.drawCenteredText(g, "Instructions", 100);

        g.setFont(GameVariables.DEFAULT_FONT);
        g.setColor(GameVariables.TEXT_COLOR);
        g.drawString("Use the arrow keys to move the fractal.", getX() + 20, 100);
        g.drawString("Use the mouse wheel to zoom in and out.", getX() + 20, 140);
        g.drawString("Press 'r' to reset the fractal.", getX() + 20, 180);
        g.drawString("Press 'c' to change the color scheme.", getX() + 20, 220);
        g.drawString("Press 'v' to view saved fractals.", getX() + 20, 260);
        g.drawString("Press 'i' to view instructions.", getX() + 20, 300);
        g.drawString("Press 'esc' to quit.", getX() + 20, 340);
    }

}
