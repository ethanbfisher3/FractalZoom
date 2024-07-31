package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import Fractals.Fractal;
import Fractals.SavedFractal;
import Fractals.FractalInfo;
import Main.ContentPane;
import Main.GameVariables;
import Main.Input;
import UI.Anchor;
import UI.Button;
import UI.Buttons;
import UI.UI;
import UIComponents.*;

public class RenderingPage extends GameState {

	private static final Color COMPONENT_BG_COLOR = new Color(0x666666);
	private static final int IMAGE_BUFFER = 50;
	private static final int imageX = -100 + (ContentPane.WIDTH - GameVariables.FRACTAL_RENDER_SIZE) / 2 + IMAGE_BUFFER;

	// components
	private ViewSavedFractalsComponent viewSavedFractals;
	private FractalColoring fractalColoring;
	private NavigationComponent navigation;
	private InstructionsComponent instructions;

	private Fractal fractal;
	private FractalInfo fractalInfo;
	private BufferedImage fractalImage;
	private Thread updateThread;

	private boolean hideStats = true;
	private double calcTime;

	public RenderingPage(GameStateManager gsm) {
		super(gsm, "res/Background4.png");

		final RenderingPage page = this;

		new Thread(new Runnable() {
			@Override
			public void run() {
				fractalColoring = new FractalColoring(page);
				fractalColoring.setAnchor(Anchor.TOP_RIGHT);
				fractalColoring.setBuffer(20);
				fractalColoring.setColorSize(80);
				fractalColoring.setBounds(-530, 50, 480, 520);
				fractalColoring.setBackgroundColor(COMPONENT_BG_COLOR);
				addUI(fractalColoring);

				viewSavedFractals = new ViewSavedFractalsComponent(page);
				viewSavedFractals.setAnchor(Anchor.CENTER_RIGHT);
				viewSavedFractals.setBuffer(20);
				viewSavedFractals.setBounds(-530, 70, 480, 410);
				viewSavedFractals.setFractalRenderSize(102);
				viewSavedFractals.setDistanceBetweenFractals(112);
				viewSavedFractals.setRows(3);
				viewSavedFractals.setColumns(4);
				viewSavedFractals.setImageIconSize(40);
				viewSavedFractals.setBackgroundColor(COMPONENT_BG_COLOR);
				addUI(viewSavedFractals);

				navigation = new NavigationComponent(page);
				navigation.setAnchor(Anchor.CENTER_LEFT);
				navigation.setBuffer(20);
				navigation.setBounds(50, -100, 280, 590);
				navigation.setBackgroundColor(COMPONENT_BG_COLOR);
				addUI(navigation);

				int instructionsSize = GameVariables.FRACTAL_RENDER_SIZE - IMAGE_BUFFER * 2;
				instructions = new InstructionsComponent(page);
				instructions.setAnchor(Anchor.CENTER);
				instructions.setBounds(-instructionsSize / 2, IMAGE_BUFFER, instructionsSize, instructionsSize);

				fractalInfo = GameVariables.getInstance().getFractalInfo();
				fractal = Fractal.getFractal(fractalInfo.getFractalType());

				if (fractalImage == null)
					fractalImage = new BufferedImage(GameVariables.FRACTAL_RENDER_SIZE,
							GameVariables.FRACTAL_RENDER_SIZE,
							BufferedImage.TYPE_INT_RGB);

				Fractal fractal = Fractal.getFractal(fractalInfo.getFractalType());
				fractal.display(page);

				Button saveImageButton = new Button();
				saveImageButton.setFont(GameVariables.DEFAULT_FONT);
				saveImageButton.setBounds(50, 230, 280, 75);
				saveImageButton.setOnClickListener(() -> {
					SavedFractal.SaveImage(fractalImage, fractalInfo);
				});
				saveImageButton.setText("Save Image");
				addUI(saveImageButton);

				Button resetValuesButton = new Button("Reset Values");
				resetValuesButton.setFont(GameVariables.DEFAULT_FONT);
				resetValuesButton.setBounds(50, 325, 280, 75);
				resetValuesButton.setOnClickListener(() -> {
					fractalInfo.resetValues();
					updateImage();
				});
				addUI(resetValuesButton);

				addUI(Buttons.getBackButton(gsm::back));
			}
		}).start();
	}

	@Override
	public void init() {
		updateImage();
	}

	@Override
	public void draw(Graphics2D g) {
		// g.drawImage(background, 0, 0, ContentPane.WIDTH, ContentPane.WIDTH, null);
		g.setColor(new Color(0x222222));
		g.fillRect(0, 0, ContentPane.WIDTH, ContentPane.HEIGHT);

		g.setColor(GameVariables.TEXT_COLOR);
		g.setFont(GameVariables.VERY_VERY_BIG_FONT);
		UI.drawCenteredText(g, "Fractal", 190, 120);
		UI.drawCenteredText(g, "Zoomer", 190, 190);

		g.drawImage(fractalImage, imageX, IMAGE_BUFFER,
				GameVariables.FRACTAL_RENDER_SIZE - IMAGE_BUFFER * 2,
				GameVariables.FRACTAL_RENDER_SIZE - IMAGE_BUFFER * 2, null);

		g.setFont(GameVariables.KINDA_SMALL_FONT);
		g.setColor(GameVariables.TEXT_COLOR);
		int textX = 25;
		int textY = 90;

		if (!hideStats) {
			g.drawString(String.format("Time to calculate: %4.2f ms", calcTime), textX, textY);
			g.drawString(String.format("Zoom Power: %f", fractalInfo.getZoomPower()), textX, textY + 25);
			g.drawString(String.format("Offset: %f, %f", fractalInfo.getOffsetX(), fractalInfo.getOffsetY()), textX,
					textY + 50);
			g.drawString(String.format("Iterations: %d", fractalInfo.getMaxIterations()), textX, textY + 75);
		}

		if (!canUpdateImage()) {
			g.setColor(new Color(0x222222));
			g.setFont(GameVariables.KINDA_BIG_FONT);
			g.fillRect(imageX + 20, 70, 415, 70);
			g.setColor(Color.white);
			g.drawString("Rendering... Please wait", imageX + 40, IMAGE_BUFFER + 40 + 25);
		}

		super.draw(g);
	}

	@Override
	public void update(float dt) {
		super.update(dt);
		if (Input.getKey(KeyEvent.VK_UP))
			fractalInfo.setMaxIterations(fractalInfo.getMaxIterations() + 10);
		else if (Input.getKey(KeyEvent.VK_DOWN))
			fractalInfo.setMaxIterations(fractalInfo.getMaxIterations() - 10);
		else if (Input.getKey(KeyEvent.VK_ENTER) && canUpdateImage())
			updateImage();
		else if (Input.getKeyDown(KeyEvent.VK_H))
			hideStats = !hideStats;
	}

	@Override
	public boolean mouseClick(Point point) {
		if (super.mouseClick(point))
			return true;
		int bufferLeft = (ContentPane.WIDTH - GameVariables.FRACTAL_RENDER_SIZE) / 2;
		int bufferRight = ContentPane.WIDTH - bufferLeft;
		if (point.x > bufferLeft && point.x < bufferRight && canUpdateImage()) {
			point.x -= (ContentPane.WIDTH - GameVariables.FRACTAL_RENDER_SIZE) / 2;
			fractalInfo.addOffset(point);
			updateImage();
			return true;
		}
		return false;
	}

	@Override
	public void mouseWheelMoved(int wheelRotation) {
		if (canUpdateImage()) {
			if (wheelRotation < 1)
				fractalInfo.zoomIn();
			else
				fractalInfo.zoomOut();
		}
	}

	public void updateColorScheme() {
		fractalColoring.setColors(GameVariables.getInstance().getFractalInfo().getColors());
	}

	// private void saveStats() {
	// StringBuilder builder = new StringBuilder();
	// builder.append("Offset X: " + fractalInfo.getOffsetX());
	// builder.append(", Offset Y: " + fractalInfo.getOffsetY());
	// builder.append(", Iterations: " + fractalInfo.getMaxIterations());
	// builder.append(", Zoom Power: " + fractalInfo.getZoomPower());
	// java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new
	// StringSelection(builder.toString()),
	// null);
	// }

	public boolean canUpdateImage() {
		return updateThread == null;
	}

	public void updateImage() {
		updateThread = new Thread(new Runnable() {
			@Override
			public void run() {
				long startTime = System.nanoTime();

				renderFractal();

				calcTime = (System.nanoTime() - startTime) / 1000000.0;

				updateThread = null;
			}
		});
		updateThread.start();
	}

	public void updateImageColors() {

		Color[][] colors = fractalInfo.iterationsToColors(fractal.getIterations());

		BufferedImage image = new BufferedImage(GameVariables.FRACTAL_RENDER_SIZE, GameVariables.FRACTAL_RENDER_SIZE,
				BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < colors.length; x++)
			for (int y = 0; y < colors[0].length; y++)
				image.setRGB(x, y, colors[x][y].getRGB());

		fractalImage = image;
	}

	public void renderFractal() {
		BufferedImage image = fractal.toImage(fractalInfo);
		fractalImage = image;
	}

}
