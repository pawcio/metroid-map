package pl.kwako.metroid_map;

import org.jboss.weld.environment.se.Weld;
import pl.kwako.metroid_map.view.MapFrame;

import java.awt.EventQueue;

class Main {
    public static void main(String[] args) {
        var weld = new Weld();
        try (var weldContainer = weld.initialize()) {
            var mapFrame = weldContainer.select(MapFrame.class).get();
            EventQueue.invokeLater(new ComponentRunner(mapFrame));
        }
    }
}
