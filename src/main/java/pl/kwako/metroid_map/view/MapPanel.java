package pl.kwako.metroid_map.view;

import pl.kwako.metroid_map.Settings;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class MapPanel extends JPanel {

    private final WindowCoordinateTranslator windowCoordinateTranslator;
    private final ImageCoordinateTranslator imageCoordinate;
    private final Settings settings;
    private final BufferedImage mapImage;
    private MouseState mouseState;

    @Inject
    public MapPanel(WindowCoordinateTranslator windowCoordinateTranslator,
                    ImageCoordinateTranslator imageCoordinate, Settings settings) throws IOException {

        this.windowCoordinateTranslator = windowCoordinateTranslator;
        this.imageCoordinate = imageCoordinate;
        this.settings = settings;

        setBackground(new Color(32, 32, 32));

        // TODO: implement loading map definition from file.
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("MetroidCompleateMapBG.png")) {
            mapImage = ImageIO.read(resourceAsStream);
        }
    }

    public void setMouseState(MouseState mouseState) {
        this.mouseState = mouseState;
    }

    private void drawMapBackgound(Graphics2D g2d) {
        for (int x = 0; x < settings.roomsSizeX(); ++x) {
            for (int y = 0; y < settings.roomsSizeY(); ++y) {
                drawRoom(g2d, x, y);
            }
        }
    }

    private void drawGrid(Graphics2D g2d) {
        for (int x = 0; x <= settings.roomsSizeX(); ++x) {
            g2d.drawLine(
                    windowCoordinateTranslator.toWindowX(this, x),
                    0,
                    windowCoordinateTranslator.toWindowX(this, x),
                    getHeight()
            );
        }

        for (int y = 0; y <= settings.roomsSizeY(); ++y) {
            g2d.drawLine(
                    0,
                    windowCoordinateTranslator.toWindowY(this, y),
                    getWidth(),
                    windowCoordinateTranslator.toWindowY(this, y)
            );
        }
    }

    private void drawRoom(Graphics2D g2d, int x, int y) {
        g2d.drawImage(
                mapImage,
                windowCoordinateTranslator.toWindowX(this, x), // x of 1st corner of room in a window
                windowCoordinateTranslator.toWindowY(this, y), // y of 1st conrner of room in a window
                windowCoordinateTranslator.toWindowX(this, x + 1), // x of 2nd corner of room in a window
                windowCoordinateTranslator.toWindowY(this, y + 1), // y of 2nd conrner of room in a window

                imageCoordinate.toImageX(x), // x of 1st corner of room in image
                imageCoordinate.toImageY(y), // y of 1st conrner of room in image
                imageCoordinate.toImageX(x + 1), // x of 2nd corner of room in image
                imageCoordinate.toImageY(y + 1), // y of 2nd conrner of room in image
                this
        );
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        updateZoom();
        drawMapBackgound(g2d);
        drawGrid(g2d);
        drawCursor(g2d);

        g2d.dispose();
    }

    private void updateZoom() {
        int wheelRotation = mouseState.popWheelRotation() * 2;

        if (wheelRotation > 0) {
            for (int i = 0; i < wheelRotation; ++i) {
                windowCoordinateTranslator.zoomOut();
            }
        }

        if (wheelRotation < 0) {
            for (int i = 0; i > wheelRotation; --i) {
                windowCoordinateTranslator.zoomIn();
            }
        }
    }

    private void drawCursor(Graphics2D g2d) {

        int mapX = windowCoordinateTranslator.toMapX(this, mouseState.getX());

        if (mapX < 0) {
            mapX = 0;
        }

        if (mapX >= settings.roomsSizeX()) {
            mapX = settings.roomsSizeX() - 1;
        }

        int x1 = windowCoordinateTranslator.toWindowX(this, mapX);
        int x2 = windowCoordinateTranslator.toWindowX(this, 1 + mapX);

        int mapY = windowCoordinateTranslator.toMapY(this, mouseState.getY());

        if (mapY < 0) {
            mapY = 0;
        }

        if (mapY >= settings.roomsSizeY()) {
            mapY = settings.roomsSizeY() - 1;
        }

        int y1 = windowCoordinateTranslator.toWindowY(this, mapY);
        int y2 = windowCoordinateTranslator.toWindowY(this, 1 + mapY);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        g2d.drawRect(
                x1,
                y1,
                Math.abs(x2 - x1),
                Math.abs(y2 - y1));

        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        g2d.drawRect(
                x1,
                y1,
                Math.abs(x2 - x1),
                Math.abs(y2 - y1));

        g2d.setColor(Color.PINK);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        g2d.drawRect(
                x1,
                y1,
                Math.abs(x2 - x1),
                Math.abs(y2 - y1));

    }
}
