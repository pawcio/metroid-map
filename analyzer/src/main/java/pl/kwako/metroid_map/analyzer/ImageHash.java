package pl.kwako.metroid_map.analyzer;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class ImageHash {
    public String getImageHash(RenderedImage roomImage) {
        try {
            return tryGetImageHash(roomImage);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String tryGetImageHash(RenderedImage roomImage) throws NoSuchAlgorithmException, IOException {
        var md5 = MessageDigest.getInstance("MD5");

        var byteArrayStream = new ByteArrayOutputStream();

        ImageIO.write(roomImage, "png", byteArrayStream);

        var bytes = byteArrayStream.toByteArray();

        var md5Bytes = md5.digest(bytes);

        var md5bigInt = new BigInteger(1, md5Bytes);
        return md5bigInt.toString(16);
    }
}
