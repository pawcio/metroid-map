package pl.kwako.metroid_map.analyzer;

import pl.kwako.metroid_map.persistence.ObjectRepository;

class Main {

    private static final String[] regions = {
            "Brinstar.json",
            "KraidsHideout.json",
            "Norfair.json",
            "RidleyHideout.json",
            "Tourian.json",
            "MetroidCompleateMap.json",
    };

    public static void main(String... args) {

        ObjectRepository objectRepository = new ObjectRepository();

        var mapAnalyzer = new MapAnalyzer(
                new Stage1(
                        new ImageHash(),
                        new ColorAnalysis()
                ),
                objectRepository
        );

        for (String region : regions) {
            RegionSettings regionSettings = objectRepository.fromResource(region, RegionSettings.class);
            mapAnalyzer.parseRegionStage1(regionSettings);
        }
    }
}
