package pl.kwako.metroid_map.analyzer.region;

public class WholeMapRegionSettings implements RegionSettings {
    @Override
    public String getImageFileName() {
        return "MetroidCompleateMap";
    }

    @Override
    public int getRoomCountX() {
        return 30;
    }

    @Override
    public int getRoomCountY() {
        return 30;
    }
}
