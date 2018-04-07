package pl.kwako.metroid_map.old_view;

import pl.kwako.metroid_map.Settings;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.*;
import java.awt.event.KeyListener;

public class MapFrame extends JFrame {

    private final WindowCoordinateTranslator windowCoordinateTranslator;
    private final MapPanel mapPanel;

    @PostConstruct
    public void setupListeners() {
        KeyListener keyPressListener = new KeyPressListener(windowCoordinateTranslator, this);
        addKeyListener(keyPressListener);

        MouseOverMapListener mouseOverMapListener = new MouseOverMapListener(this);
        addMouseMotionListener(mouseOverMapListener);
        addMouseWheelListener(mouseOverMapListener);
        mapPanel.setMouseState(mouseOverMapListener);
    }

    @Inject
    public MapFrame(WindowCoordinateTranslator windowCoordinateTranslator,
                    MapPanel mapPanel,
                    Settings settings) {
        super("Metroid Map");
        this.windowCoordinateTranslator = windowCoordinateTranslator;
        this.mapPanel = mapPanel;

        add(mapPanel);

        setTitle("Metroid Map");
        setSize(settings.defaultWindowWidth(),
                settings.defaultWindowHeight());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
