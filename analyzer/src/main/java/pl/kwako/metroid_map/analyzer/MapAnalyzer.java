package pl.kwako.metroid_map.analyzer;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.kwako.metroid_map.analyzer.map_definition.Map;
import pl.kwako.metroid_map.analyzer.region.RegionSettings;

import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class MapAnalyzer {

    private final Stage1 stage1;
    private final ObjectMapper objectMapper;

    public MapAnalyzer(Stage1 stage1, ObjectMapper objectMapper) {
        this.stage1 = stage1;
        this.objectMapper = objectMapper;
    }

    public void parseRegionStage1(RegionSettings regionSettings) throws IOException, NoSuchAlgorithmException {
        Map stage1Result = stage1.runStage1(regionSettings);

        String serializedMap = objectMapper.writeValueAsString(stage1Result);

        try (FileWriter resultsFile = new FileWriter(String.format("analyze/%s/stage01_result.json", regionSettings.getImageFileName()))) {
            resultsFile.write(serializedMap);
        }
    }
}
