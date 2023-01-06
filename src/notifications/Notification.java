package notifications;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import database.JSONable;
import helpers.Helpers;

public class Notification implements JSONable {
    private String movieName;
    private String message;

    public Notification(String movieName, String message) {
        this.movieName = movieName;
        this.message = message;
    }

    @Override
    public JsonNode toJSON() {
        final ObjectNode output = Helpers.OBJECT_MAPPER.createObjectNode();

        output.put("movieName", movieName);
        output.put("message", message);

        return output;
    }
    
    // TODO: might not be necessary these getters and setters
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
