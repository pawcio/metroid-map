package pl.kwako.metroid_map

interface Settings {
    val roomsSizeX: Int
    val roomsSizeY: Int
    val roomImgWidth: Int
    val roomImgHeight: Int
    val defaultWindowWidth: Int
    val defaultWindowHeight: Int

    fun roomRangeX(): IntRange

    fun roomRangeY(): IntRange
}