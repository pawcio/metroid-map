package pl.kwako.metroid_map.analyzer;

import java.awt.image.BufferedImage;
import java.util.*;

public class ColorAnalysis {

    public boolean isSingleColor(BufferedImage image) {
        int firstColor = image.getRGB(0, 0);

        return isSingleColor(image, firstColor);
    }

    public boolean isSingleColor(BufferedImage image, int expectedColor) {
        for (int x = 0; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight(); ++y) {
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
        return detectDoor(roomImage, 16, 24);
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
        return detectDoor(roomImage, 232, 224);
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
        BufferedImage doorPart = roomImage.getSubimage(doorPartX, 94, 8, 20);
        Set<Integer> doorColors = distinctColors(doorPart);

        if (doorColors.size() > 1 || doorColors.contains(ColorConstants.BLACK)) {
            return false;
        }

        BufferedImage emptyPart = roomImage.getSubimage(emptyPartX, 80, 8, 48);
        Set<Integer> emptyColors = distinctColors(emptyPart);

        if (emptyColors.size() > 1 || !emptyColors.contains(ColorConstants.BLACK)) {
            return false;
        }

        return true;
    }

    public Set<Integer> distinctColors(BufferedImage roomImage) {
        Set<Integer> distinctColors = new HashSet<>();

        for (int x = 0; x < roomImage.getWidth(); ++x) {
            for (int y = 0; y < roomImage.getHeight(); ++y) {
                int rgb = roomImage.getRGB(x, y);
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

        Map<Integer, Integer> colorCount = new HashMap<>(25); // there are 25 possible colors in the whole map.

        for (int x = 0; x < roomImage.getWidth(); ++x) {
            for (int y = 0; y < roomImage.getHeight(); ++y) {

                int rgb = roomImage.getRGB(x, y);

                if (colorCount.containsKey(rgb)) {
                    Integer currentCount = colorCount.get(rgb);
                    colorCount.put(rgb, currentCount + 1);
                } else {
                    colorCount.put(rgb, 1);
                }
            }
        }

        Optional<Map.Entry<Integer, Integer>> max = colorCount.entrySet().stream()
                .filter(entrySet -> entrySet.getKey() != ColorConstants.BLACK)
                .max(Comparator.comparing(Map.Entry::getValue));

        return max.map(Map.Entry::getKey)
                .orElse(ColorConstants.BLACK);
    }
}
