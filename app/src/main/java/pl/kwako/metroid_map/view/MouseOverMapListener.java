package pl.kwako.metroid_map.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

class MouseOverMapListener extends MouseAdapter implements MouseState {

    private int x;
    private int y;
    private int wheelRotation;
    private final Component component;

    public MouseOverMapListener(Component component) {
        this.component = component;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        component.revalidate();
        component.repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        wheelRotation = e.getWheelRotation();

        component.revalidate();
        component.repaint();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int popWheelRotation() {
        var lastRotation = wheelRotation;

        wheelRotation = 0;

        return lastRotation;
    }
}
