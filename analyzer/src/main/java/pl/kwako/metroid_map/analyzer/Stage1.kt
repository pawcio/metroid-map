package pl.kwako.metroid_map.analyzer

import pl.kwako.metroid_map.analyzer.Settings.BLOCK_HEIGHT_PIXELS
import pl.kwako.metroid_map.analyzer.Settings.BLOCK_WIDTH_PIXELS
import pl.kwako.metroid_map.analyzer.Settings.ROOM_HEIGHT_BLOCKS
import pl.kwako.metroid_map.analyzer.Settings.ROOM_HEIGHT_PIXELS
import pl.kwako.metroid_map.analyzer.Settings.ROOM_WIDTH_BLOCKS
import pl.kwako.metroid_map.analyzer.Settings.ROOM_WIDTH_PIXELS
import pl.kwako.metroid_map.map_definition.Map
import pl.kwako.metroid_map.map_definition.Room
import java.awt.image.BufferedImage
import java.io.File
import java.util.LinkedList
import java.util.Optional
import javax.imageio.ImageIO

class Stage1(private val imageHash: ImageHash,
             private val colorAnalysis: ColorAnalysis) {

    fun runStage1(regionSettings: RegionSettings): Map {
        val mapImage = readMapImage(regionSettings)
        val rooms = LinkedList<Room>()

        for (x in 0 until regionSettings.roomCountX) {
            for (y in 0 until regionSettings.roomCountY) {
                parseRoom(regionSettings, mapImage, x, y)
                        .ifPresent { rooms.add(it) }
            }
        }

        return Map(
                regionSettings.roomCountX,
                regionSettings.roomCountY,
                ROOM_WIDTH_PIXELS,
                ROOM_HEIGHT_PIXELS,
                rooms
        )
    }

    private fun readMapImage(regionSettings: RegionSettings): BufferedImage {
        javaClass.classLoader.getResourceAsStream(regionSettings.imageFileName + ".png").use {
            return ImageIO.read(it)
        }
    }

    private fun parseRoom(regionSettings: RegionSettings, mapImage: BufferedImage, roomX: Int, roomY: Int): Optional<Room> {
        val roomImageX = ROOM_WIDTH_PIXELS * roomX
        val roomImageY = ROOM_HEIGHT_PIXELS * roomY
        val roomImage = mapImage.getSubimage(roomImageX, roomImageY, ROOM_WIDTH_PIXELS, ROOM_HEIGHT_PIXELS)

        if (colorAnalysis.isSingleColor(roomImage)) {
            return Optional.empty()
        }

        return Optional.of(saveStage1(regionSettings, roomImage, roomX, roomY))
    }

    private fun saveStage1(regionSettings: RegionSettings, roomImage: BufferedImage, roomX: Int, roomY: Int): Room {
        val md5Name = imageHash.getImageHash(roomImage)
        val outFile = File(String.format("analyze/%s/hd/map_%s.png", regionSettings.imageFileName, md5Name))
        saveImageFile(outFile, roomImage)

        val simpleRoom = renderSimplifiedRoom(roomImage)
        val roomHashCode = simpleRoom.hashCode().toString()
        val simpleOutFile = File(String.format("analyze/%s/sd/map_%s.png", regionSettings.imageFileName, roomHashCode))
        val image = simpleRoom.createImage()
        saveImageFile(simpleOutFile, image)

        val hasLeftDoor = colorAnalysis.hasLeftDoor(roomImage)
        val hasRightDoor = colorAnalysis.hasRightDoor(roomImage)
        return Room(roomX, roomY, roomHashCode, hasLeftDoor, hasRightDoor)
    }

    private fun saveImageFile(outFile: File, roomImage: BufferedImage) {
        if (!outFile.exists()) {
            outFile.mkdirs()
            ImageIO.write(roomImage, "png", outFile)
        }
    }

    private fun renderSimplifiedRoom(roomImage: BufferedImage): SimplifiedRoomImage {
        val simplifiedRoomImage = SimplifiedRoomImage()
        for (blockX in 0 until ROOM_WIDTH_BLOCKS) {
            for (blockY in 0 until ROOM_HEIGHT_BLOCKS) {
                val dominantColor = getDominantColor(roomImage, blockX, blockY)
                simplifiedRoomImage.setBlock(blockX, blockY, dominantColor)
            }
        }
        return simplifiedRoomImage
    }

    private fun getDominantColor(roomImage: BufferedImage, blockX: Int, blockY: Int): Int {
        val blockImage = roomImage.getSubimage(
                blockX * BLOCK_WIDTH_PIXELS,
                blockY * BLOCK_HEIGHT_PIXELS,
                BLOCK_WIDTH_PIXELS,
                BLOCK_HEIGHT_PIXELS)
        return colorAnalysis.dominantColor(blockImage)
    }
}
