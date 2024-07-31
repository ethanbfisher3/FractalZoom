package Fractals;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SavedFractal {

    public static final ArrayList<SavedFractal> savedFractals = new ArrayList<>();

    private BufferedImage image;
    private FractalInfo fractalInfo;
    private File imageFile;
    private File infoFile;

    public SavedFractal() {
    }

    public SavedFractal(BufferedImage image, FractalInfo info) {
        this.image = image;
        fractalInfo = info;
    }

    public static SavedFractal SaveImage(BufferedImage image, FractalInfo info) {
        final SavedFractal f = new SavedFractal(image, info);
        savedFractals.add(f);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    File dir = new File("FractalPics");
                    if (!dir.exists())
                        dir.mkdir();

                    String fractalInfoAString = info.toString();
                    String fractalInfoFileSuffix = ".fractalinfo";

                    String fractalFileName = "FractalPics/Fractal";
                    String fractalFileSuffix = ".png";

                    int number = 0;

                    File fractalFile = new File(fractalFileName + "(" + number + ")" + fractalFileSuffix);
                    while (!fractalFile.createNewFile()) {
                        number++;
                        fractalFile = new File(fractalFileName + "(" + number + ")" + fractalFileSuffix);
                    }

                    File fractalInfoFile = new File(fractalFileName + "(" + number + ")" + fractalInfoFileSuffix);
                    fractalInfoFile.createNewFile();
                    FileWriter writer = new FileWriter(fractalInfoFile);
                    writer.write(fractalInfoAString);
                    writer.close();

                    System.out.println("Image saved!");

                    f.imageFile = fractalFile;
                    f.infoFile = fractalInfoFile;

                    ImageIO.write(image, "png", fractalFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return f;
    }

    public static void GetAllSavedFractals() {
        try {
            File dir = new File("FractalPics");
            if (!dir.exists())
                return;

            for (final File fileEntry : dir.listFiles()) {
                String name = fileEntry.getName();

                if (name.contains(".fractalinfo")) {
                    // read the fractal info from the file
                    BufferedReader reader = new BufferedReader(new FileReader(new File("FractalPics/" + name)));
                    FractalInfo info = FractalInfo.fromString(reader.readLine());

                    // get the fractal png file
                    String fractalImageFileName = name.replace(".fractalinfo", ".png");
                    File imageFile = new File("FractalPics/" + fractalImageFileName);
                    BufferedImage image = ImageIO.read(imageFile);

                    SavedFractal f = new SavedFractal(image, info);
                    f.imageFile = imageFile;
                    f.infoFile = fileEntry;

                    savedFractals.add(f);

                    reader.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFiles() {
        boolean deleteImage = imageFile.delete();
        boolean deleteInfo = infoFile.delete();
        savedFractals.remove(this);
        if (deleteImage && deleteInfo) {
            System.out.println("Files deleted.");
        } else
            System.out.println("Files not deleted");
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public FractalInfo getFractalInfo() {
        return fractalInfo;
    }

    public void setFractalInfo(FractalInfo fractalInfo) {
        this.fractalInfo = fractalInfo;
    }

    public String getName() {
        return imageFile.getName();
    }

}
