package velociraptor.window;

import lombok.extern.java.Log;
import velociraptor.constant.Constant;
import velociraptor.function.CollectPosition;
import velociraptor.function.ReadPicture;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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
        JFileChooser pictureChooser = new JFileChooser(VelociraptorWindow.getInstance().getImagePath());
        pictureChooser.setMultiSelectionEnabled(Constant.FALSE);
        pictureChooser.showOpenDialog(VelociraptorWindow.getInstance());
        VelociraptorWindow.getInstance().setImagePath(pictureChooser.getSelectedFile().getPath());
        try {
            FileInputStream fileInputStream = new FileInputStream(VelociraptorWindow.getInstance().getImagePath());
            byte[] bytes = new byte[Constant.FOUR];
            fileInputStream.read(bytes, Constant.ZERO, bytes.length);
            String fileInnerCode = bytesToHexString(bytes);
            if (!Constant.PNG_CODE.equals(fileInnerCode)) {
                JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), Constant.ONLY_PNG_SUPPORT);
                return;
            }
            VelociraptorWindow.getInstance().velocityPaint(CollectPosition.getPositionList(ReadPicture.imageToBmp(VelociraptorWindow.getInstance().getImagePath()), VelociraptorWindow.getInstance().getPixel()), Constant.TRUE);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), Constant.UNKNOWN_ERROR);
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
        for (byte bite : byteArray) {
            hexValue = Integer.toHexString(bite & Constant._0XFF).toUpperCase();
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
    }
}

@Log
class SliderMouseListener implements MouseInputListener {

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (VelociraptorWindow.getInstance().getImagePath() != null) {
            VelociraptorWindow.getInstance().setPositionList(CollectPosition.getPositionList(ReadPicture.imageToBmp(VelociraptorWindow.getInstance().getImagePath()), VelociraptorWindow.getInstance().getPixel()));
            VelociraptorWindow.getInstance().repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}