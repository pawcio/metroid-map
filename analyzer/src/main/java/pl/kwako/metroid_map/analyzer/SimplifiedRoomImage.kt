package pl.kwako.metroid_map.analyzer


import pl.kwako.metroid_map.analyzer.Settings.BLOCK_HEIGHT_PIXELS
import pl.kwako.metroid_map.analyzer.Settings.BLOCK_WIDTH_PIXELS
import pl.kwako.metroid_map.analyzer.Settings.ROOM_HEIGHT_BLOCKS
import pl.kwako.metroid_map.analyzer.Settings.ROOM_HEIGHT_PIXELS
import pl.kwako.metroid_map.analyzer.Settings.ROOM_WIDTH_BLOCKS
import pl.kwako.metroid_map.analyzer.Settings.ROOM_WIDTH_PIXELS
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.image.BufferedImage

class SimplifiedRoomImage {

    private val blocks = Array(ROOM_WIDTH_BLOCKS) {
        Array(ROOM_HEIGHT_BLOCKS) {
            0
        }
    }

    fun setBlock(x: Int, y: Int, color: Int) {
        blocks[x][y] = color
    }

    fun createImage(): BufferedImage {
        val bufferedImage = BufferedImage(ROOM_WIDTH_PIXELS, ROOM_HEIGHT_PIXELS, BufferedImage.TYPE_4BYTE_ABGR)
        val g2d = bufferedImage.createGraphics()

        for (blockX in 0 until ROOM_WIDTH_BLOCKS) {
            for (blockY in 0 until ROOM_HEIGHT_BLOCKS) {
                drawBlock(g2d, blockX, blockY)
            }
        }

        return bufferedImage
    }

    private fun drawBlock(g2d: Graphics2D, blockX: Int, blockY: Int) {
        g2d.paint = Color(blocks[blockX][blockY], false)

        g2d.fill(Rectangle(blockX * BLOCK_WIDTH_PIXELS,
                blockY * BLOCK_HEIGHT_PIXELS,
                BLOCK_WIDTH_PIXELS,
                BLOCK_HEIGHT_PIXELS))
    }

    override fun hashCode(): Int = blocks.contentDeepHashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SimplifiedRoomImage

        if (!blocks.contentDeepEquals(other.blocks)) return false

        return true
    }
}
