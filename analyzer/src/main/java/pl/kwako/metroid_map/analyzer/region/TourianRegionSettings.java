package pl.kwako.metroid_map.analyzer.region;

public class TourianRegionSettings implements RegionSettings {
    @Override
    public String getImageFileName() {
        return "Tourian";
    }

    @Override
    public int getRoomCountX() {
        return 10;
    }

    @Override
    public int getRoomCountY() {
        return 9;
    }
}
