package pl.kwako.metroid_map.analyzer;

import pl.kwako.metroid_map.persistence.ObjectRepository;

import java.util.List;

class Main {

    private static final List<String> regions = List.of(
            "Brinstar.json",
            "KraidsHideout.json",
            "Norfair.json",
            "RidleyHideout.json",
            "Tourian.json",
            "MetroidCompleateMap.json");

    public static void main(String... args) {
        var mapAnalyzer = new MapAnalyzer(
                new Stage1(
                        new ImageHash(),
                        new ColorAnalysis()
                ),
                new ObjectRepository()
        );

        regions.parallelStream().forEach(mapAnalyzer::analyzeFile);
    }
}
