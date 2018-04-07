package pl.kwako.metroid_map.analyzer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ImageHash {
    public String getImageHash(BufferedImage roomImage) throws NoSuchAlgorithmException, IOException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ImageIO.write(roomImage, "png", byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();

        byte[] md5Bytes = md5.digest(bytes);

        BigInteger md5bigInt = new BigInteger(1, md5Bytes);
        return md5bigInt.toString(16);
    }
}
