package pl.kwako.metroid_map.analyzer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import static pl.kwako.metroid_map.analyzer.Settings.*;

class SimplifiedRoomImage {
    private int[][] blocks = new int[ROOM_WIDTH_BLOCKS][ROOM_HEIGHT_BLOCKS];

    public void setBlock(int x, int y, int color) {
        blocks[x][y] = color;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(blocks);
    }

    public BufferedImage createImage() {
        var bufferedImage = new BufferedImage(ROOM_WIDTH_PIXELS, ROOM_HEIGHT_PIXELS, BufferedImage.TYPE_4BYTE_ABGR);

        var g2d = bufferedImage.createGraphics();

        for (var blockX = 0; blockX < ROOM_WIDTH_BLOCKS; ++blockX) {
            for (var blockY = 0; blockY < ROOM_HEIGHT_BLOCKS; ++blockY) {
                drawBlock(g2d, blockX, blockY);
            }
        }

        return bufferedImage;
    }

    private void drawBlock(Graphics2D g2d, int blockX, int blockY) {
        g2d.setPaint(new Color(blocks[blockX][blockY], false));

        g2d.fill(new Rectangle(blockX * BLOCK_WIDTH_PIXELS,
                blockY * BLOCK_HEIGHT_PIXELS,
                BLOCK_WIDTH_PIXELS,
                BLOCK_HEIGHT_PIXELS));
    }
}
