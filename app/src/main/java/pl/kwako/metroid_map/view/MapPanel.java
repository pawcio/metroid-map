package pl.kwako.metroid_map.view;

import pl.kwako.metroid_map.Settings;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;

class MapPanel extends JPanel {

    private final WindowCoordinateTranslator windowCoordinateTranslator;
    private final ImageCoordinateTranslator imageCoordinate;
    private final Settings settings;
    private final BufferedImage mapImage;
    private MouseState mouseState;

    @Inject
    MapPanel(WindowCoordinateTranslator windowCoordinateTranslator,
             ImageCoordinateTranslator imageCoordinate, Settings settings) {

        this.windowCoordinateTranslator = windowCoordinateTranslator;
        this.imageCoordinate = imageCoordinate;
        this.settings = settings;

        setBackground(new Color(32, 32, 32));

        // TODO: implement loading map definition from file.
        try (var resourceAsStream = getClass().getClassLoader().getResourceAsStream("MetroidCompleateMapBG.png")) {
            mapImage = ImageIO.read(resourceAsStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void setMouseState(MouseState mouseState) {
        this.mouseState = mouseState;
    }

    private void drawMapBackgound(Graphics2D g2d) {
        for (var x = 0; x < settings.roomsSizeX(); ++x) {
            for (var y = 0; y < settings.roomsSizeY(); ++y) {
                drawRoom(g2d, x, y);
            }
        }
    }

    private void drawGrid(Graphics2D g2d) {
        for (var x = 0; x <= settings.roomsSizeX(); ++x) {
            g2d.drawLine(
                    windowCoordinateTranslator.toWindowX(this, x),
                    0,
                    windowCoordinateTranslator.toWindowX(this, x),
                    getHeight()
            );
        }

        for (var y = 0; y <= settings.roomsSizeY(); ++y) {
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

        var g2d = (Graphics2D) g.create();

        updateZoom();
        drawMapBackgound(g2d);
        drawGrid(g2d);
        drawCursor(g2d);

        g2d.dispose();
    }

    private void updateZoom() {
        var wheelRotation = mouseState.popWheelRotation() * 2;

        if (wheelRotation > 0) {
            for (var i = 0; i < wheelRotation; ++i) {
                windowCoordinateTranslator.zoomOut();
            }
        }

        if (wheelRotation < 0) {
            for (var i = 0; i > wheelRotation; --i) {
                windowCoordinateTranslator.zoomIn();
            }
        }
    }

    private void drawCursor(Graphics2D g2d) {

        var mapX = windowCoordinateTranslator.toMapX(this, mouseState.getX());

        if (mapX < 0) {
            mapX = 0;
        }

        if (mapX >= settings.roomsSizeX()) {
            mapX = settings.roomsSizeX() - 1;
        }

        var x1 = windowCoordinateTranslator.toWindowX(this, mapX);
        var x2 = windowCoordinateTranslator.toWindowX(this, 1 + mapX);

        var mapY = windowCoordinateTranslator.toMapY(this, mouseState.getY());

        if (mapY < 0) {
            mapY = 0;
        }

        if (mapY >= settings.roomsSizeY()) {
            mapY = settings.roomsSizeY() - 1;
        }

        var y1 = windowCoordinateTranslator.toWindowY(this, mapY);
        var y2 = windowCoordinateTranslator.toWindowY(this, 1 + mapY);

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
