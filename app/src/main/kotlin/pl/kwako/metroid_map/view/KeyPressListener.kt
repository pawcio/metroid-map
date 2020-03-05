package pl.kwako.metroid_map.view

import java.awt.Component
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.VK_A
import java.awt.event.KeyEvent.VK_D
import java.awt.event.KeyEvent.VK_DOWN
import java.awt.event.KeyEvent.VK_E
import java.awt.event.KeyEvent.VK_LEFT
import java.awt.event.KeyEvent.VK_PAGE_DOWN
import java.awt.event.KeyEvent.VK_PAGE_UP
import java.awt.event.KeyEvent.VK_Q
import java.awt.event.KeyEvent.VK_RIGHT
import java.awt.event.KeyEvent.VK_S
import java.awt.event.KeyEvent.VK_UP
import java.awt.event.KeyEvent.VK_W
import javax.swing.SwingUtilities

private const val MOVE_UP = 0b1
private const val MOVE_DOWN = 0b10
private const val MOVE_LEFT = 0b100
private const val MOVE_RIGHT = 0b1000
private const val ZOOM_IN = 0b10000
private const val ZOOM_OUT = 0b100000

private val KEY_TO_ACTION_MAPPING = mapOf(
        VK_UP to MOVE_UP,
        VK_W to MOVE_UP,
        VK_DOWN to MOVE_DOWN,
        VK_S to MOVE_DOWN,
        VK_LEFT to MOVE_LEFT,
        VK_A to MOVE_LEFT,
        VK_RIGHT to MOVE_RIGHT,
        VK_D to MOVE_RIGHT,
        VK_PAGE_UP to ZOOM_IN,
        VK_E to ZOOM_IN,
        VK_PAGE_DOWN to ZOOM_OUT,
        VK_Q to ZOOM_OUT
)

private val actionToWindowOperation = mapOf(
        MOVE_UP to WindowCoordinateTranslator::moveUp,
        MOVE_DOWN to WindowCoordinateTranslator::moveDown,
        MOVE_LEFT to WindowCoordinateTranslator::moveLeft,
        MOVE_RIGHT to WindowCoordinateTranslator::moveRight,
        ZOOM_IN to WindowCoordinateTranslator::zoomIn,
        ZOOM_OUT to WindowCoordinateTranslator::zoomOut
)

class KeyPressListener(private val component: Component,
                       private val windowCoordinateTranslator: WindowCoordinateTranslator) : KeyAdapter() {

    private var actionMask: Int = 0

    override fun keyPressed(e: KeyEvent) {
        KEY_TO_ACTION_MAPPING[e.keyCode]?.let {
            setAction(it)
            processActions()
        }
    }

    override fun keyReleased(e: KeyEvent) {
        KEY_TO_ACTION_MAPPING[e.keyCode]?.let {
            unsetAction(it)
            processActions()
        }
    }

    private fun setAction(action: Int) {
        actionMask = actionMask.or(action)
    }

    private fun unsetAction(action: Int) {
        actionMask = actionMask.and(action.inv())
    }

    private fun processActions() {
        actionToWindowOperation.filterKeys { actionSet(it) }
                .forEach { (_, operation) -> operation(windowCoordinateTranslator) }

        SwingUtilities.invokeLater {
            component.revalidate()
            component.repaint()
        }
    }

    private fun actionSet(action: Int): Boolean {
        return actionMask.and(action) != 0
    }
}