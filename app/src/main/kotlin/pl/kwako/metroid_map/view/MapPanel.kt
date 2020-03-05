package pl.kwako.metroid_map.view

import pl.kwako.metroid_map.Settings
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.inject.Inject
import javax.swing.JPanel
import kotlin.math.abs

class MapPanel @Inject constructor(private val windowCoordinateTranslator: WindowCoordinateTranslator,
                                   private val imageCoordinate: ImageCoordinateTranslator,
                                   private val settings: Settings) : JPanel() {

    init {
        background = Color(32, 32, 32)
    }

    private lateinit var mouseState: MouseState
    private lateinit var mapImage: BufferedImage


    fun setMouseState(mouseState: MouseState) {
        this.mouseState = mouseState
    }

    fun setMapImage(mapImage: BufferedImage) {
        this.mapImage = mapImage
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2d = g.create() as Graphics2D
        updateZoom()
        drawMapBackground(g2d)
        drawGrid(g2d)
        drawCursor(g2d)

        g2d.dispose()
    }


    private fun updateZoom() {
        val wheelRotation = mouseState.popWheelRotation()
        if (wheelRotation > 0) {
            repeat(wheelRotation) {
                windowCoordinateTranslator.zoomOut()
            }
        }
        if (wheelRotation < 0) {
            repeat(-wheelRotation) {
                windowCoordinateTranslator.zoomIn()
            }
        }
    }

    private fun drawMapBackground(g2d: Graphics2D) {
        for (x in settings.roomRangeX()) {
            for (y in settings.roomRangeY()) {
                drawRoom(g2d, x, y)
            }
        }
    }

    private fun drawRoom(g2d: Graphics2D, x: Int, y: Int) = g2d.drawImage(
            mapImage,
            windowCoordinateTranslator.toWindowX(this, x),  // x of 1st corner of room in a window
            windowCoordinateTranslator.toWindowY(this, y),  // y of 1st corner of room in a window
            windowCoordinateTranslator.toWindowX(this, x + 1),  // x of 2nd corner of room in a window
            windowCoordinateTranslator.toWindowY(this, y + 1),  // y of 2nd corner of room in a window
            imageCoordinate.toImageX(x),  // x of 1st corner of room in image
            imageCoordinate.toImageY(y),  // y of 1st corner of room in image
            imageCoordinate.toImageX(x + 1),  // x of 2nd corner of room in image
            imageCoordinate.toImageY(y + 1),  // y of 2nd corner of room in image
            this
    )

    private fun drawGrid(g2d: Graphics2D) {
        for (x in settings.roomRangeX()) {
            g2d.drawLine(
                    windowCoordinateTranslator.toWindowX(this, x),
                    0,
                    windowCoordinateTranslator.toWindowX(this, x),
                    height
            )
        }
        for (y in settings.roomRangeY()) {
            g2d.drawLine(
                    0,
                    windowCoordinateTranslator.toWindowY(this, y),
                    width,
                    windowCoordinateTranslator.toWindowY(this, y)
            )
        }
    }

    private fun drawCursor(g2d: Graphics2D) {
        val mapX = getMapCursorX()
        val mapY = getMapCursorY()
        val x1 = windowCoordinateTranslator.toWindowX(this, mapX)
        val y1 = windowCoordinateTranslator.toWindowY(this, mapY)
        val x2 = windowCoordinateTranslator.toWindowX(this, 1 + mapX)
        val y2 = windowCoordinateTranslator.toWindowY(this, 1 + mapY)
        drawCursorRect(g2d, Point(x1, y1), Point(x2, y2), Color.BLACK, 8)
        drawCursorRect(g2d, Point(x1, y1), Point(x2, y2), Color.RED, 3)
        drawCursorRect(g2d, Point(x1, y1), Point(x2, y2), Color.PINK, 1)
    }

    private fun getMapCursorX(): Int {
        val mapX = windowCoordinateTranslator.toMapX(this, mouseState.getX())
        if (mapX < 0) {
            return 0
        }
        if (mapX >= settings.roomsSizeX) {
            return settings.roomsSizeX - 1
        }
        return mapX
    }

    private fun getMapCursorY(): Int {
        val mapY = windowCoordinateTranslator.toMapY(this, mouseState.getY())
        if (mapY < 0) {
            return 0
        }
        if (mapY >= settings.roomsSizeY) {
            return settings.roomsSizeY - 1
        }
        return mapY
    }

    private fun drawCursorRect(g2d: Graphics2D, topLeft: Point, bottomRight: Point, color: Color, border: Int) {
        g2d.color = color
        g2d.stroke = BasicStroke(border.toFloat(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
        g2d.drawRect(
                topLeft.x,
                topLeft.y,
                abs(bottomRight.x - topLeft.x),
                abs(bottomRight.y - topLeft.y)
        )
    }
}
