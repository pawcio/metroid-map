package pl.kwako.metroid_map.metroid_map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
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
    private final BufferedImage mapImage;

    public MapPanel(WindowCoordinateTranslator windowCoordinateTranslator,
                    ImageCoordinateTranslator imageCoordinate) throws IOException {

        windowCoordinate = windowCoordinateTranslator;
        this.imageCoordinate = imageCoordinate;
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("MetroidCompleateMapBG.png")) {
            mapImage = ImageIO.read(resourceAsStream);
        }
    }

    private void drawMapBackgound(Graphics2D g2d) {
        for (int x = 0; x < ROOMS_SIZE_X; ++x) {
            for (int y = 0; y < ROOMS_SIZE_Y; ++y) {
                drawRoom(g2d, mapImage, x, y);
            }
        }
    }

    private void drawGrid(Graphics2D g2d) {
        for (int x = 1; x < ROOMS_SIZE_X; ++x) {
            g2d.drawLine(
                    windowCoordinate.toWindowX(this, x),
                    windowCoordinate.toWindowY(this, 0),
                    windowCoordinate.toWindowX(this, x),
                    windowCoordinate.toWindowY(this, ROOMS_SIZE_Y)
            );
        }

        for (int y = 1; y < ROOMS_SIZE_Y; ++y) {
            g2d.drawLine(
                    windowCoordinate.toWindowX(this, 0),
                    windowCoordinate.toWindowY(this, y),
                    windowCoordinate.toWindowX(this, ROOMS_SIZE_X),
                    windowCoordinate.toWindowY(this, y)
            );
        }
    }

    private void drawRoom(Graphics2D g2d, BufferedImage image, int x, int y) {
        g2d.drawImage(
                image,
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

        drawMapBackgound(g2d);
        drawGrid(g2d);

        g2d.dispose();
    }
}
