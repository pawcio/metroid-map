package pl.kwako.metroid_map.analyzer;

import pl.kwako.metroid_map.analyzer.region.RegionSettings;
import pl.kwako.metroid_map.persistence.ObjectRepository;

class MapAnalyzer {

    private final Stage1 stage1;
    private final ObjectRepository objectRepository;

    public MapAnalyzer(Stage1 stage1, ObjectRepository objectRepository) {
        this.stage1 = stage1;
        this.objectRepository = objectRepository;
    }

    public void parseRegionStage1(RegionSettings regionSettings) {
        var stage1Result = stage1.runStage1(regionSettings);
        var filePath = String.format("analyze/%s/stage01_result.json", regionSettings.getImageFileName());
        objectRepository.toFile(stage1Result, filePath);
    }
}
