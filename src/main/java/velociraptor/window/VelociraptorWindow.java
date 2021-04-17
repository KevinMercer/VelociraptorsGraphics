package velociraptor.window;

import lombok.extern.java.Log;
import velociraptor.constant.Constant;
import velociraptor.function.Position;
import velociraptor.util.ScreenUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.List;

/**
 * @author Velociraptor
 * @apiNote 主窗口
 */
@Log
public class VelociraptorWindow extends JFrame implements Constant {

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
                    if (VelociraptorWindowCount.velociraptorWindowCount > ZERO) {
                        throw new RuntimeException(ONLY_ONE_INSTANCE_OF_WINDOW);
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
                JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), EMPTY_PIXEL_LIST);
            }
            return;
        }
        myGraphics2d.clearRect(ZERO, ZERO, VELOCIRAPTOR_WINDOW_WIDTH, VELOCIRAPTOR_WINDOW_HEIGHT);
        StringBuffer result = new StringBuffer(LEFT_BRACE);
        positionList.forEach(position -> {
            try {
                Color color = new Color(position.getColor());
                myGraphics2d.setColor(color);
                myGraphics2d.fillRect((position.getX() + (DRAW_OFFSET - (pixel * OFFSET_RATE))), (position.getY() + (DRAW_OFFSET - (pixel * OFFSET_RATE))), position.getWidth(), position.getHeight());
                result.append(position.getX())
                        .append(COMMA).append(position.getY())
                        .append(COMMA).append(position.getWidth())
                        .append(COMMA).append(position.getHeight())
                        .append(COMMA).append(position.getColor()).append(COMMA);
            } catch (Exception exception) {
                exception.printStackTrace();
                log.info(DRAW_IMAGE_ERROR);
                log.info(Arrays.toString(exception.getStackTrace()));
            }
        });
        result.append(RIGHT_BRACE);
        setClipBoard(result.toString());
        if (showMessageDialog) {
            JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), COPY_READY + positionList.size() + COPY_DESC + result.length());
        }
    }

    private void createWindow() {
        setLayout(new BorderLayout());
        setBounds((int) ((ScreenUtil.getScreenSize().width - VELOCIRAPTOR_WINDOW_WIDTH) * HALF_RATE), (int) ((ScreenUtil.getScreenSize().height - VELOCIRAPTOR_WINDOW_HEIGHT) * HALF_RATE), VELOCIRAPTOR_WINDOW_WIDTH, VELOCIRAPTOR_WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(TITLE);
        setResizable(FALSE);
        add(createPanel());
        add(createSlider(), BorderLayout.SOUTH);
    }

    private JPanel createPanel() {
        JPanel mainPanel = new JPanel();
        JMenuBar mainMenuBar = new JMenuBar();
        mainMenuBar.setVisible(TRUE);
        mainMenuBar.setBorderPainted(TRUE);
        JMenu mainMenu = new JMenu(OPTION);
        JMenuItem selectImage = new JMenuItem(SELECT_IMAGE);
        JMenuItem aboutMe = new JMenuItem(ABOUT_ME);
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
        bottomSlider.setMaximum(SLIDER_MAX_VALUE);
        bottomSlider.setMinimum(ONE);
        bottomSlider.setMajorTickSpacing(ONE);
        bottomSlider.setPaintTicks(TRUE);
        bottomSlider.setPaintTrack(TRUE);
        bottomSlider.setPaintLabels(TRUE);
        bottomSlider.setValue(ZERO);
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
        velocityPaint(positionList, TRUE);
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
            pixel = ONE;
        }
        return pixel;
    }

    public String getImagePath() {
        return imagePath;
    }

    private static class VelociraptorWindowCount implements Constant {
        private static int velociraptorWindowCount = ZERO;
    }

}
