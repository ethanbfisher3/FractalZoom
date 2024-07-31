package GameState;

import java.awt.Color;
import java.util.ArrayList;

import Fractals.FractalInfo;
import Main.GameVariables;
import UI.Anchor;
import UI.Button;
import UI.Buttons;
import UI.ColorField;
import UI.UIElement;

public class ColorsPage extends GameState {

	private ArrayList<ColorField> colorFields;
	private Button addColorFieldButton;
	private FractalInfo info;

	public ColorsPage(GameStateManager scm) {
		super(scm, null);
		
		colorFields = new ArrayList<>();

		info = GameVariables.getInstance().getFractalInfo();

		Color[] startColors = GameVariables.COLORS_PAGE_START_COLORS;
		
		for (int i = 0; i < startColors.length; i++) {
			final int num = i;
			ColorField colorField = new ColorField(startColors[i], true);
			colorField.setBounds(100 + 300 * i, 100, 200, 400);
			colorField.setColorChangedListener((color) -> { info.setColor(num, color); });
			colorFields.add(colorField);
		}

		addColorFieldButton = new Button("Add Color");
		addColorFieldButton.setAnchor(Anchor.TOP_CENTER);
		addColorFieldButton.setBounds(-75, 10, 150, 50);
		addColorFieldButton.setAlignment(null);
		addColorFieldButton.setOnClickListener(() -> { addColorField(); });
	}

	@Override
	public void init() {

		removeAllUIElements();
		
		setColorFieldBounds();
		for (UIElement element : colorFields) 
			addUI(element);
		
		addUI(addColorFieldButton);
		addUI(Buttons.getBackButton(gsm::back));
	}

	@Override
	public void update(float dt) {
		super.update(dt);
		setColorFieldBounds();
	}

	private void addColorField() {
		final int num = colorFields.size();
		if (num == 9) return;
		ColorField colorField = new ColorField(Color.white, true);
		colorField.setColorChangedListener((color) -> { info.setColor(num, color); });
		colorFields.add(colorField);
		addUI(colorField);

		setColorFieldBounds();
	}

	private void setColorFieldBounds() {
		for (int i = 0; i < colorFields.size(); i++)
			colorFields.get(i).setBounds(100 + 300 * (i % 3), 100 + 300 * (i / 3), 200, 200);
	}

}
