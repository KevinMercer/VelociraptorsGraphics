package velociraptor.util;

import java.awt.*;

/**
 * @author Velociraptor
 * @apiNote 屏幕工具
 */
public class ScreenUtil {

    private static Dimension dimension;

    private ScreenUtil() {
    }

    public static Dimension getScreenSize() {
        if (dimension == null) {
            dimension = Toolkit.getDefaultToolkit().getScreenSize();
        }
        return dimension;
    }

}
