package pl.kwako.metroid_map;

import javax.swing.JFrame;
import java.awt.EventQueue;
import java.io.IOException;

public class MetroidMap extends JFrame {

    private MetroidMap() throws IOException {

        WindowCoordinateTranslator windowCoordinateTranslator = new WindowCoordinateTranslator();

        MouseOverMapListener mouseOverMapListener = new MouseOverMapListener(this);

        MapPanel mapPanel = new MapPanel(
                windowCoordinateTranslator,
                new ImageCoordinateTranslator(), mouseOverMapListener);

        KeyPressListener keyPressListener = new KeyPressListener(
                windowCoordinateTranslator,
                this
        );

        add(mapPanel);

        addKeyListener(keyPressListener);
        addMouseMotionListener(mouseOverMapListener);
        addMouseWheelListener(mouseOverMapListener);

        setTitle("Metroid Map");
        setSize(
                Settings.DEFAULT_WINDOW_WIDTH,
                Settings.DEFAULT_WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws IOException {
        MetroidMap metroidMap = new MetroidMap();
        EventQueue.invokeLater(new ComponentRunner(metroidMap));
    }
}
