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

    private Graphics2D myGraphics2d;

    private FileChooserListener fileChooserListener;

    private AboutMeListener aboutMeListener;

    private SliderListener sliderListener;

    private SliderMouseListener sliderMouseListener;

    private Integer pixel;

    private String imagePath;

    private List<Position> positionList;

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

    public void velocityPaint(List<Position> positionList, Boolean showMessageDialog) {
        myGraphics2d = (Graphics2D) this.getGraphics();
        if (positionList == null) {
            if (imagePath != null) {
                JOptionPane.showMessageDialog(this, "像素点坐标表是空的。");
                log.info("像素点坐标表是空的。");
            }
            return;
        }
        myGraphics2d.clearRect(Constant.ZERO, Constant.ZERO, Constant.VELOCIRAPTOR_WINDOW_WIDTH, Constant.VELOCIRAPTOR_WINDOW_HEIGHT);
        StringBuffer result = new StringBuffer("{");
        positionList.forEach(position -> {
            try {
                Color color = new Color(position.getColor());
                myGraphics2d.setColor(color);
                myGraphics2d.fillRect((position.getX() + (Constant.DRAW_OFFSET - (pixel * Constant.OFFSET_RATE))), (position.getY() + (Constant.DRAW_OFFSET - (pixel * Constant.OFFSET_RATE))), position.getWidth(), position.getHeight());
                result.append(position.getX())
                        .append(",").append(position.getY())
                        .append(",").append(position.getWidth())
                        .append(",").append(position.getHeight())
                        .append(",").append(position.getColor());
            } catch (Exception exception) {
                exception.printStackTrace();
                log.info("绘图过程中出现异常。");
                log.info(exception.getStackTrace().toString());
            }
        });
        result.append("}");
        setClipBoard(result.toString());
        if (showMessageDialog) {
            JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), "已复制到剪切板，共计耗费" + positionList.size() + "个box，字符长度：" + result.length());
        }
    }

    private void createWindow() {
        setLayout(new BorderLayout());
        setBounds((int) ((ScreenUtil.getScreenSize().width - Constant.VELOCIRAPTOR_WINDOW_WIDTH) * Constant.HALF_RATE), (int) ((ScreenUtil.getScreenSize().height - Constant.VELOCIRAPTOR_WINDOW_HEIGHT) * Constant.HALF_RATE), Constant.VELOCIRAPTOR_WINDOW_WIDTH, Constant.VELOCIRAPTOR_WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Velociraptor的像素图片转换器V1.0");
        setResizable(false);
        add(createPanel());
        add(createSlider(), BorderLayout.SOUTH);
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
            fileChooserListener = new FileChooserListener();
        }
        if (aboutMeListener == null) {
            aboutMeListener = new AboutMeListener();
        }
        addPicture.addActionListener(fileChooserListener);
        aboutMe.addActionListener(aboutMeListener);
        return jPanel;
    }

    private JSlider createSlider() {
        JSlider jSlider = new JSlider();
        jSlider.setMaximum(Constant.SLIDER_MAX_VALUE);
        jSlider.setMinimum(Constant.ONE);
        jSlider.setMajorTickSpacing(Constant.ONE);
        jSlider.setPaintTicks(true);
        jSlider.setPaintTrack(true);
        jSlider.setPaintLabels(true);
        jSlider.setValue(Integer.valueOf(Constant.ZERO));
        if (sliderListener == null) {
            sliderListener = new SliderListener(jSlider);
        }
        if (sliderMouseListener == null) {
            sliderMouseListener = new SliderMouseListener();
        }
        jSlider.addChangeListener(sliderListener);
        jSlider.addMouseListener(sliderMouseListener);
        jSlider.addMouseMotionListener(sliderMouseListener);
        return jSlider;
    }

    private void setClipBoard(String result) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(result), null);
    }

    @Override
    public void repaint() {
        velocityPaint(positionList, Constant.TRUE);
    }

    public void setPixel(Integer pixel) {
        this.pixel = pixel;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setPositionList(List<Position> positionList) {
        this.positionList = positionList;
    }

    public Integer getPixel() {
        if (pixel == null) {
            pixel = Integer.valueOf(Constant.ONE);
        }
        return pixel;
    }

    public String getImagePath() {
        return imagePath;
    }

    private static class VelociraptorWindowCount {

        private static int velociraptorWindowCount = Integer.valueOf(Constant.ZERO);

    }

}
