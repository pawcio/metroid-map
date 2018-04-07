package pl.kwako.metroid_map.analyzer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import static pl.kwako.metroid_map.analyzer.Settings.*;

public class SimplifiedRoomImage {
    private int[][] blocks = new int[ROOM_WIDTH_BLOCKS][ROOM_HEIGHT_BLOCKS];

    public void setBlock(int x, int y, int color) {
        blocks[x][y] = color;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(blocks);
    }

    public BufferedImage createImage() {
        BufferedImage bufferedImage = new BufferedImage(ROOM_WIDTH_PIXELS, ROOM_HEIGHT_PIXELS, BufferedImage.TYPE_4BYTE_ABGR);

        Graphics2D g2d = bufferedImage.createGraphics();

        for (int blockX = 0; blockX < ROOM_WIDTH_BLOCKS; ++blockX) {
            for (int blockY = 0; blockY < ROOM_HEIGHT_BLOCKS; ++blockY) {
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
