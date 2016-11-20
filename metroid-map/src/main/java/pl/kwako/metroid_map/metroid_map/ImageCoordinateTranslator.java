package pl.kwako.metroid_map.metroid_map;

import static pl.kwako.metroid_map.metroid_map.Settings.ROOM_IMG_HEIGHT;
import static pl.kwako.metroid_map.metroid_map.Settings.ROOM_IMG_WIDTH;

public class ImageCoordinateTranslator {
    public int toImageX(int x) {
        return (x * ROOM_IMG_WIDTH);
    }

    public int toImageY(int y) {
        return (y * ROOM_IMG_HEIGHT);
    }
}
