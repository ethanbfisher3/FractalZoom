package Fractals;

import GameState.RenderingPage;

public class BurningShip extends Fractal {

	public BurningShip() {
	}

	@Override
	protected int generateIterationCount(double x, double y, int max, boolean asJulia) {
		ComplexNumber start = new ComplexNumber(0, 0);
		ComplexNumber add = new ComplexNumber(x, y);

		int iterations = 0;
		while (start.sqrDistance() < 4 && iterations < max) {
			start = start.squared();
			start.add(add);
			start.abs();
			iterations++;
		}

		return iterations;
	}

	@Override
	public void display(RenderingPage page) {

		// if (powerSpinner != null) {
		// page.addUI(powerSpinner, updateButton);
		// powerSpinner.setVisible(true);
		// powerSpinner.setVisible(false);
		// }

		// powerSpinner = new Spinner();
		// powerSpinner.setValue(power);
		// powerSpinner.setDisplayAsInt(true);
		// powerSpinner.setIncrement(1);
		// powerSpinner.setBounds(500, 300, 120, 60);
		// powerSpinner.setMinAndMax(2, 10);

		// updateButton = new Button("Update");
		// updateButton.setBounds(500, 500, 120, 60);
		// updateButton.setOnClickListener(() -> {
		// if (page.canUpdateImage()) {
		// power = (int) powerSpinner.getValue();
		// page.updateImage();
		// }
		// });

		// page.addUI(powerSpinner, updateButton);
	}

}
