package velociraptor.function;

import lombok.extern.java.Log;
import velociraptor.constant.Constant;
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

    private static List<Position> positionList = new ArrayList<>();

    public static List<Position> getFidelityPositionList(int[][] bitmap, int pixel) {
        if (bitmap == null || pixel <= Constant.ZERO) {
            JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), "位图字节流传输过程中发生异常，导致字节流为空，请重试。");
            return null;
        }
        List<Position> fidelityPositionList = new ArrayList<>();
        for (int x = Integer.valueOf(Constant.ZERO); x < bitmap.length; x++) {
            for (int y = Integer.valueOf(Constant.ZERO); y < bitmap[x].length; y++) {
                Position position = new Position();
                position.setX(x);
                position.setY(y);
                position.setWidth(pixel);
                position.setHeight(pixel);
                position.setColor(bitmap[x][y]);
            }
        }
        return fidelityPositionList;
    }

    public static List<Position> getPositionList(int[][] bitmap, int pixel) {
        if (bitmap == null || pixel <= Constant.ZERO) {
            JOptionPane.showMessageDialog(VelociraptorWindow.getInstance(), "位图字节流传输过程中发生异常，导致字节流为空，请重试。");
            return null;
        }
        List<Position> verticalModel = verticalMode(bitmap, pixel);
        List<Position> horizontalModel = horizontalMode(bitmap, pixel);
        List<Position> mergedModel = verticalModel;
        if (verticalModel.size() > horizontalModel.size()) {
            mergedModel = horizontalModel;
        }
        positionList.clear();
        mergePosition(mergedModel);
        return positionList;
    }

    private static void mergePosition(List<Position> mergedModel) {
        if (mergedModel != null && mergedModel.size() > Constant.ZERO) {
            Position position = mergedModel.get(Constant.ZERO);
            List<Position> obsoleteModel = new ArrayList<>();
            obsoleteModel.add(position);
            for (int i = Integer.valueOf(Constant.ONE); i < mergedModel.size(); i++) {
                Position pool = mergedModel.get(i);
                if (position.getColor() == pool.getColor()) {
                    if (position.getX() == pool.getX()) {
                        if (position.getWidth() == pool.getWidth()) {
                            if ((position.getY() + position.getHeight()) == pool.getY()) {
                                position.setHeight(position.getHeight() + pool.getHeight());
                                obsoleteModel.add(pool);
                            } else if ((pool.getY() + pool.getHeight()) == position.getY()) {
                                position.setHeight(position.getHeight() + pool.getHeight());
                                position.setX(pool.getX());
                                obsoleteModel.add(pool);
                            }
                        }
                    } else if (position.getY() == pool.getY()) {
                        if (position.getHeight() == pool.getHeight()) {
                            if ((position.getX() + position.getWidth()) == pool.getX()) {
                                position.setWidth(position.getWidth() + pool.getWidth());
                                obsoleteModel.add(pool);
                            } else if ((pool.getX() + pool.getWidth()) == position.getX()) {
                                position.setWidth(position.getWidth() + pool.getWidth());
                                position.setY(pool.getY());
                                obsoleteModel.add(pool);
                            }
                        }
                    }
                }
            }
            positionList.add(position);
            mergedModel.removeAll(obsoleteModel);
            mergePosition(mergedModel);
        }
    }

    private static List<Position> verticalMode(int[][] bitmap, int pixel) {
        List<Position> verticalModel = new ArrayList<>();
        Position lastVerticalPosition = null;
        int lastVerticalColorInt = Integer.valueOf(Constant.ZERO);
        for (int x = Integer.valueOf(Constant.ZERO); x < bitmap.length; x++) {
            for (int y = Integer.valueOf(Constant.ZERO); y < bitmap[x].length; y++) {
                int theColorInt = Integer.valueOf(Constant.ZERO);
                try {
                    theColorInt = bitmap[x][y];
                } catch (Exception exception) {
                    exception.printStackTrace();
                    log.info(exception.getStackTrace().toString());
                }
                boolean concat = false;
                Color color = new Color(theColorInt);
                if (color.equals(Color.black)) {
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
                int posX = pixel + x * pixel;
                int posY = pixel + y * pixel;
                Position position;
                if (concat) {
                    position = lastVerticalPosition;
                    position.setHeight(position.getHeight() + pixel);
                    position.setColor(theColorInt);
                } else {
                    position = new Position();
                    position.setX(posX);
                    position.setY(posY);
                    position.setWidth(pixel);
                    position.setHeight(pixel);
                    position.setColor(theColorInt);
                    verticalModel.add(position);
                }
                lastVerticalColorInt = theColorInt;
                lastVerticalPosition = position;
            }
        }
        return verticalModel;
    }

    private static List<Position> horizontalMode(int[][] bitmap, int pixel) {
        List<Position> horizontalModel = new ArrayList<>();
        Position lastHorizontalPosition = null;
        int lastHorizontalColorInt = Integer.valueOf(Constant.ZERO);
        for (int y = Integer.valueOf(Constant.ZERO); y < bitmap.length; y++) {
            for (int x = Integer.valueOf(Constant.ZERO); x < bitmap[y].length; x++) {
                int theColorInt = Integer.valueOf(Constant.ZERO);
                try {
                    theColorInt = bitmap[x][y];
                } catch (Exception exception) {
                    exception.printStackTrace();
                    log.info(exception.getStackTrace().toString());
                }
                boolean concat = false;
                Color color = new Color(theColorInt);
                if (color.equals(Color.black)) {
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
                int posX = pixel + x * pixel;
                int posY = pixel + y * pixel;
                Position position;
                if (concat) {
                    position = lastHorizontalPosition;
                    position.setWidth(position.getWidth() + pixel);
                    position.setColor(theColorInt);
                } else {
                    position = new Position();
                    position.setX(posX);
                    position.setY(posY);
                    position.setWidth(pixel);
                    position.setHeight(pixel);
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
