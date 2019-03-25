package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonSerializer {
    public <T> T jsonToObject(String s, Class<T> type) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(s, type);
    }

    public <T> String objectToJson(T requestReply) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(requestReply);
    }
}
