package Fractals;

import java.awt.Color;
import java.awt.Point;

import Main.GameVariables;
import Main.Mathf;

/**
 * Stores information about the fractal, like zoom, iterations, offset, etc.
 */
public class FractalInfo {

	// each number is the amount of iterations to use that color index in the color
	// array
	private static int[] COLOR_ITERATIONS = { 5, 15, 40, 60, 100, 200, 300, 425, 575, 700, 800, 900, 1000, 1100 };

	private int fractalType;
	private int[][] iterations;

	private boolean renderAsJulia;
	private ComplexNumber juliaAdd;

	private Color[] colors;
	private double offsetX;
	private double offsetY;
	private double zoomPower;
	private int imageSize;
	private int maxIterations;

	public FractalInfo(int fractalType) {
		this();
		this.fractalType = fractalType;
	}

	public FractalInfo() {
		colors = GameVariables.COLORS_PAGE_START_COLORS.clone();
		fractalType = Fractal.MANDELBROT;
		offsetX = 0;
		offsetY = 0;
		imageSize = GameVariables.FRACTAL_RENDER_SIZE;
		zoomPower = 0;
		maxIterations = 100;

		renderAsJulia = false;
		juliaAdd = null;
	}

	public FractalInfo(FractalInfo other) {
		colors = other.colors;
		fractalType = other.fractalType;
		offsetX = other.offsetX;
		offsetY = other.offsetY;
		imageSize = other.imageSize;
		zoomPower = other.zoomPower;
		maxIterations = other.maxIterations;

		renderAsJulia = other.renderAsJulia;
		juliaAdd = other.juliaAdd;
	}

	public Color[][] iterationsToColors(int[][] iterations) {
		this.iterations = iterations;
		Color[][] rgbs = new Color[iterations.length][iterations[0].length];
		for (int x = 0; x < iterations.length; x++) {
			for (int y = 0; y < iterations[0].length; y++) {

				int iterationCount = iterations[x][y];
				if (iterationCount == maxIterations) {
					rgbs[x][y] = Color.black;
					continue;
				}

				for (int i = 0; i < COLOR_ITERATIONS.length; i++) {
					if (iterationCount < COLOR_ITERATIONS[i]) {
						Color color = colors[i];

						if (i == 0) {
							rgbs[x][y] = color;
							break;
						}

						Color before = colors[i - 1];
						float percentage = iterationCount / (float) COLOR_ITERATIONS[0];
						if (i > 0) {
							int difference = COLOR_ITERATIONS[i] - COLOR_ITERATIONS[i - 1];
							percentage = (iterationCount - COLOR_ITERATIONS[i - 1]) / (float) difference;
							rgbs[x][y] = Mathf.lerpColor(before, color, percentage);
						}
						break;
					}
				}

				if (rgbs[x][y] == null) {
					rgbs[x][y] = Color.white;
				}
			}
		}
		return rgbs;
	}

	public int[][] iterationsToRGBs(int[][] iterations) {
		this.iterations = iterations;
		Color[][] colors = iterationsToColors(iterations);
		int[][] rgbs = new int[colors.length][colors[0].length];
		for (int x = 0; x < colors.length; x++) {
			for (int y = 0; y < colors[0].length; y++) {
				rgbs[x][y] = colors[x][y].getRGB();
			}
		}
		return rgbs;
	}

	public int getFractalType() {
		return fractalType;
	}

	public boolean renderAsJulia() {
		return renderAsJulia;
	}

	public ComplexNumber juliaAdd() {
		return juliaAdd;
	}

	public double getOffsetX() {
		return offsetX;
	}

	public double getOffsetY() {
		return offsetY;
	}

	public int getImageSize() {
		return imageSize;
	}

	public double getZoomPower() {
		return zoomPower;
	}

	public int getMaxIterations() {
		return maxIterations;
	}

	public Color[] getColors() {
		return colors;
	}

	public int[][] getIterations() {
		if (iterations == null) {
			iterations = Fractal.getFractal(fractalType).getIterations();
		}
		return iterations;
	}

	public void setFractalType(int fractal) {
		fractalType = fractal;
	}

	public void addOffsetX(double offset) {
		offsetX += offset;
	}

	public void addOffsetY(double offset) {
		offsetY += offset;
	}

	public void setOffsetX(double offset) {
		offsetX = offset;
	}

	public void setOffsetY(double offset) {
		offsetY = offset;
	}

	public void setImageSize(int imageSize) {
		this.imageSize = imageSize;
	}

	public void setColors(Color[] colors) {
		this.colors = colors;
	}

	public void setColor(int index, Color color) {
		if (colors.length <= index) {
			Color[] newColors = new Color[index + 1];
			for (int i = 0; i < this.colors.length; i++) {
				newColors[i] = this.colors[i];
			}
			this.colors = newColors;
		}
		this.colors[index] = color;
	}

	public void zoomIn() {
		setZoomPower(zoomPower + 1);
	}

	public void zoomOut() {
		setZoomPower(zoomPower - 1);
	}

	public void addOffset(Point o) {
		double zoom = Math.pow(2, zoomPower);
		offsetX += (o.x - imageSize * 0.5) / zoom;
		offsetY += -(o.y - imageSize * 0.5) / zoom;
	}

	public void setZoomPower(double zoomPower) {
		if (zoomPower >= 0 && zoomPower <= GameVariables.MAX_ZOOM_POWER) {
			this.zoomPower = zoomPower;
		}
	}

	public void setMaxIterations(int maxIterations) {
		if (maxIterations > 0 && maxIterations < GameVariables.MAX_ITERATIONS) {

			if (iterations != null && maxIterations > this.maxIterations)
				for (int x = 0; x < iterations.length; x++) {
					for (int y = 0; y < iterations[0].length; y++) {
						if (iterations[x][y] == this.maxIterations)
							iterations[x][y] = maxIterations;
					}
				}

			this.maxIterations = maxIterations;
			if (maxIterations > COLOR_ITERATIONS[COLOR_ITERATIONS.length - 1])
				COLOR_ITERATIONS[COLOR_ITERATIONS.length - 1] = maxIterations;
		}
	}

	public void setJuliaAdd(ComplexNumber cn) {
		if (cn == null) {
			renderAsJulia = false;
			juliaAdd = null;
		} else {
			renderAsJulia = true;
			juliaAdd = new ComplexNumber(cn);
		}
	}

	public boolean equals(FractalInfo other) {
		return offsetX == other.offsetX
				&& offsetY == other.offsetY
				&& imageSize == other.imageSize
				&& zoomPower == other.zoomPower
				&& maxIterations == other.maxIterations
				&& other.fractalType == fractalType;
	}

	@Override
	public String toString() {
		String line1 = offsetX + "," + offsetY + "," + maxIterations + "," + zoomPower + "," + fractalType + ",";
		String line2 = "";
		for (int i = 0; i < colors.length; i++) {
			line2 += colors[i].getRGB() + ",";
		}
		return line1 + line2.substring(0, line2.length() - 1);
	}

	public void resetValues() {
		setValues(new FractalInfo());
	}

	public void setValues(FractalInfo other) {
		offsetX = other.offsetX;
		offsetY = other.offsetY;
		imageSize = other.imageSize;
		zoomPower = other.zoomPower;
		maxIterations = other.maxIterations;
		fractalType = other.fractalType;
		colors = other.colors;
	}

	public static FractalInfo fromString(String string) {
		FractalInfo info = new FractalInfo();
		String[] values = string.split(",");
		info.offsetX = Double.parseDouble(values[0]);
		info.offsetY = Double.parseDouble(values[1]);
		info.maxIterations = Integer.parseInt(values[2]);
		info.zoomPower = Double.parseDouble(values[3]);
		info.fractalType = Integer.parseInt(values[4]);
		info.colors = new Color[values.length - 5];
		for (int i = 5; i < values.length; i++) {
			info.colors[i - 5] = new Color(Integer.parseInt(values[i]));
		}
		return info;
	}

}
