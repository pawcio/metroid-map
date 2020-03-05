package pl.kwako.metroid_map.view

import java.awt.Component
import javax.inject.Singleton

private const val MOVE_STEP = 16

/**
 * Mutable class
 * stores info about offset and zoom
 */
@Singleton
class WindowCoordinateTranslator {

    private var zoom = 128
    private var xOffset = -512
    private var yOffset = -512

    fun zoomIn() {
        if (zoom >= 512) {
            return
        }
        ++zoom
        xOffset = xOffset * zoom / (zoom - 1)
        yOffset = yOffset * zoom / (zoom - 1)
    }

    fun zoomOut() {
        if (zoom <= 16) {
            return
        }
        --zoom
        xOffset = xOffset * zoom / (zoom + 1)
        yOffset = yOffset * zoom / (zoom + 1)
    }

    fun moveLeft() {
        xOffset += MOVE_STEP
    }

    fun moveRight() {
        xOffset -= MOVE_STEP
    }

    fun moveUp() {
        yOffset += MOVE_STEP
    }

    fun moveDown() {
        yOffset -= MOVE_STEP
    }

    fun toWindowX(component: Component, mapX: Int): Int {
        return (xOffset
                + zoom * mapX
                + component.width / 2)
    }

    fun toWindowY(component: Component, mapY: Int): Int {
        return (yOffset
                + zoom * mapY
                + component.height / 2)
    }

    fun toMapX(component: Component, windowX: Int): Int {
        return (windowX - component.width / 2 - xOffset) / zoom
    }

    fun toMapY(component: Component, windowY: Int): Int {
        return (windowY - component.height / 2 - yOffset) / zoom
    }
}