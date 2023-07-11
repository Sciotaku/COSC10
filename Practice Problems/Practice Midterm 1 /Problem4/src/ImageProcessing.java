import java.awt.image.BufferedImage;

public class ImageProcessing {


    // 4.1
    public void flipX(BufferedImage image) {

        for(int x = 0; x < image.getWidth()/2; x++) {
            for(int y = 0; y < image.getHeight(); y++) {

                // store one color
                int leftColor = image.getRGB(x, y);

                // swap the other two
                image.setRGB(x, y, image.getRGB(image.getWidth() - x, y));
                image.setRGB(image.getWidth() - x, y, leftColor);

            }
        }

    }

    // 4.1
    public void flipY(BufferedImage image) {

        for(int x = 0; x < image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight()/2; y++) {

                // store one color
                int topColor = image.getRGB(x, y);

                // swap the other two
                image.setRGB(x, y, image.getRGB(x, image.getHeight() - y));
                image.setRGB(x, image.getHeight() - y, topColor);

            }
        }

    }
}
