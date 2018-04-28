package pl.kwako.metroid_map.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.kwako.metroid_map.Settings;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ImageCoordinateTranslatorTest {

    private ImageCoordinateTranslator imageCoordinateTranslator;

    @Mock
    private Settings settings;

    @Before
    public void setUp() {
        imageCoordinateTranslator = new ImageCoordinateTranslator(settings);
        doReturn(3).when(settings).roomImgWidth();
        doReturn(5).when(settings).roomImgHeight();
    }

    @Test
    public void zeroXGivesZero() {
        assertThat(imageCoordinateTranslator.toImageX(0), is(0));
    }

    @Test
    public void imageXMultipliesByRoomWidth() {
        var imageX = imageCoordinateTranslator.toImageX(2);
        verify(settings).roomImgWidth();
        assertThat(imageX, is(2 * 3));
    }

    @Test
    public void zeroYGivesZero() {
        assertThat(imageCoordinateTranslator.toImageY(0), is(0));
    }

    @Test
    public void imageYMultipliesByRoomHeight() {
        var imageX = imageCoordinateTranslator.toImageY(2);
        verify(settings).roomImgHeight();
        assertThat(imageX, is(2 * 5));
    }
}