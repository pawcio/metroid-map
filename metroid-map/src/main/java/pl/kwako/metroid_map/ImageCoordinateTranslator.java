package pl.kwako.metroid_map;

public class ImageCoordinateTranslator {
    public int toImageX(int x) {
        return (x * Settings.ROOM_IMG_WIDTH);
    }

    public int toImageY(int y) {
        return (y * Settings.ROOM_IMG_HEIGHT);
    }
}
