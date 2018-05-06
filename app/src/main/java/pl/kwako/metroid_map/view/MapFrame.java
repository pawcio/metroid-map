package pl.kwako.metroid_map.view;

import pl.kwako.metroid_map.Settings;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.event.KeyListener;

public class MapFrame extends JFrame {

    private static final String TITLE = "Metroid Map";
    private final WindowCoordinateTranslator windowCoordinateTranslator;
    private final MapPanel mapPanel;

    @PostConstruct
    public void setupListeners() {
        KeyListener keyPressListener = new KeyPressListener(windowCoordinateTranslator, this);
        addKeyListener(keyPressListener);

        var mouseOverMapListener = new MouseOverMapListener(this);
        addMouseMotionListener(mouseOverMapListener);
        addMouseWheelListener(mouseOverMapListener);
        mapPanel.setMouseState(mouseOverMapListener);
    }

    @Inject
    public MapFrame(WindowCoordinateTranslator windowCoordinateTranslator,
                    MapPanel mapPanel,
                    Settings settings) {
        super(TITLE);
        this.windowCoordinateTranslator = windowCoordinateTranslator;
        this.mapPanel = mapPanel;

        add(mapPanel);

        setTitle(TITLE);
        setSize(settings.defaultWindowWidth(),
                settings.defaultWindowHeight());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
