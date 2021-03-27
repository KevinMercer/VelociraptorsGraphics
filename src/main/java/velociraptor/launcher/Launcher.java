package velociraptor.launcher;

import velociraptor.window.VelociraptorWindow;

/**
 * @author Velociraptor
 */
public class Launcher {
    public static void main(String[] args) {
        VelociraptorWindow velociraptorWindow = VelociraptorWindow.getInstance();
        velociraptorWindow.setVisible(true);
    }
}
