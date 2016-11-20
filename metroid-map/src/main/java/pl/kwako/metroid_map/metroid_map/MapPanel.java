package pl.kwako.metroid_map.metroid_map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static pl.kwako.metroid_map.metroid_map.Settings.ROOMS_SIZE_X;
import static pl.kwako.metroid_map.metroid_map.Settings.ROOMS_SIZE_Y;

public class MapPanel extends JPanel {

    private final WindowCoordinateTranslator windowCoordinate;
    private final ImageCoordinateTranslator imageCoordinate;
    private final MouseState mouseState;
    private final BufferedImage mapImage;

    public MapPanel(WindowCoordinateTranslator windowCoordinateTranslator,
                    ImageCoordinateTranslator imageCoordinate, MouseState mouseState) throws IOException {
        this.mouseState = mouseState;

        setBackground(new Color(32, 32, 32));

        windowCoordinate = windowCoordinateTranslator;
        this.imageCoordinate = imageCoordinate;
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("MetroidCompleateMapBG.png")) {
            mapImage = ImageIO.read(resourceAsStream);
        }
    }

    private void drawMapBackgound(Graphics2D g2d) {
        for (int x = 0; x < ROOMS_SIZE_X; ++x) {
            for (int y = 0; y < ROOMS_SIZE_Y; ++y) {
                drawRoom(g2d, x, y);
            }
        }
    }

    private void drawGrid(Graphics2D g2d) {
        for (int x = 0; x <= ROOMS_SIZE_X; ++x) {
            g2d.drawLine(
                    windowCoordinate.toWindowX(this, x),
                    0,
                    windowCoordinate.toWindowX(this, x),
                    getHeight()
            );
        }

        for (int y = 0; y <= ROOMS_SIZE_Y; ++y) {
            g2d.drawLine(
                    0,
                    windowCoordinate.toWindowY(this, y),
                    getWidth(),
                    windowCoordinate.toWindowY(this, y)
            );
        }
    }

    private void drawRoom(Graphics2D g2d, int x, int y) {
        g2d.drawImage(
                mapImage,
                windowCoordinate.toWindowX(this, x), // x of 1st corner of room in a window
                windowCoordinate.toWindowY(this, y), // y of 1st conrner of room in a window
                windowCoordinate.toWindowX(this, x + 1), // x of 2nd corner of room in a window
                windowCoordinate.toWindowY(this, y + 1), // y of 2nd conrner of room in a window

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
                windowCoordinate.zoomOut();
            }
        }

        if (wheelRotation < 0) {
            for (int i = 0; i > wheelRotation; --i) {
                windowCoordinate.zoomIn();
            }
        }
    }

    private void drawCursor(Graphics2D g2d) {

        int mapX = windowCoordinate.toMapX(this, mouseState.getX());

        if (mapX < 0) {
            mapX = 0;
        }

        if (mapX >= ROOMS_SIZE_X) {
            mapX = ROOMS_SIZE_X - 1;
        }

        int x1 = windowCoordinate.toWindowX(this, mapX);
        int x2 = windowCoordinate.toWindowX(this, 1 + mapX);

        int mapY = windowCoordinate.toMapY(this, mouseState.getY());

        if (mapY < 0) {
            mapY = 0;
        }

        if (mapY >= ROOMS_SIZE_Y) {
            mapY = ROOMS_SIZE_Y - 1;
        }

        int y1 = windowCoordinate.toWindowY(this, mapY);
        int y2 = windowCoordinate.toWindowY(this, 1 + mapY);

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
