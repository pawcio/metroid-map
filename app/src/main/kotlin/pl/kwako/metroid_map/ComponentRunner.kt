package pl.kwako.metroid_map

import java.awt.Frame

class ComponentRunner(private val component: Frame) : Runnable {
    override fun run() {
        component.extendedState = Frame.MAXIMIZED_BOTH
        component.isVisible = true
    }
}