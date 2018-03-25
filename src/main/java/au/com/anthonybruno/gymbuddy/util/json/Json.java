package au.com.anthonybruno.gymbuddy.util.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class Json {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final JsonNode jsonNode;

    public Json(String json)  {
        try {
            jsonNode = objectMapper.readTree(json);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public ObjectNode asObject() {
        return (ObjectNode) jsonNode;
    }

    public ArrayNode asArray() {
        return (ArrayNode) jsonNode;
    }
}
