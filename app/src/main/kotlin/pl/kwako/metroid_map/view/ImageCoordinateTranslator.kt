package pl.kwako.metroid_map.view

import pl.kwako.metroid_map.Settings

import javax.inject.Inject

class ImageCoordinateTranslator @Inject constructor(private val settings: Settings) {

    fun toImageX(x: Int): Int {
        return x * settings.roomImgWidth
    }

    fun toImageY(y: Int): Int {
        return y * settings.roomImgHeight
    }
}
