package velociraptor.function;

import lombok.extern.java.Log;
import velociraptor.window.VelociraptorWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

/**
 * @author 18704
 */
@Log
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
            BufferedImage bufferedImage = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_4BYTE_ABGR_PRE);
            log.info(bufferedImage.getTransparency() + "");
            Graphics2D graphics2D = bufferedImage.createGraphics();
            bufferedImage = graphics2D.getDeviceConfiguration().createCompatibleImage(image.getWidth(null), image.getHeight(null), Transparency.TRANSLUCENT);
            graphics2D.dispose();
            graphics2D = bufferedImage.createGraphics();
            graphics2D.drawImage(image, 0, 0, null);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            int[][] bitmapArray = new int[width][height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmapArray[x][y] = bufferedImage.getRGB(x, y);
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
