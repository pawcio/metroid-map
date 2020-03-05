package pl.kwako.metroid_map.persistence

import java.io.FileWriter
import javax.json.bind.Jsonb
import javax.json.bind.JsonbBuilder

class ObjectRepository {

    private val jsonb: Jsonb = JsonbBuilder.create()

    fun toFile(obj: Any, filePath: String) {
        FileWriter(filePath).use {
            jsonb.toJson(obj, it)
        }
    }


    fun <T> fromResource(resourcePath: String, outputType: Class<T>): T {
        javaClass.classLoader.getResourceAsStream(resourcePath).use {
            return jsonb.fromJson(it, outputType)
        }
    }
}
