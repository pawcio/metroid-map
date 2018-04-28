package pl.kwako.metroid_map;

import java.awt.*;

class ComponentRunner implements Runnable {

    private final Frame component;

    ComponentRunner(Frame component) {
        this.component = component;
    }

    @Override
    public void run() {
        component.setExtendedState(Frame.MAXIMIZED_BOTH);
        component.setVisible(true);
    }
}
