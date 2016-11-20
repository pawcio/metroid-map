package pl.kwako.metroid_map.metroid_map;

import java.awt.Component;

/**
 * Mutable class
 * stores info about offset and zoom
 */
public class WindowCoordinateTranslator {

    private static final int MOVE_STEP = 16;

    private int zoom = 128;
    private int xOffset = -512;
    private int yOffset = -512;


    public void zoomIn() {

        if (zoom >= 512) {
            return;
        }

        ++zoom;

        xOffset = xOffset * (zoom) / (zoom - 1);
        yOffset = yOffset * (zoom) / (zoom - 1);
    }

    public void zoomOut() {

        if (zoom <= 16) {
            return;
        }

        --zoom;

        xOffset = xOffset * (zoom) / (zoom + 1);
        yOffset = yOffset * (zoom) / (zoom + 1);
    }

    public void moveLeft() {
        xOffset += MOVE_STEP;
    }

    public void moveRight() {
        xOffset -= MOVE_STEP;
    }

    public void moveUp() {
        yOffset += MOVE_STEP;
    }

    public void moveDown() {
        yOffset -= MOVE_STEP;
    }

    public int toWindowX(Component component, int x) {
        return xOffset
                + zoom * x
                + component.getWidth() / 2;
    }

    public int toWindowY(Component component, int y) {
        return yOffset
                + zoom * y
                + component.getHeight() / 2;
    }
}
