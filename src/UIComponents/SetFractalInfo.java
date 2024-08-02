package UIComponents;

import Fractals.FractalInfo;
import GameState.GameState;
import Main.GameVariables;
import Main.Mathf;
import UI.Button;
import UI.Component;
import UI.Text.NumberDocument;
import UI.Text.Text;
import UI.Text.TextField;

public class SetFractalInfo extends Component {

	private static final int textFieldWidth = 200;
	private static final int textFieldHeight = 50;
	private static final int textFieldYDist = 150;

	private static final String[] TEXT_FIELD_NAMES = new String[] {
			"X Offset (" + -GameVariables.FRACTAL_RENDER_SIZE / 2 + " to " + GameVariables.FRACTAL_RENDER_SIZE / 2
					+ ")",
			"Y Offset (" + -GameVariables.FRACTAL_RENDER_SIZE / 2 + " to " + GameVariables.FRACTAL_RENDER_SIZE / 2
					+ ")",
			"Iterations (10 to " + GameVariables.MAX_ITERATIONS + ")",
			"Zoom (0 to " + GameVariables.MAX_ZOOM_POWER + ")" };;

	private FractalInfo fractalInfo;
	private TextField[] textFields;
	private Text[] labels;
	private Button resetButton;

	public SetFractalInfo(final GameState state) {
		super(state);

		fractalInfo = GameVariables.getInstance().getFractalInfo();
		textFields = new TextField[TEXT_FIELD_NAMES.length];
		labels = new Text[TEXT_FIELD_NAMES.length];

		for (int i = 0; i < textFields.length; i++) {

			TextField textField = new TextField("");
			NumberDocument document = new NumberDocument(textField, true, true);
			document.setLimit(10);
			textField.setDocument(document);
			textField.setFont(GameVariables.DEFAULT_FONT);
			textField.setColor(GameVariables.TEXT_COLOR);
			textFields[i] = textField;
			children.add(textField);

			Text label = new Text(TEXT_FIELD_NAMES[i]);
			label.setColor(GameVariables.TEXT_COLOR);
			label.setFont(GameVariables.DEFAULT_FONT);
			label.setAlignment(Text.TextAlign.CENTER);
			labels[i] = label;
			children.add(label);
		}

		textFields[0].setText(fractalInfo.getOffsetX() + "");
		textFields[1].setText(fractalInfo.getOffsetY() + "");
		textFields[2].setText(fractalInfo.getMaxIterations() + "");
		textFields[3].setText(fractalInfo.getZoomPower() + "");

		resetButton = new Button("Reset Values");
		resetButton.setFont(GameVariables.DEFAULT_FONT);
		resetButton.setOnClickListener(() -> {

			fractalInfo.setOffsetX(0);
			fractalInfo.setOffsetY(0);
			fractalInfo.setMaxIterations(100);
			fractalInfo.setZoomPower(0);

			textFields[0].setText(fractalInfo.getOffsetX() + "");
			textFields[1].setText(fractalInfo.getOffsetY() + "");
			textFields[2].setText(fractalInfo.getMaxIterations() + "");
			textFields[3].setText(fractalInfo.getZoomPower() + "");
		});

		children.add(resetButton);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		for (int i = 0; i < textFields.length; i++) {
			textFields[i].setBounds(x + buffer, y + buffer + 40 + (i * textFieldYDist), width - buffer * 2,
					textFieldHeight);
			labels[i].setBounds(x + width / 2, y + buffer + 20 + (i * textFieldYDist), textFieldWidth, textFieldHeight);
		}
		resetButton.setBounds(x + buffer, y + height - (buffer + 75), width - buffer * 2, 75);
	}

	public void checkInputs() {
		// offset x and y
		try {
			double offsetX = Double.parseDouble(textFields[0].getText());

			if (Math.abs(offsetX) <= GameVariables.FRACTAL_RENDER_SIZE / 2)
				fractalInfo.setOffsetX(offsetX);
			else
				fractalInfo.setOffsetX(Mathf.clamp(-GameVariables.FRACTAL_RENDER_SIZE / 2,
						GameVariables.FRACTAL_RENDER_SIZE / 2, offsetX));
		} catch (Exception e) {
			fractalInfo.setOffsetX(0);
		}

		try {
			double offsetY = Double.parseDouble(textFields[1].getText());

			if (Math.abs(offsetY) <= GameVariables.FRACTAL_RENDER_SIZE / 2)
				fractalInfo.setOffsetY(offsetY);
			else
				fractalInfo.setOffsetY(Mathf.clamp(-GameVariables.FRACTAL_RENDER_SIZE / 2,
						GameVariables.FRACTAL_RENDER_SIZE / 2, offsetY));
		} catch (Exception e) {
			fractalInfo.setOffsetY(0);
		}

		// iterations
		try {
			int iterations = Integer.parseInt(textFields[2].getText());

			if (iterations >= 10 && iterations <= GameVariables.MAX_ITERATIONS)
				fractalInfo.setMaxIterations(iterations);
			else
				fractalInfo.setMaxIterations(10);
		} catch (Exception e) {
			fractalInfo.setMaxIterations(10);
		}

		// zoom
		try {
			int zoomPower = Integer.parseInt(textFields[3].getText());

			if (zoomPower >= 0 && zoomPower <= GameVariables.MAX_ZOOM_POWER)
				fractalInfo.setZoomPower(zoomPower);
			else
				fractalInfo.setZoomPower(0);
		} catch (Exception e) {
			fractalInfo.setZoomPower(0);
		}
	}

}
