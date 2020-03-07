package pl.kwako.metroid_map.analyzer;

import pl.kwako.metroid_map.persistence.ObjectRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

class MapAnalyzer {

    private final Stage1 stage1;
    private final ObjectRepository objectRepository;

    MapAnalyzer(Stage1 stage1, ObjectRepository objectRepository) {
        this.stage1 = stage1;
        this.objectRepository = objectRepository;
    }

    void analyzeFile(String fileName) {
        Logger.getGlobal().log(Level.INFO, "START " + fileName);
        RegionSettings regionSettings = objectRepository.fromResource(fileName, RegionSettings.class);
        var stage1Result = stage1.runStage1(regionSettings);
        var filePath = String.format("analyze/%s/stage01_result.json", regionSettings.getImageFileName());
        objectRepository.toFile(stage1Result, filePath);
        Logger.getGlobal().log(Level.INFO, "END " + fileName);
    }
}
