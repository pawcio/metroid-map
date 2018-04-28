package pl.kwako.metroid_map.persistence;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;

public class ObjectRepository {

    private final Jsonb jsonb;

    public ObjectRepository() {
        jsonb = JsonbBuilder.create();
    }

    public void toFile(Object object, String filePath) {
        try (var fileWriter = new FileWriter(filePath)) {
            jsonb.toJson(object, fileWriter);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public <T> T fromResource(String resourcePath, Class<T> outputType) {
        try (var resourceStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            return jsonb.fromJson(resourceStream, outputType);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
