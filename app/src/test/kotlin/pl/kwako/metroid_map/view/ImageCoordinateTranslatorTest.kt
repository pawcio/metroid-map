package pl.kwako.metroid_map.view

@org.junit.runner.RunWith(org.mockito.junit.MockitoJUnitRunner::class)
class ImageCoordinateTranslatorTest {

    lateinit var imageCoordinateTranslator: pl.kwako.metroid_map.view.ImageCoordinateTranslator

    @org.mockito.Mock
    lateinit var settings: pl.kwako.metroid_map.Settings;

    @org.junit.Before
    fun setUp() {
        imageCoordinateTranslator = pl.kwako.metroid_map.view.ImageCoordinateTranslator(settings)
        org.mockito.Mockito.doReturn(3).`when`(settings).roomImgWidth
        org.mockito.Mockito.doReturn(5).`when`(settings).roomImgHeight
    }

    @org.junit.Test
    fun zeroXGivesZero() {
        org.junit.Assert.assertThat(imageCoordinateTranslator.toImageX(0), org.hamcrest.CoreMatchers.`is`(0));
    }

    @org.junit.Test
    fun imageXMultipliesByRoomWidth() {
        var imageX = imageCoordinateTranslator.toImageX(2);
        org.mockito.Mockito.verify(settings).roomImgWidth;
        org.junit.Assert.assertThat(imageX, org.hamcrest.CoreMatchers.`is`(2 * 3));
    }

    @org.junit.Test
    fun zeroYGivesZero() {
        org.junit.Assert.assertThat(imageCoordinateTranslator.toImageY(0), org.hamcrest.CoreMatchers.`is`(0));
    }

    @org.junit.Test
    fun imageYMultipliesByRoomHeight() {
        var imageX = imageCoordinateTranslator.toImageY(2);
        org.mockito.Mockito.verify(settings).roomImgHeight
        org.junit.Assert.assertThat(imageX, org.hamcrest.CoreMatchers.`is`(2 * 5));
    }
}