package pl.kwako.metroid_map.view;


import javax.inject.Singleton;
import java.awt.Component;

/**
 * Mutable class
 * stores info about offset and zoom
 */
@Singleton
class WindowCoordinateTranslator {

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

    public int toWindowX(Component component, int mapX) {
        return xOffset
                + zoom * mapX
                + component.getWidth() / 2;
    }

    public int toWindowY(Component component, int mapY) {
        return yOffset
                + zoom * mapY
                + component.getHeight() / 2;
    }

    public int toMapX(Component component, int windowX) {
        return (windowX - component.getWidth() / 2 - xOffset)
                / zoom;
    }

    public int toMapY(Component component, int windowY) {
        return (windowY - component.getHeight() / 2 - yOffset)
                / zoom;
    }
}
