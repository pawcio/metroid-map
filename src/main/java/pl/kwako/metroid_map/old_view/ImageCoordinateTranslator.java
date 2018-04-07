package pl.kwako.metroid_map.old_view;

import pl.kwako.metroid_map.Settings;

import javax.inject.Inject;

public class ImageCoordinateTranslator {

    private final Settings settings;

    @Inject
    public ImageCoordinateTranslator(Settings settings) {
        this.settings = settings;
    }

    public int toImageX(int x) {
        return (x * settings.roomImgWidth());
    }

    public int toImageY(int y) {
        return (y * settings.roomImgHeight());
    }
}
