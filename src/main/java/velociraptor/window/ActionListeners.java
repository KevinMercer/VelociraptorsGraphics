package velociraptor.window;

import lombok.extern.java.Log;
import velociraptor.constant.Constant;
import velociraptor.function.CollectPosition;
import velociraptor.function.ReadPicture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.HashMap;

/**
 * @author Velociraptor
 */
@Log
public class ActionListeners {
}

@Log
class FileChooserListener implements ActionListener {

    private String filePath;

    public FileChooserListener(Component component) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        VelociraptorWindow.getInstance().repaint();
        JFileChooser pictureChooser = new JFileChooser(filePath);
        pictureChooser.setMultiSelectionEnabled(false);
        pictureChooser.showOpenDialog(VelociraptorWindow.getInstance());
        filePath = pictureChooser.getSelectedFile().getPath();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            byte[] bytes = new byte[4];
            fileInputStream.read(bytes, 0, bytes.length);
            String fileInnerCode = bytesToHexString(bytes);
            if (!Constant.JPG_CODE.equals(fileInnerCode) && !Constant.PNG_CODE.equals(fileInnerCode)) {
                JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), "暂时只支持JPG和PNG格式的图片。");
                return;
            }
            VelociraptorWindow.getInstance().velocityPaint(CollectPosition.getPositionList(ReadPicture.imageToBmp(filePath)));
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), "啊哦，出现了未知异常，请重试！");
            exception.printStackTrace();
            log.info(exception.getStackTrace().toString());
            return;
        }
    }

    private String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (byte aSrc : src) {
            hv = Integer.toHexString(aSrc & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

}