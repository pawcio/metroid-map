package pl.kwako.metroid_map.metroid_map;

import java.awt.Component;

public class ComponentRunner implements Runnable {

    private final Component component;

    public ComponentRunner(Component component) {
        this.component = component;
    }

    @Override
    public void run() {
        component.setVisible(true);
    }
}
