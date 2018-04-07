package pl.kwako.metroid_map.analyzer.region;

public class NorfairRegionSettings implements RegionSettings {
    @Override
    public String getImageFileName() {
        return "Norfair";
    }

    @Override
    public int getRoomCountX() {
        return 18;
    }

    @Override
    public int getRoomCountY() {
        return 15;
    }
}
