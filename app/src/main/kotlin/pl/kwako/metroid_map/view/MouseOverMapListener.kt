package pl.kwako.metroid_map.view

import java.awt.Component
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent
import javax.swing.SwingUtilities

class MouseOverMapListener(private val component: Component) : MouseAdapter(), MouseState {

    private var x: Int = 0
    private var y: Int = 0
    private var wheelRotation: Int = 0

    override fun getX(): Int = x

    override fun getY(): Int = y

    override fun popWheelRotation(): Int {
        val lastRotation = wheelRotation
        wheelRotation = 0
        return lastRotation
    }

    override fun mouseMoved(e: MouseEvent) {
        x = e.x
        y = e.y
        SwingUtilities.invokeLater {
            component.revalidate()
            component.repaint()
        }
    }

    override fun mouseWheelMoved(e: MouseWheelEvent) {
        wheelRotation = e.wheelRotation
        SwingUtilities.invokeLater {
            component.revalidate()
            component.repaint()
        }
    }
}
