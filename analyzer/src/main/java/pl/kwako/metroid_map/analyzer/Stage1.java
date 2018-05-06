package pl.kwako.metroid_map.analyzer;

import pl.kwako.metroid_map.map_definition.Map;
import pl.kwako.metroid_map.map_definition.Room;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import static pl.kwako.metroid_map.analyzer.Settings.BLOCK_HEIGHT_PIXELS;
import static pl.kwako.metroid_map.analyzer.Settings.BLOCK_WIDTH_PIXELS;
import static pl.kwako.metroid_map.analyzer.Settings.ROOM_HEIGHT_BLOCKS;
import static pl.kwako.metroid_map.analyzer.Settings.ROOM_HEIGHT_PIXELS;
import static pl.kwako.metroid_map.analyzer.Settings.ROOM_WIDTH_BLOCKS;
import static pl.kwako.metroid_map.analyzer.Settings.ROOM_WIDTH_PIXELS;

class Stage1 {

    private final ImageHash imageHash;
    private final ColorAnalysis colorAnalysis;

    Stage1(ImageHash imageHash, ColorAnalysis colorAnalysis) {
        this.imageHash = imageHash;
        this.colorAnalysis = colorAnalysis;
    }

    public Map runStage1(RegionSettings regionSettings) {

        var mapImage = readMapImage(regionSettings);

        Collection<Room> rooms = new LinkedList<>();

        for (var x = 0; x < regionSettings.getRoomCountX(); ++x) {
            for (var y = 0; y < regionSettings.getRoomCountY(); ++y) {
                parseRoom(regionSettings, mapImage, x, y)
                        .ifPresent(rooms::add);
            }
        }

        return new Map(
                regionSettings.getRoomCountX(),
                regionSettings.getRoomCountY(),
                ROOM_WIDTH_PIXELS,
                ROOM_HEIGHT_PIXELS,
                rooms
        );
    }

    private BufferedImage readMapImage(RegionSettings regionSettings) {
        try (var resourceAsStream = getClass().getClassLoader().getResourceAsStream(
                regionSettings.getImageFileName() + ".png")) {
            return ImageIO.read(resourceAsStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Optional<Room> parseRoom(RegionSettings regionSettings, BufferedImage mapImage, int roomX, int roomY) {

        var roomImageX = ROOM_WIDTH_PIXELS * roomX;
        var roomImageY = ROOM_HEIGHT_PIXELS * roomY;
        var roomImage = mapImage.getSubimage(roomImageX, roomImageY, ROOM_WIDTH_PIXELS, ROOM_HEIGHT_PIXELS);

        // skip empty spaces
        if (colorAnalysis.isSingleColor(roomImage)) {
            return Optional.empty();
        }

        return Optional.of(saveStage1(regionSettings, roomImage, roomX, roomY));
    }

    /**
     * split whole map into rooms, find distinct rooms and return info about room locations.
     */
    private Room saveStage1(RegionSettings regionSettings, BufferedImage roomImage, int roomX, int roomY) {

        var md5name = imageHash.getImageHash(roomImage);
        var hasLeftDoor = colorAnalysis.hasLeftDoor(roomImage);
        var hasRightDoor = colorAnalysis.hasRightDoor(roomImage);

        var outFile = new File(String.format("analyze/%s/hd/map_%s.png", regionSettings.getImageFileName(), md5name));
        if (!outFile.exists()) {
            outFile.mkdirs();
            try {
                ImageIO.write(roomImage, "png", outFile);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }


        var simpleRoom = renderSimplifiedRoom(roomImage);
        var roomHashCode = ((Integer) simpleRoom.hashCode()).toString();
        var image = simpleRoom.createImage();

        var simpleOutFile = new File(String.format("analyze/%s/sd/map_%s.png", regionSettings.getImageFileName(),
                roomHashCode));

        if (!simpleOutFile.exists()) {
            simpleOutFile.mkdirs();
            try {
                ImageIO.write(image, "png", simpleOutFile);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        return new Room(roomX, roomY, roomHashCode, hasLeftDoor, hasRightDoor);
    }

    private SimplifiedRoomImage renderSimplifiedRoom(BufferedImage roomImage) {

        var simplifiedRoomImage = new SimplifiedRoomImage();

        for (var blockX = 0; blockX < ROOM_WIDTH_BLOCKS; ++blockX) {
            for (var blockY = 0; blockY < ROOM_HEIGHT_BLOCKS; ++blockY) {
                var dominantColor = getDominantColor(roomImage, blockX, blockY);
                simplifiedRoomImage.setBlock(blockX, blockY, dominantColor);
            }
        }

        return simplifiedRoomImage;
    }

    private int getDominantColor(BufferedImage roomImage, int blockX, int blockY) {
        var blockImage = roomImage.getSubimage(
                blockX * BLOCK_WIDTH_PIXELS,
                blockY * BLOCK_HEIGHT_PIXELS,
                BLOCK_WIDTH_PIXELS,
                BLOCK_HEIGHT_PIXELS);

        return colorAnalysis.dominantColor(blockImage);
    }
}
