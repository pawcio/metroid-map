package pl.kwako.metroid_map;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import pl.kwako.metroid_map.view.MapFrame;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Weld weld = new Weld();
        try (WeldContainer weldContainer = weld.initialize()) {
            MapFrame mapFrame = weldContainer.select(MapFrame.class).get();
            EventQueue.invokeLater(new ComponentRunner(mapFrame));
        }
    }
}
