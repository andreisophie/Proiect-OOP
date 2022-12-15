package database;

import com.fasterxml.jackson.databind.JsonNode;

public interface JSONable {
    /**
     * Returns a JsonNode object which contains relevant data from class
     * that implements this interface
     * To be used for output
     */
    JsonNode toJSON();
}
