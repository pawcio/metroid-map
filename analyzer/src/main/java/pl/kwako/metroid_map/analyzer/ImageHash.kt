package pl.kwako.metroid_map.analyzer

import java.awt.image.RenderedImage
import java.io.ByteArrayOutputStream
import java.math.BigInteger
import java.security.MessageDigest
import javax.imageio.ImageIO

class ImageHash {
    fun getImageHash(roomImage: RenderedImage): String {
        val md5 = MessageDigest.getInstance("MD5")

        val byteArrayStream = ByteArrayOutputStream()
        ImageIO.write(roomImage, "png", byteArrayStream)
        val bytes = byteArrayStream.toByteArray()

        val md5Bytes = md5.digest(bytes)
        val md5bigInt = BigInteger(1, md5Bytes)
        return md5bigInt.toString(16)
    }
}
