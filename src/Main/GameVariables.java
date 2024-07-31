package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import Fractals.Fractal;
import Fractals.FractalInfo;
import GameState.*;

public class GameVariables {

    private static GameVariables instance;

    // fractal renderer stuff
    public static final int MAX_ZOOM_POWER = 45;
    public static final int MAX_ITERATIONS = 10000;

    public static final int START_SCREEN_WIDTH = 1920 * 3 / 4;
    public static final int START_SCREEN_HEIGHT = 1080 * 3 / 4;
    public static final Dimension START_SCREEN_SIZE = new Dimension(START_SCREEN_WIDTH, START_SCREEN_HEIGHT);

    public static final Color[] COLORS_PAGE_START_COLORS = new Color[] {
            new Color(125, 0, 0),
            new Color(125, 64, 0),
            new Color(125, 200, 0),
            new Color(110, 255, 0),
            new Color(80, 50, 80),
            new Color(190, 0, 255),
            Color.cyan,
            new Color(0, 0, 125),
            new Color(90, 0, 90),
            Color.red,
            new Color(255, 0, 130),
            Color.pink,
            new Color(255, 128, 0),
            new Color(60, 170, 60),
            new Color(128, 0, 255),
    };

    public static final Color DEFAULT_BUTTON_COLOR = new Color(0x111111);
    public static final Color DEFAULT_BUTTON_HOVER_COLOR = new Color(0x222222);
    public static final Color DEFAULT_BUTTON_CLICK_COLOR = new Color(0x333333);

    public static final int START_SCREEN = GameStateManager.FRONT_PAGE;

    public static final Font SMALL_FONT = new Font("Century Gothic", Font.PLAIN, 16);
    public static final Font KINDA_SMALL_FONT = new Font("Century Gothic", Font.PLAIN, 20);
    public static final Font DEFAULT_FONT = new Font("Century Gothic", Font.PLAIN, 24);
    public static final Font KINDA_BIG_FONT = new Font("Century Gothic", Font.PLAIN, 32);
    public static final Font BIG_FONT = new Font("Century Gothic", Font.PLAIN, 40);
    public static final Font VERY_BIG_FONT = new Font("Century Gothic", Font.PLAIN, 48);
    public static final Font VERY_VERY_BIG_FONT = new Font("Century Gothic", Font.PLAIN, 64);
    public static final Color TEXT_COLOR = Color.white;

    public static int FRACTAL_RENDER_SIZE = 1080;

    private final FractalInfo currentFractalInfo;

    private Color textColor;
    private Font font;
    private GameState currentState;

    public GameVariables() {
        instance = this;

        currentFractalInfo = new FractalInfo(Fractal.MANDELBROT);
    }

    public static GameVariables getInstance() {
        return instance;
    }

    public void setCurrentGameState(GameState state) {
        currentState = state;
    }

    public GameState getCurrentGameState() {
        return currentState;
    }

    public FractalInfo getFractalInfo() {
        return currentFractalInfo;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
