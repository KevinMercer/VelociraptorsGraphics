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
                    if (VelociraptorWindowCount.velociraptorWindowCount > Constant.ZERO) {
                        throw new RuntimeException(Constant.ONLY_ONE_INSTANCE_OF_WINDOW);
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
                JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), Constant.EMPTY_PIXEL_LIST);
            }
            return;
        }
        myGraphics2d.clearRect(Constant.ZERO, Constant.ZERO, Constant.VELOCIRAPTOR_WINDOW_WIDTH, Constant.VELOCIRAPTOR_WINDOW_HEIGHT);
        StringBuffer result = new StringBuffer(Constant.LEFT_BRACE);
        positionList.forEach(position -> {
            try {
                Color color = new Color(position.getColor());
                myGraphics2d.setColor(color);
                myGraphics2d.fillRect((position.getX() + (Constant.DRAW_OFFSET - (pixel * Constant.OFFSET_RATE))), (position.getY() + (Constant.DRAW_OFFSET - (pixel * Constant.OFFSET_RATE))), position.getWidth(), position.getHeight());
                result.append(position.getX())
                        .append(Constant.COMMA).append(position.getY())
                        .append(Constant.COMMA).append(position.getWidth())
                        .append(Constant.COMMA).append(position.getHeight())
                        .append(Constant.COMMA).append(position.getColor()).append(Constant.COMMA);
            } catch (Exception exception) {
                exception.printStackTrace();
                log.info(Constant.DRAW_IMAGE_ERROR);
                log.info(exception.getStackTrace().toString());
            }
        });
        result.append(Constant.RIGHT_BRACE);
        setClipBoard(result.toString());
        if (showMessageDialog) {
            StringBuffer messageBuffer = new StringBuffer();
            messageBuffer.append(Constant.COPY_READY).append(positionList.size()).append(Constant.COPY_DESC).append(result.length());
            JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), messageBuffer.toString());
        }
    }

    private void createWindow() {
        setLayout(new BorderLayout());
        setBounds((int) ((ScreenUtil.getScreenSize().width - Constant.VELOCIRAPTOR_WINDOW_WIDTH) * Constant.HALF_RATE), (int) ((ScreenUtil.getScreenSize().height - Constant.VELOCIRAPTOR_WINDOW_HEIGHT) * Constant.HALF_RATE), Constant.VELOCIRAPTOR_WINDOW_WIDTH, Constant.VELOCIRAPTOR_WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(Constant.TITLE);
        setResizable(Constant.FALSE);
        add(createPanel());
        add(createSlider(), BorderLayout.SOUTH);
    }

    private JPanel createPanel() {
        JPanel mainPanel = new JPanel();
        JMenuBar mainMenuBar = new JMenuBar();
        mainMenuBar.setVisible(Constant.TRUE);
        mainMenuBar.setBorderPainted(Constant.TRUE);
        JMenu mainMenu = new JMenu(Constant.OPTION);
        JMenuItem selectImage = new JMenuItem(Constant.SELECT_IMAGE);
        JMenuItem aboutMe = new JMenuItem(Constant.ABOUT_ME);
        mainMenu.add(selectImage);
        mainMenu.add(aboutMe);
        mainMenuBar.add(mainMenu);
        setJMenuBar(mainMenuBar);
        if (fileChooserListener == null) {
            fileChooserListener = new FileChooserListener();
        }
        if (aboutMeListener == null) {
            aboutMeListener = new AboutMeListener();
        }
        selectImage.addActionListener(fileChooserListener);
        aboutMe.addActionListener(aboutMeListener);
        return mainPanel;
    }

    private JSlider createSlider() {
        JSlider bottomSlider = new JSlider();
        bottomSlider.setMaximum(Constant.SLIDER_MAX_VALUE);
        bottomSlider.setMinimum(Constant.ONE);
        bottomSlider.setMajorTickSpacing(Constant.ONE);
        bottomSlider.setPaintTicks(Constant.TRUE);
        bottomSlider.setPaintTrack(Constant.TRUE);
        bottomSlider.setPaintLabels(Constant.TRUE);
        bottomSlider.setValue(Integer.valueOf(Constant.ZERO));
        if (sliderListener == null) {
            sliderListener = new SliderListener(bottomSlider);
        }
        if (sliderMouseListener == null) {
            sliderMouseListener = new SliderMouseListener();
        }
        bottomSlider.addChangeListener(sliderListener);
        bottomSlider.addMouseListener(sliderMouseListener);
        bottomSlider.addMouseMotionListener(sliderMouseListener);
        return bottomSlider;
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
