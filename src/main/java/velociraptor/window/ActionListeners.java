package velociraptor.window;

import lombok.extern.java.Log;
import velociraptor.constant.Constant;
import velociraptor.function.CollectPosition;
import velociraptor.function.ReadPicture;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;

/**
 * @author Velociraptor
 */
@Log
public class ActionListeners {
}

@Log
class FileChooserListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        VelociraptorWindow.getInstance().repaint();
        JFileChooser pictureChooser = new JFileChooser(VelociraptorWindow.getInstance().getImagePath());
        pictureChooser.setMultiSelectionEnabled(false);
        pictureChooser.showOpenDialog(VelociraptorWindow.getInstance());
        VelociraptorWindow.getInstance().setImagePath(pictureChooser.getSelectedFile().getPath());
        try {
            FileInputStream fileInputStream = new FileInputStream(VelociraptorWindow.getInstance().getImagePath());
            byte[] bytes = new byte[Constant.FOUR];
            fileInputStream.read(bytes, Constant.ZERO, bytes.length);
            String fileInnerCode = bytesToHexString(bytes);
            if (!Constant.PNG_CODE.equals(fileInnerCode)) {
                JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), "暂时只支持PNG格式的图片。");
                return;
            }
            log.info(VelociraptorWindow.getInstance().getImagePath());
            VelociraptorWindow.getInstance().velocityPaint(CollectPosition.getPositionList(ReadPicture.imageToBmp(VelociraptorWindow.getInstance().getImagePath()), VelociraptorWindow.getInstance().getPixel()));
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), "啊哦，出现了未知异常，请重试！");
            exception.printStackTrace();
            log.info(exception.getStackTrace().toString());
            return;
        }
    }

    private String bytesToHexString(byte[] byteArray) {
        StringBuilder stringBuilder = new StringBuilder();
        if (byteArray == null || byteArray.length <= Constant.ZERO) {
            return null;
        }
        String hexValue;
        for (byte b : byteArray) {
            hexValue = Integer.toHexString(b & Constant._0XFF).toUpperCase();
            if (hexValue.length() < Constant.TWO) {
                stringBuilder.append(Constant.ZERO);
            }
            stringBuilder.append(hexValue);
        }
        return stringBuilder.toString();
    }

}

@Log
class AboutMeListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), Constant.AUTHOR_S_WORD);
    }
}

@Log
class SliderListener implements ChangeListener {

    private JSlider jSlider;

    public SliderListener(JSlider jSlider) {
        this.jSlider = jSlider;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        VelociraptorWindow.getInstance().setPixel(jSlider.getValue());
        VelociraptorWindow.getInstance().setPositionList(CollectPosition.getPositionList(ReadPicture.imageToBmp(VelociraptorWindow.getInstance().getImagePath()), VelociraptorWindow.getInstance().getPixel()));
        VelociraptorWindow.getInstance().repaint();
    }
}