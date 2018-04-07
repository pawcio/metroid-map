package pl.kwako.metroid_map.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        enable(SerializationFeature.INDENT_OUTPUT);
    }
}
