package pl.kwako.metroid_map.analyzer.region;

public class RidleyHideoutRegionSettings implements RegionSettings {
    @Override
    public String getImageFileName() {
        return "RidleyHideout";
    }

    @Override
    public int getRoomCountX() {
        return 17;
    }

    @Override
    public int getRoomCountY() {
        return 8;
    }
}
