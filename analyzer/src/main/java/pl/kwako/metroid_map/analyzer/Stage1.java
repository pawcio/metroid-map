package pl.kwako.metroid_map.analyzer;

import pl.kwako.metroid_map.map_definition.Map;
import pl.kwako.metroid_map.map_definition.Room;
import pl.kwako.metroid_map.analyzer.region.RegionSettings;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import static pl.kwako.metroid_map.analyzer.Settings.*;

public class Stage1 {

    private final ImageHash imageHash;
    private final ColorAnalysis colorAnalysis;

    public Stage1(ImageHash imageHash, ColorAnalysis colorAnalysis) {
        this.imageHash = imageHash;
        this.colorAnalysis = colorAnalysis;
    }

    public Map runStage1(RegionSettings regionSettings) throws IOException, NoSuchAlgorithmException {

        BufferedImage mapImage = readMapImage(regionSettings);

        Collection<Room> rooms = new LinkedList<>();

        for (int x = 0; x < regionSettings.getRoomCountX(); ++x) {
            for (int y = 0; y < regionSettings.getRoomCountY(); ++y) {
                parseRoom(regionSettings, mapImage, x, y)
                        .ifPresent(rooms::add);
            }
        }

        return new Map(
                regionSettings.getRoomCountX(),
                regionSettings.getRoomCountY(),
                Settings.ROOM_WIDTH_PIXELS,
                Settings.ROOM_HEIGHT_PIXELS,
                rooms
        );
    }

    private BufferedImage readMapImage(RegionSettings regionSettings) throws IOException {
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(regionSettings.getImageFileName() + ".png")) {
            return ImageIO.read(resourceAsStream);
        }
    }

    private Optional<Room> parseRoom(RegionSettings regionSettings, BufferedImage mapImage, int roomX, int roomY) throws IOException, NoSuchAlgorithmException {

        int roomImageX = ROOM_WIDTH_PIXELS * roomX;
        int roomImageY = ROOM_HEIGHT_PIXELS * roomY;

        BufferedImage roomImage = mapImage.getSubimage(roomImageX, roomImageY, ROOM_WIDTH_PIXELS, ROOM_HEIGHT_PIXELS);

        // skip empty spaces
        if (colorAnalysis.isSingleColor(roomImage)) {
            return Optional.empty();
        }

        return Optional.of(saveStage1(regionSettings, roomImage, roomX, roomY));
    }

    /**
     * split whole map into rooms, find distinct rooms and return info about room locations.
     *
     * @param roomImage
     * @param roomX
     * @param roomY
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private Room saveStage1(RegionSettings regionSettings, BufferedImage roomImage, int roomX, int roomY) throws IOException, NoSuchAlgorithmException {

        String md5name = imageHash.getImageHash(roomImage);
        boolean hasLeftDoor = colorAnalysis.hasLeftDoor(roomImage);
        boolean hasRightDoor = colorAnalysis.hasRightDoor(roomImage);

        File outFile = new File(String.format("analyze/%s/hd/map_%s.png", regionSettings.getImageFileName(), md5name));

        if (!outFile.exists()) {
            outFile.mkdirs();
            ImageIO.write(roomImage, "png", outFile);
        }


        SimplifiedRoomImage simpleRoom = renderSimplifiedRoom(roomImage);
        String roomHashCode = ((Integer) simpleRoom.hashCode()).toString();
        BufferedImage image = simpleRoom.createImage();

        File simpleOutFile = new File(String.format("analyze/%s/sd/map_%s.png", regionSettings.getImageFileName(), roomHashCode));

        if (!simpleOutFile.exists()) {
            simpleOutFile.mkdirs();
            ImageIO.write(image, "png", simpleOutFile);
        }

        return new Room(roomX, roomY, roomHashCode, hasLeftDoor, hasRightDoor);
    }

    private SimplifiedRoomImage renderSimplifiedRoom(BufferedImage roomImage) {

        SimplifiedRoomImage simplifiedRoomImage = new SimplifiedRoomImage();

        for (int blockX = 0; blockX < ROOM_WIDTH_BLOCKS; ++blockX) {
            for (int blockY = 0; blockY < ROOM_HEIGHT_BLOCKS; ++blockY) {
                int dominantColor = getDominantColor(roomImage, blockX, blockY);
                simplifiedRoomImage.setBlock(blockX, blockY, dominantColor);
            }
        }

        return simplifiedRoomImage;
    }

    private int getDominantColor(BufferedImage roomImage, int blockX, int blockY) {
        BufferedImage blockImage = roomImage.getSubimage(
                blockX * BLOCK_WIDTH_PIXELS,
                blockY * BLOCK_HEIGHT_PIXELS,
                BLOCK_WIDTH_PIXELS,
                BLOCK_HEIGHT_PIXELS);

        return colorAnalysis.dominantColor(blockImage);
    }
}
