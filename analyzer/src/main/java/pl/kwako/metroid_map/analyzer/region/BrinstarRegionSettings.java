package pl.kwako.metroid_map.analyzer.region;

public class BrinstarRegionSettings implements RegionSettings {
    @Override
    public String getImageFileName() {
        return "Brinstar";
    }

    @Override
    public int getRoomCountX() {
        return 30;
    }

    @Override
    public int getRoomCountY() {
        return 19;
    }
}
