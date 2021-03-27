package velociraptor.function;

import velociraptor.window.VelociraptorWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

/**
 * @author 18704
 */
public class ReadPicture {

    private static ReadPicture readPicture;

    private ReadPicture() {
    }

    public static ReadPicture getInstance() {
        if (readPicture == null) {
            synchronized (ReadPicture.class) {
                if (readPicture == null) {
                    if (ReadPictureCount.readPictureCount > 0) {
                        throw new RuntimeException("Only one picture reader constructed is allowed！");
                    }
                    readPicture = new ReadPicture();
                    ReadPictureCount.readPictureCount++;
                }
            }
        }
        return readPicture;
    }

    private static class ReadPictureCount {

        private static int readPictureCount = 0;

    }

    public static int[][] imageToBmp(String filePath) {
        try {
            BufferedImage sourceImg = ImageIO.read(new File(filePath));
            int sourceHeight = sourceImg.getHeight();
            int sourceWidth = sourceImg.getWidth();
            int[] pixelArray = new int[sourceWidth * sourceHeight];
            PixelGrabber pixelGrabber = new PixelGrabber(sourceImg, 0, 0, sourceWidth, sourceHeight, pixelArray, 0, sourceWidth);
            pixelGrabber.grabPixels();
            MemoryImageSource memoryImageSource = new MemoryImageSource(sourceWidth, sourceHeight, pixelArray, 0, sourceWidth);
            Image image = Toolkit.getDefaultToolkit().createImage(memoryImageSource);
            BufferedImage bufferedImage = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_USHORT_565_RGB);
            bufferedImage.createGraphics().drawImage(image, 0, 0, null);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            int startX = 0;
            int startY = 0;
            int offset = 0;
            int scanSize = width;
            int[] rgbArray = new int[offset + (height - startY) * scanSize + (width - startX)];
            int[][] bitmapArray = new int[width][height];
            int totalLength = 0;
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmapArray[x][y] = bufferedImage.getRGB(x, y);
                    totalLength++;
                }
            }
            return bitmapArray;
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), "对图片进行流转换时出现异常，请更换图源。");
            ioException.printStackTrace();
            return null;
        } catch (InterruptedException interruptedException) {
            JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), "对图片进行像素化时出现异常，请更换图源。");
            interruptedException.printStackTrace();
            return null;
        }
    }


}
