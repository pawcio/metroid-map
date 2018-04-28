package pl.kwako.metroid_map;

class DefaultSettings implements Settings {
    private static final int ROOMS_SIZE_X = 30;
    private static final int ROOMS_SIZE_Y = 30;
    private static final int ROOM_IMG_WIDTH = 256;
    private static final int ROOM_IMG_HEIGHT = 240;
    private static final int DEFAULT_WINDOW_WIDTH = 960;
    private static final int DEFAULT_WINDOW_HEIGHT = 960;

    @Override
    public int roomsSizeX() {
        return ROOMS_SIZE_X;
    }

    @Override
    public int roomsSizeY() {
        return ROOMS_SIZE_Y;
    }

    @Override
    public int roomImgWidth() {
        return ROOM_IMG_WIDTH;
    }

    @Override
    public int roomImgHeight() {
        return ROOM_IMG_HEIGHT;
    }

    @Override
    public int defaultWindowWidth() {
        return DEFAULT_WINDOW_WIDTH;
    }

    @Override
    public int defaultWindowHeight() {
        return DEFAULT_WINDOW_HEIGHT;
    }
}
