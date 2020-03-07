package pl.kwako.metroid_map.analyzer

import pl.kwako.metroid_map.persistence.ObjectRepository

private val regions = listOf(
        "Brinstar.json",
        "KraidsHideout.json",
        "Norfair.json",
        "RidleyHideout.json",
        "Tourian.json",
        "MetroidCompleateMap.json"
)

fun main() {
    val mapAnalyzer = MapAnalyzer(
            Stage1(
                    ImageHash(),
                    ColorAnalysis()
            ),
            ObjectRepository()
    )

    regions.parallelStream().forEach(mapAnalyzer::analyzeFile)
}

