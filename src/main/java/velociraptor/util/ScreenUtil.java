package velociraptor.util;

import java.awt.*;

/**
 * @author Velociraptor
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
