package pl.kwako.metroid_map;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.EventQueue;
import java.awt.event.KeyListener;

public class MetroidMap extends JFrame {

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
    public MetroidMap(WindowCoordinateTranslator windowCoordinateTranslator,
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

    public static void main(String[] args) {
        Weld weld = new Weld();
        try (WeldContainer weldContainer = weld.initialize()) {
            MetroidMap metroidMap = weldContainer.select(MetroidMap.class).get();
            EventQueue.invokeLater(new ComponentRunner(metroidMap));
        }
    }
}
