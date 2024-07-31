package UIComponents;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import GameState.GameState;
import UI.Anchor;
import UI.UIElement;

public class Component extends UIElement {

    protected ArrayList<UIElement> children;
    protected Color backgroundColor;
    protected GameState state;
    protected int buffer;
    protected boolean drawBackground;

    public Component(final GameState state) {
        super();

        this.state = state;
        children = new ArrayList<>();
        drawBackground = true;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        if (drawBackground) {
            g.setColor(backgroundColor);
            g.fillRect(getX(), getY(), width, height);
        }
    }

    @Override
    public void setAnchor(Anchor anchor) {
        super.setAnchor(anchor);
        children.forEach((child) -> child.setAnchor(anchor));
    }

    @Override
    public void onAdd(GameState currentState) {
        super.onAdd(currentState);
        children.forEach((child) -> currentState.addUI(child));
    }

    @Override
    public void onRemove(GameState currentState) {
        super.onRemove(currentState);
        children.forEach((child) -> currentState.remove(child));
    }

    public void setBuffer(int buffer) {
        this.buffer = buffer;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setDrawBackground(boolean drawBackground) {
        this.drawBackground = drawBackground;
    }
}
