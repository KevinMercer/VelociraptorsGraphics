package velociraptor.launcher;

import velociraptor.constant.Constant;
import velociraptor.window.VelociraptorWindow;

/**
 * @author Velociraptor
 * @apiNote 启动类
 */
public class Launcher implements Constant {
    public static void main(String[] args) {
        VelociraptorWindow velociraptorWindow = VelociraptorWindow.getInstance();
        velociraptorWindow.setVisible(TRUE);
    }
}
