package pl.kwako.metroid_map

import org.jboss.weld.environment.se.Weld
import pl.kwako.metroid_map.view.MapFrame
import javax.swing.SwingUtilities

fun main() {
    Weld().initialize().use {
        val mapFrame = it.select(MapFrame::class.java).get()
        SwingUtilities.invokeLater(ComponentRunner(mapFrame))
    }
}
