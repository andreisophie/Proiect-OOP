package database;

import com.fasterxml.jackson.databind.JsonNode;

public interface JSONable {
    JsonNode toJSON();
}
