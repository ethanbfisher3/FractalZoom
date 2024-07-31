package Main;

import java.awt.Color;

public class ImageSmoother {

    private static int BLACK_RGB = Color.black.getRGB();
    private static int COLOR_CHECK = 10;

    public static void smoothImage(Color[][] colors) {

        for (int x = colors.length / 2; x < colors.length - 1; x += 2) {
            for (int y = 1; y < colors[0].length - 1; y += 2) {

                Color color = colors[x][y];
                if (color.getRGB() == BLACK_RGB)
                    continue;

                // get the neighboring pixels
                Color[] neighbors = {
                    colors[x - 1][y - 1], colors[x + 1][y - 1],
                    
                    colors[x - 1][y + 1], colors[x + 1][y + 1]
                };

                for (int i = 0; i < 4; i++) {
                    Color n = neighbors[i];
                    if (n.getRGB() == BLACK_RGB) 
                        break;

                    Color old = new Color(color.getRGB());
                    colors[x][y] = fuseColorsIfAble(color, n);
                    if (colors[x][y].getRGB() != old.getRGB())
                        break;
                }

            }
        }

    }

    /*
     * @returns 3 booleans, each of which indicating whether color one's
     *  red, green, or blue values are higher than that of color two
     */
    private static Color fuseColorsIfAble(Color one, Color two) {
        int red = one.getRed(), green = one.getGreen(), blue = one.getBlue();

        int diff = Math.abs(red - two.getRed());
        if (diff < COLOR_CHECK) {
            // red = (red + two.getRed()) / 2;
            red += 50;
        }
        diff = Math.abs(green - two.getGreen());
        if (diff < COLOR_CHECK) {
            // green = (green + two.getGreen()) / 2;
            green += 50;
        }
        diff = Math.abs(blue - two.getBlue());
        if (diff < COLOR_CHECK) {
            // blue = (blue + two.getBlue()) / 2;
            blue += 50;
        }
        red = Math.min(255, red);
        green = Math.min(255, green);
        blue = Math.min(255, blue);
        return new Color(red, green, blue);
    }
    
}
