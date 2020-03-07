package pl.kwako.metroid_map.analyzer

import pl.kwako.metroid_map.persistence.ObjectRepository
import java.util.logging.Level
import java.util.logging.Logger

internal class MapAnalyzer(private val stage1: Stage1,
                           private val objectRepository: ObjectRepository) {

    fun analyzeFile(fileName: String) {
        Logger.getGlobal().log(Level.INFO, "START $fileName")
        val regionSettings = objectRepository.fromResource(fileName, RegionSettings::class.java)
        val stage1Result = stage1.runStage1(regionSettings)
        val filePath = String.format("analyze/%s/stage01_result.json", regionSettings.imageFileName)
        objectRepository.toFile(stage1Result, filePath)
        Logger.getGlobal().log(Level.INFO, "END $fileName")
    }
}