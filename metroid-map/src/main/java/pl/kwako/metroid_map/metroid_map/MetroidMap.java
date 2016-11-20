package pl.kwako.metroid_map.metroid_map;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;

import static pl.kwako.metroid_map.metroid_map.Settings.DEFAULT_WINDOW_HEIGHT;
import static pl.kwako.metroid_map.metroid_map.Settings.DEFAULT_WINDOW_WIDTH;

public class MetroidMap extends JFrame {

    private MetroidMap() throws IOException {

        WindowCoordinateTranslator windowCoordinateTranslator = new WindowCoordinateTranslator();

        MapPanel mapPanel = new MapPanel(
                windowCoordinateTranslator,
                new ImageCoordinateTranslator());

        KeyPressListener keyPressListener = new KeyPressListener(
                windowCoordinateTranslator,
                this
        );

        add(mapPanel);

        addKeyListener(keyPressListener);

        setTitle("Metroid Map");
        setSize(
                DEFAULT_WINDOW_WIDTH,
                DEFAULT_WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws IOException {
        MetroidMap metroidMap = new MetroidMap();
        EventQueue.invokeLater(new ComponentRunner(metroidMap));
    }
}
