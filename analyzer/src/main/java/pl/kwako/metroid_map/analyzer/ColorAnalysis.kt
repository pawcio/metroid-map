package pl.kwako.metroid_map.analyzer

import java.awt.image.BufferedImage
import java.util.Collections.singleton

private const val LEFT_DOOR_START_X = 16
private const val LEFT_DOOR_END_X = 24
private const val RIGHT_DOOR_START_X = 232
private const val RIGHT_DOOR_END_X = 224
private const val DOOR_PART_START_Y = 94
private const val DOOR_PART_WIDTH = 8
private const val DOOR_PART_HEIGHT = 20
private const val EMPTY_DOOR_PART_Y = 80
private const val EMPTY_DOOR_PART_WIDTH = 8
private const val EMPTY_DOOR_PART_HEIGHT = 48
private const val COLOR_PALETTE_SIZE = 25 // there are 25 possible colors in the whole map.


class ColorAnalysis {

    fun isSingleColor(image: BufferedImage): Boolean {
        val firstColor = image.getRGB(0, 0)
        return isSingleColor(image, firstColor)
    }

    private fun isSingleColor(image: BufferedImage, expectedColor: Int): Boolean {
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                if (image.getRGB(x, y) != expectedColor) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * Left doors are always located in the same position in the room
     *
     *
     * OO\ ##
     * OO**##
     * OO**##
     * OO**##
     * OO**##
     * OO/ ##
     *
     *
     * asterisk area * - is always one solid, non-black color (yellow, red, pink etc)
     * this area is located in (16, 94) and has dimensions of 8 x 20
     *
     *
     * hash area  # - is always black
     * this area is located in (24, 80) and has dimensions of 8 x 48
     *
     *
     * this method check solid color areas to determine if there are doors there.
     *
     * @param roomImage
     * @return
     */
    fun hasLeftDoor(roomImage: BufferedImage?): Boolean {
        return hasDoor(roomImage!!, LEFT_DOOR_START_X, LEFT_DOOR_END_X)
    }

    /**
     * Right doors are always located in the same position in the room
     *
     *
     * .................................## /OO
     * .................................##**OO
     * .................................##**OO
     * .................................##**OO
     * .................................##**OO
     * .................................## \OO
     *
     *
     * asterisk area * - is always one solid, non-black color (yellow, red, pink etc)
     * this area is located in (232, 94) and has dimensions of 8 x 20
     *
     *
     * hash area  # - is always black
     * this area is located in (224, 80) and has dimensions of 8 x 48
     *
     *
     * this method check solid color areas to determine if there are doors there.
     *
     * @param roomImage
     * @return
     */
    fun hasRightDoor(roomImage: BufferedImage?): Boolean {
        return hasDoor(roomImage!!, RIGHT_DOOR_START_X, RIGHT_DOOR_END_X)
    }

    /**
     * door part and empty part have the same dimensions in left and right door. they also share the same y position,
     * the only difference is x dimension of door part and empty part
     *
     * @param roomImage
     * @param doorPartX
     * @param emptyPartX
     * @return
     */
    private fun hasDoor(roomImage: BufferedImage, doorPartX: Int, emptyPartX: Int): Boolean {
        val doorPart = roomImage.getSubimage(doorPartX, DOOR_PART_START_Y, DOOR_PART_WIDTH, DOOR_PART_HEIGHT)
        val doorColors: Set<Int> = distinctColors(doorPart)
        if (doorColors.size > 1 || doorColors.contains(ColorConstants.BLACK)) {
            return false
        }
        val emptyPart = roomImage.getSubimage(emptyPartX, EMPTY_DOOR_PART_Y, EMPTY_DOOR_PART_WIDTH,
                EMPTY_DOOR_PART_HEIGHT)
        val emptyColors: Set<Int> = distinctColors(emptyPart)
        val onlyBlack = singleton(ColorConstants.BLACK)
        return emptyColors == onlyBlack
    }

    private fun distinctColors(roomImage: BufferedImage): Set<Int> {
        val distinctColors: MutableSet<Int> = HashSet()
        for (x in 0 until roomImage.width) {
            for (y in 0 until roomImage.height) {
                val rgb = roomImage.getRGB(x, y)
                distinctColors.add(rgb)
            }
        }
        return distinctColors
    }

    /**
     * find dominant color in given image. Skips black pixels. If no colors are found, then black is returned.
     *
     * @param roomImage
     * @return
     */
    fun dominantColor(roomImage: BufferedImage): Int {

        val colorCount = HashMap<Int, Int>(COLOR_PALETTE_SIZE)

        for (x in 0 until roomImage.width) {
            for (y in 0 until roomImage.height) {
                val rgb = roomImage.getRGB(x, y)
                colorCount.compute(rgb) { _, count ->
                    count?.plus(1) ?: 1
                }
            }
        }

        return colorCount.filterKeys { it != ColorConstants.BLACK }
                .maxBy { it.value }
                ?.key
                ?: ColorConstants.BLACK
    }
}
