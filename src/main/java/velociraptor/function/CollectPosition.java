package velociraptor.function;

import lombok.extern.java.Log;
import velociraptor.window.VelociraptorWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Velociraptor
 */
@Log
public class CollectPosition {

    private static List<Position> positionList;

    public static List<Position> getPositionList(int[][] bitmap) {
        if (bitmap == null) {
            JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), "位图字节流传输过程中发生异常，导致字节流为空，请重试。");
            return null;
        }
        List<Position> verticalModel = verticalMode(bitmap);
        List<Position> horizontalModel = horizontalMode(bitmap);

        if (verticalModel.size() > horizontalModel.size()) {
            return horizontalModel;
        }

        return verticalModel;
    }

    private static List<Position> verticalMode(int[][] bitmap) {
        List<Position> verticalModel = new ArrayList<>();
        Position lastVerticalPosition = null;
        int lastVerticalColorInt = 0;
        for (int x = 0; x < bitmap.length; x++) {
            for (int y = 0; y < bitmap[x].length; y++) {
                int theColorInt = 0;
                try {
                    theColorInt = bitmap[x][y];
                } catch (Exception exception) {
                    exception.printStackTrace();
                    log.info(exception.getStackTrace().toString());
                }
                boolean concat = false;
                Color color = new Color(theColorInt);
                if (color.equals(Color.white) || color.equals(Color.black)) {
                    lastVerticalColorInt = theColorInt;
                    lastVerticalPosition = null;
                    continue;
                }
                if (lastVerticalColorInt == theColorInt) {
                    concat = true;
                } else {
                    concat = false;
                }
                if (lastVerticalPosition == null) {
                    lastVerticalPosition = new Position();
                }
                int posX = 1 + x * 1;
                int posY = 1 + y * 1;
                Position position;
                if (concat) {
                    position = lastVerticalPosition;
                    position.setHeight(position.getHeight() + 1);
                    position.setColor(theColorInt);
                } else {
                    position = new Position();
                    position.setX(posX);
                    position.setY(posY);
                    position.setWidth(1);
                    position.setHeight(1);
                    position.setColor(theColorInt);
                    verticalModel.add(position);
                }
                lastVerticalColorInt = theColorInt;
                lastVerticalPosition = position;
            }
        }
        return verticalModel;
    }

    private static List<Position> horizontalMode(int[][] bitmap) {
        List<Position> horizontalModel = new ArrayList<>();
        Position lastHorizontalPosition = null;
        int lastHorizontalColorInt = 0;
        for (int y = 0; y < bitmap.length; y++) {
            for (int x = 0; x < bitmap[y].length; x++) {
                int theColorInt = 0;
                try {
                    theColorInt = bitmap[x][y];
                } catch (Exception exception) {
                    exception.printStackTrace();
                    log.info(exception.getStackTrace().toString());
                }
                boolean concat = false;
                Color color = new Color(theColorInt);
                if (color.equals(Color.white) || color.equals(Color.black)) {
                    lastHorizontalColorInt = theColorInt;
                    lastHorizontalPosition = null;
                    continue;
                }
                if (lastHorizontalColorInt == theColorInt) {
                    concat = true;
                } else {
                    concat = false;
                }
                if (lastHorizontalPosition == null) {
                    lastHorizontalPosition = new Position();
                }
                int posX = 1 + x * 1;
                int posY = 1 + y * 1;
                Position position;
                if (concat) {
                    position = lastHorizontalPosition;
                    position.setWidth(position.getWidth() + 1);
                    position.setColor(theColorInt);
                } else {
                    position = new Position();
                    position.setX(posX);
                    position.setY(posY);
                    position.setWidth(1);
                    position.setHeight(1);
                    position.setColor(theColorInt);
                    horizontalModel.add(position);
                }
                lastHorizontalColorInt = theColorInt;
                lastHorizontalPosition = position;
            }
        }
        return horizontalModel;
    }

}
