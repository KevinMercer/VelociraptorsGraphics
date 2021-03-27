package velociraptor.window;

import lombok.extern.java.Log;
import velociraptor.constant.Constant;
import velociraptor.function.Position;
import velociraptor.util.ScreenUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

/**
 * @author Velociraptor
 */
@Log
public class VelociraptorWindow extends JFrame {

    private static VelociraptorWindow velociraptorWindow;

    private Graphics myGraphics;

    private FileChooserListener fileChooserListener;

    private VelociraptorWindow() {
        createWindow();
    }

    public static VelociraptorWindow getInstance() {
        if (velociraptorWindow == null) {
            synchronized (VelociraptorWindow.class) {
                if (velociraptorWindow == null) {
                    if (VelociraptorWindowCount.velociraptorWindowCount > 0) {
                        throw new RuntimeException("Only one window program open is allowed.");
                    }
                    velociraptorWindow = new VelociraptorWindow();
                    VelociraptorWindowCount.velociraptorWindowCount++;
                }
            }
        }
        return velociraptorWindow;
    }

    public void velocityPaint(List<Position> positionList) {
        myGraphics = this.getGraphics();
        if (positionList == null) {
            JOptionPane.showMessageDialog(this, "像素点坐标表是空的。");
            log.info("像素点坐标表是空的。");
            return;
        }
        myGraphics.clearRect(Constant.ZERO, Constant.ZERO, Constant.ZERO, Constant.ZERO);
        StringBuffer result = new StringBuffer("{");
        positionList.forEach(position -> {
            try {
                myGraphics.fillRect(position.getX() + Constant.DRAW_OFFSET, position.getY() + Constant.DRAW_OFFSET, position.getWidth(), position.getHeight());
                myGraphics.setColor(new Color(position.getColor()));
                result.append("{")
                        .append("x = ").append(position.getX())
                        .append(", y = ").append(position.getY())
                        .append(", width = ").append(position.getWidth())
                        .append(", height = ").append(position.getHeight())
                        .append(", color = ").append(position.getColor()).append("}, ");
            } catch (Exception exception) {
                exception.printStackTrace();
                log.info("绘图过程中出现异常。");
                log.info(exception.getStackTrace().toString());
            }
        });
        result.append("}");
        setClipBoard(result.toString());
        JOptionPane.showMessageDialog(this, "已复制到剪切板，共计耗费" + positionList.size() + "个box。");
    }

    private void createWindow() {
        setBounds((int) ((ScreenUtil.getScreenSize().width - Constant.VELOCIRAPTOR_WINDOW_WIDTH) * 0.5), (int) ((ScreenUtil.getScreenSize().height - Constant.VELOCIRAPTOR_WINDOW_HEIGHT) * 0.5), Constant.VELOCIRAPTOR_WINDOW_WIDTH, Constant.VELOCIRAPTOR_WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Velociraptor的像素图片转换器V1.0");
        setResizable(false);
        add(createPanel());
    }

    private JPanel createPanel() {
        JPanel jPanel = new JPanel();
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.setVisible(true);
        jMenuBar.setBorderPainted(true);
        JMenu jMenu = new JMenu("选项");
        JMenuItem addPicture = new JMenuItem("添加图片");
        JMenuItem aboutMe = new JMenuItem("关于Velociraptor的像素图片转换器");
        jMenu.add(addPicture);
        jMenu.add(aboutMe);
        jMenuBar.add(jMenu);
        setJMenuBar(jMenuBar);
        if (fileChooserListener == null) {
            fileChooserListener = new FileChooserListener(addPicture);
        }
        addPicture.addActionListener(fileChooserListener);
        return jPanel;
    }

    public void doRepaint() {
        repaint();
    }

    private void setClipBoard(String result) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(result), null);
    }

    private static class VelociraptorWindowCount {

        private static int velociraptorWindowCount = 0;

    }

}
