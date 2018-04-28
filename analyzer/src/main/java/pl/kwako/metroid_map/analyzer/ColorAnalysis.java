package pl.kwako.metroid_map.analyzer;

import java.awt.image.BufferedImage;
import java.util.*;

class ColorAnalysis {

    private static final int LEFT_DOOR_START_X = 16;
    private static final int LEFT_DOOR_END_X = 24;
    private static final int RIGHT_DOOR_START_X = 232;
    private static final int RIGHT_DOOR_END_X = 224;
    private static final int DOOR_PART_START_Y = 94;
    private static final int DOOR_PART_WIDTH = 8;
    private static final int DOOR_PART_HEIGHT = 20;
    private static final int EMPTY_DOOR_PART_Y = 80;
    private static final int EMPTY_DOOR_PART_WIDTH = 8;
    private static final int EMPTY_DOOR_PART_HEIGHT = 48;
    private static final int COLOR_PALETTE_SIZE = 25;// there are 25 possible colors in the whole map.

    public boolean isSingleColor(BufferedImage image) {
        var firstColor = image.getRGB(0, 0);

        return isSingleColor(image, firstColor);
    }

    public boolean isSingleColor(BufferedImage image, int expectedColor) {
        for (var x = 0; x < image.getWidth(); ++x) {
            for (var y = 0; y < image.getHeight(); ++y) {
                if (image.getRGB(x, y) != expectedColor) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Left doors are always located in the same position in the room
     * <p>
     * OO\ ##
     * OO**##
     * OO**##
     * OO**##
     * OO**##
     * OO/ ##
     * <p>
     * asterisk area * - is always one solid, non-black color (yellow, red, pink etc)
     * this area is located in (16, 94) and has dimensions of 8 x 20
     * <p>
     * hashtag area  # - is always black
     * this area is located in (24, 80) and has dimensions of 8 x 48
     * <p>
     * this method check solid color areas to determine if there are doors there.
     *
     * @param roomImage
     * @return
     */
    public boolean hasLeftDoor(BufferedImage roomImage) {
        return detectDoor(roomImage, LEFT_DOOR_START_X, LEFT_DOOR_END_X);
    }

    /**
     * Right doors are always located in the same position in the room
     * <p>
     * .................................## /OO
     * .................................##**OO
     * .................................##**OO
     * .................................##**OO
     * .................................##**OO
     * .................................## \OO
     * <p>
     * asterisk area * - is always one solid, non-black color (yellow, red, pink etc)
     * this area is located in (232, 94) and has dimensions of 8 x 20
     * <p>
     * hashtag area  # - is always black
     * this area is located in (224, 80) and has dimensions of 8 x 48
     * <p>
     * this method check solid color areas to determine if there are doors there.
     *
     * @param roomImage
     * @return
     */
    public boolean hasRightDoor(BufferedImage roomImage) {
        return detectDoor(roomImage, RIGHT_DOOR_START_X, RIGHT_DOOR_END_X);
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
    public boolean detectDoor(BufferedImage roomImage, int doorPartX, int emptyPartX) {
        var doorPart = roomImage.getSubimage(doorPartX, DOOR_PART_START_Y, DOOR_PART_WIDTH, DOOR_PART_HEIGHT);
        var doorColors = distinctColors(doorPart);

        if (doorColors.size() > 1 || doorColors.contains(ColorConstants.BLACK)) {
            return false;
        }

        var emptyPart = roomImage.getSubimage(emptyPartX, EMPTY_DOOR_PART_Y,
                EMPTY_DOOR_PART_WIDTH, EMPTY_DOOR_PART_HEIGHT);
        var emptyColors = distinctColors(emptyPart);

        if (emptyColors.size() > 1 || !emptyColors.contains(ColorConstants.BLACK)) {
            return false;
        }

        return true;
    }

    public Set<Integer> distinctColors(BufferedImage roomImage) {
        Set<Integer> distinctColors = new HashSet<>();

        for (var x = 0; x < roomImage.getWidth(); ++x) {
            for (var y = 0; y < roomImage.getHeight(); ++y) {
                var rgb = roomImage.getRGB(x, y);
                distinctColors.add(rgb);
            }
        }

        return distinctColors;
    }

    /**
     * find dominant color in given image. Skips black pixels. If no colors are found, then black is returned.
     *
     * @param roomImage
     * @return
     */
    public int dominantColor(BufferedImage roomImage) {

        Map<Integer, Integer> colorCount = new HashMap<>(COLOR_PALETTE_SIZE);

        for (var x = 0; x < roomImage.getWidth(); ++x) {
            for (var y = 0; y < roomImage.getHeight(); ++y) {

                var rgb = roomImage.getRGB(x, y);

                if (colorCount.containsKey(rgb)) {
                    var currentCount = colorCount.get(rgb);
                    colorCount.put(rgb, currentCount + 1);
                } else {
                    colorCount.put(rgb, 1);
                }
            }
        }

        var max = colorCount.entrySet().stream()
                .filter(entrySet -> entrySet.getKey() != ColorConstants.BLACK)
                .max(Comparator.comparing(Map.Entry::getValue));

        return max.map(Map.Entry::getKey)
                .orElse(ColorConstants.BLACK);
    }
}
