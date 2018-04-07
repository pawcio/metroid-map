package pl.kwako.metroid_map.analyzer.region;

public class KraidsHideoutRegionSettings implements RegionSettings {
    @Override
    public String getImageFileName() {
        return "KraidsHideout";
    }

    @Override
    public int getRoomCountX() {
        return 12;
    }

    @Override
    public int getRoomCountY() {
        return 15;
    }
}
