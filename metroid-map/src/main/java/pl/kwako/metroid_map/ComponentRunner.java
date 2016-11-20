package pl.kwako.metroid_map;

import java.awt.Frame;

public class ComponentRunner implements Runnable {

    private final Frame component;

    public ComponentRunner(Frame component) {
        this.component = component;
    }

    @Override
    public void run() {
        component.setExtendedState(Frame.MAXIMIZED_BOTH);
        component.setVisible(true);
    }
}
