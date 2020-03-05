package pl.kwako.metroid_map

data class DefaultSettings(
        override val roomsSizeX: Int = 30,
        override val roomsSizeY: Int = 30,
        override val roomImgWidth: Int = 256,
        override val roomImgHeight: Int = 240,
        override val defaultWindowWidth: Int = 960,
        override val defaultWindowHeight: Int = 960
) : Settings {
    override fun roomRangeX(): IntRange = 0 until roomsSizeX
    override fun roomRangeY(): IntRange = 0 until roomsSizeY
}