package pl.kwako.metroid_map.metroid_map;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import static java.awt.event.KeyEvent.*;

public class KeyPressListener extends KeyAdapter {

    private final WindowCoordinateTranslator windowCoordinateTranslator;
    private final Component component;

    /**
     * keeps track of currently active actions
     */
    private int actionMask;

    private static final int MOVE_UP = 0b1;
    private static final int MOVE_DOWN = 0b10;
    private static final int MOVE_LEFT = 0b100;
    private static final int MOVE_RIGHT = 0b1000;
    private static final int ZOOM_IN = 0b10000;
    private static final int ZOOM_OUT = 0b100000;

    private Map<Integer, Integer> keyToActionMapping = new HashMap<>();

    public KeyPressListener(WindowCoordinateTranslator windowCoordinateTranslator, Component component) {
        this.windowCoordinateTranslator = windowCoordinateTranslator;
        this.component = component;

        // TODO: read and write settings using file
        keyToActionMapping.put(VK_UP, MOVE_UP);
        keyToActionMapping.put(VK_W, MOVE_UP);

        keyToActionMapping.put(VK_DOWN, MOVE_DOWN);
        keyToActionMapping.put(VK_S, MOVE_DOWN);

        keyToActionMapping.put(VK_LEFT, MOVE_LEFT);
        keyToActionMapping.put(VK_A, MOVE_LEFT);

        keyToActionMapping.put(VK_RIGHT, MOVE_RIGHT);
        keyToActionMapping.put(VK_D, MOVE_RIGHT);

        keyToActionMapping.put(VK_PAGE_UP, ZOOM_IN);
        keyToActionMapping.put(VK_E, ZOOM_IN);

        keyToActionMapping.put(VK_PAGE_DOWN, ZOOM_OUT);
        keyToActionMapping.put(VK_Q, ZOOM_OUT);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        Integer action = keyToActionMapping.get(e.getKeyCode());

        if (action != null) {
            setAction(action);
        }

        processActions();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Integer action = keyToActionMapping.get(e.getKeyCode());

        if (action != null) {
            unsetAction(action);
        }
    }

    private void setAction(int action) {
        actionMask |= action;
    }

    private void unsetAction(int action) {
        actionMask &= ~action;
    }

    private void processActions() {
        if (actionSet(MOVE_UP)) {
            windowCoordinateTranslator.moveUp();
        }

        if (actionSet(MOVE_DOWN)) {
            windowCoordinateTranslator.moveDown();
        }

        if (actionSet(MOVE_LEFT)) {
            windowCoordinateTranslator.moveLeft();
        }

        if (actionSet(MOVE_RIGHT)) {
            windowCoordinateTranslator.moveRight();
        }

        if (actionSet(ZOOM_IN)) {
            windowCoordinateTranslator.zoomIn();
        }

        if (actionSet(ZOOM_OUT)) {
            windowCoordinateTranslator.zoomOut();
        }

        component.revalidate();
        component.repaint();
    }

    private boolean actionSet(int action) {
        return (actionMask & action) != 0;
    }
}
