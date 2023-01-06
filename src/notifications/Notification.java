package notifications;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import database.JSONable;
import helpers.Helpers;

public class Notification implements JSONable {
    private String movieName;
    private String message;

    public Notification(final String movieName, final String message) {
        this.movieName = movieName;
        this.message = message;
    }

    /**
     * Returns a JsonNode object which contains relevant data from this class
     * To be used for output
     */
    @Override
    public JsonNode toJSON() {
        final ObjectNode output = Helpers.OBJECT_MAPPER.createObjectNode();

        output.put("movieName", movieName);
        output.put("message", message);

        return output;
    }

    /**
     * @return the name of the movie mentioned by this notification
     */
    public String getMovieName() {
        return movieName;
    }

    /**
     * @return the message of this notification
     */
    public String getMessage() {
        return message;
    }
}
