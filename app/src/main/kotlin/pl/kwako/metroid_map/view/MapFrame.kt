package pl.kwako.metroid_map.view

import pl.kwako.metroid_map.Settings
import java.awt.Frame
import java.awt.image.BufferedImage
import javax.annotation.PostConstruct
import javax.imageio.ImageIO
import javax.inject.Inject
import javax.swing.JFrame
import javax.swing.WindowConstants

private const val TITLE = "Metroid Map"

class MapFrame @Inject constructor(private val windowCoordinateTranslator: WindowCoordinateTranslator,
                                   private val mapPanel: MapPanel,
                                   settings: Settings) : JFrame(TITLE) {
    init {
        add(mapPanel)
        title = TITLE
        setSize(settings.defaultWindowWidth, settings.defaultWindowHeight)
        setLocationRelativeTo(null)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        extendedState = Frame.MAXIMIZED_BOTH
        isVisible = true
    }

    @PostConstruct
    fun setUp() {
        // TODO: implement loading map definition from file.
        val backgroundImage = loadBackgroundImage()
        mapPanel.setMapImage(backgroundImage)
        setupListeners()
    }


    private fun setupListeners() {
        addKeyListener(KeyPressListener(this, windowCoordinateTranslator))
        MouseOverMapListener(this).let {
            addMouseMotionListener(it)
            addMouseMotionListener(it)
            addMouseWheelListener(it)
            mapPanel.setMouseState(it)
        }
    }

    private fun loadBackgroundImage(): BufferedImage {
        javaClass.classLoader.getResourceAsStream("MetroidCompleateMapBG.png").use {
            return ImageIO.read(it)
        }
    }
}