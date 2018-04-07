package pl.kwako.metroid_map.analyzer;

import pl.kwako.metroid_map.analyzer.json.CustomObjectMapper;
import pl.kwako.metroid_map.analyzer.region.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String... args) throws IOException, NoSuchAlgorithmException {

        MapAnalyzer mapAnalyzer = new MapAnalyzer(
                new Stage1(
                        new ImageHash(),
                        new ColorAnalysis()
                ),
                new CustomObjectMapper()
        );

        mapAnalyzer.parseRegionStage1(new BrinstarRegionSettings());
        mapAnalyzer.parseRegionStage1(new KraidsHideoutRegionSettings());
        mapAnalyzer.parseRegionStage1(new NorfairRegionSettings());
        mapAnalyzer.parseRegionStage1(new RidleyHideoutRegionSettings());
        mapAnalyzer.parseRegionStage1(new TourianRegionSettings());
        mapAnalyzer.parseRegionStage1(new WholeMapRegionSettings());
    }
}
