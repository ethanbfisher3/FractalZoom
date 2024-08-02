package UIComponents;

import java.awt.Color;
import java.awt.Graphics;

import Fractals.FractalInfo;
import GameState.GameState;
import GameState.RenderingPage;
import Main.GameVariables;
import UI.Button;
import UI.Component;
import UI.ImageButton;
import UI.UI;

public class NavigationComponent extends Component {

    private static final Color ONE = new Color(0x555555), TWO = new Color(0x333333), THREE = new Color(0x111111);

    private static final double moveDistance = 25;

    private ImageButton[] arrowButtons;
    private ImageButton[] iterationButtons;
    private Button updateButton;

    private int iconSize;

    public NavigationComponent(GameState state) {
        super(state);

        String[] paths = { "res/icons/back_arrow.png", "res/icons/up_arrow.png", "res/icons/right_arrow.png",
                "res/icons/down_arrow.png" };

        arrowButtons = new ImageButton[4];
        for (int i = 0; i < arrowButtons.length; i++) {
            final int num = i;
            ImageButton arrow = new ImageButton(paths[i]);
            arrow.setColors(ONE, TWO, THREE);
            arrow.setOnClickListener(() -> {
                move(num);
            });
            arrowButtons[i] = arrow;
            children.add(arrow);
        }

        paths = new String[] { "res/icons/minus.png", "res/icons/plus.png" };

        iterationButtons = new ImageButton[2];
        for (int i = 0; i < 2; i++) {
            final int num = i;
            ImageButton button = new ImageButton(paths[i]);
            button.setMousePressListener(() -> {
                changeIterations(num);
            });
            button.setColors(ONE, TWO, THREE);
            children.add(button);
            iterationButtons[i] = button;
        }

        updateButton = new Button("Update Image");
        updateButton.setOnClickListener(() -> {
            RenderingPage page = (RenderingPage) state;
            if (page.canUpdateImage()) {
                page.updateImage();
            }
        });
        updateButton.setFont(GameVariables.KINDA_BIG_FONT);
        children.add(updateButton);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        // if (Input.getKeyDown(KeyEvent.VK_LEFT))
        // move(0);
        // if (Input.getKeyDown(KeyEvent.VK_UP))
        // move(1);
        // if (Input.getKeyDown(KeyEvent.VK_RIGHT))
        // move(2);
        // if (Input.getKeyDown(KeyEvent.VK_DOWN))
        // move(3);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        iconSize = (width - buffer * 2 - 20) / 3;
        int startY = y + buffer + 50 + 30;
        arrowButtons[0].setBounds(x + buffer, startY + 10 + iconSize, iconSize, iconSize);
        arrowButtons[1].setBounds(x + buffer + iconSize + 10, startY, iconSize, iconSize);
        arrowButtons[2].setBounds(x + buffer + 20 + iconSize * 2, startY + 10 + iconSize, iconSize,
                iconSize);
        arrowButtons[3].setBounds(x + buffer + iconSize + 10, startY + 20 + iconSize * 2, iconSize,
                iconSize);

        iterationButtons[0].setBounds(x + buffer, startY + iconSize * 3 + 30 + 45, iconSize, iconSize);
        iterationButtons[1].setBounds(x + width - buffer - iconSize, startY + iconSize * 3 + 30 + 45, iconSize,
                iconSize);

        updateButton.setBounds(x + buffer, y + height - buffer - iconSize, width - buffer * 2, iconSize);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        g.setColor(GameVariables.TEXT_COLOR);
        g.setFont(GameVariables.BIG_FONT);
        g.drawString("Navigation", getX() + buffer, getY() + buffer + 30);

        g.setFont(GameVariables.DEFAULT_FONT);
        g.drawString("Iterations", getX() + buffer, getY() + buffer + 50 + iconSize * 3 + 30 + 60);
        g.drawString("Movement", getX() + buffer, getY() + buffer + 50 + 20);
        UI.drawCenteredText(g, GameVariables.getInstance().getFractalInfo().getMaxIterations() + "", x + width / 2,
                iterationButtons[0].getY() + 45);
    }

    private void changeIterations(int index) {
        FractalInfo info = GameVariables.getInstance().getFractalInfo();
        info.setMaxIterations(info.getMaxIterations() + (index == 0 ? -10 : 10));
    }

    private void move(int index) {
        FractalInfo info = GameVariables.getInstance().getFractalInfo();
        double zoom = Math.max(info.getZoomPower(), 1);
        switch (index) {
            case 0:
                info.addOffsetX(-moveDistance / zoom);
                break;
            case 1:
                info.addOffsetY(moveDistance / zoom);
                break;
            case 2:
                info.addOffsetX(moveDistance / zoom);
                break;
            case 3:
                info.addOffsetY(-moveDistance / zoom);
                break;
        }
        RenderingPage page = (RenderingPage) state;
        if (page.canUpdateImage()) {
            page.updateImage();
        }
    }

}
