package velociraptor.function;

import lombok.extern.java.Log;
import velociraptor.constant.Constant;
import velociraptor.window.VelociraptorWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

/**
 * @author Velociraptor
 * @apiNote 读取图流
 */
@Log
public class ReadPicture implements Constant {

    private ReadPicture() {
    }

    public static int[][] imageToBmp(String filePath) {
        try {
            BufferedImage sourceImg = ImageIO.read(new File(filePath));
            int sourceHeight = sourceImg.getHeight();
            int sourceWidth = sourceImg.getWidth();
            BufferedImage bufferedImage = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = bufferedImage.createGraphics();
            bufferedImage = graphics2D.getDeviceConfiguration().createCompatibleImage(sourceWidth, sourceHeight, Transparency.TRANSLUCENT);
            graphics2D.dispose();
            graphics2D = bufferedImage.createGraphics();
            graphics2D.drawImage(sourceImg, ZERO, ZERO, null);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            int[][] bitmapArray = new int[width][height];
            for (int x = ZERO; x < width; x++) {
                for (int y = ZERO; y < height; y++) {
                    bitmapArray[x][y] = bufferedImage.getRGB(x, y);
                }
            }
            return bitmapArray;
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), TRANSLATE_IMAGE_TO_STREAM_ERROR);
            ioException.printStackTrace();
            return null;
        }
    }


}
