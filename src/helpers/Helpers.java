package helpers;

import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Database;
import database.JSONable;
import database.MovieList;
import pages.LoggedOutHomepage;

public final class Helpers {
    private Helpers() { }

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Creates a JsonNode that contains an array of Strings from an ArrayList of Strings
     * @param list ArrayList with Strings
     * @return ArrayNode with Strings formatted for output
     */
    public static ArrayNode stringListToJSON(final ArrayList<String> list) {
        final ArrayNode listNode = Helpers.OBJECT_MAPPER.createArrayNode();
        for (final String string : list) {
            listNode.add(string);
        }
        return listNode;
    }

    /**
     * Creates a JsonNode that contains an array of Objects from an ArrayList of Objects
     * @param list ArrayList with Strings
     * @return ArrayNode with Strings formatted for output
     */
    public static ArrayNode objectListToJSON(final ArrayList<? extends JSONable> list) {
        final ArrayNode listNode = Helpers.OBJECT_MAPPER.createArrayNode();
        for (final JSONable object : list) {
            listNode.add(object.toJSON());
        }
        return listNode;
    }

    /**
     * Creates a JsonNode containing an error message
     * @param errorType should be true for errors, false for informational messages
     * @return an ObjectNode with fields specific for an error
     */
    public static ObjectNode createError(final boolean errorType) {
        final ObjectNode errorNode = Helpers.OBJECT_MAPPER.createObjectNode();

        errorNode.put("error", errorType ? "Error" : null);
        errorNode.set("currentMoviesList", errorType ? new MovieList().toJSON()
            : Database.getInstance().getCurrentMovies().toJSON());
        errorNode.set("currentUser", errorType ? null
            : (Database.getInstance().getCurrentUser() != null
            ? Database.getInstance().getCurrentUser().toJSON() : null));

        return errorNode;
    }

    /**
     * Logs out current user and changed page to a LoggedOutHomepage
     */
    public static void logout() {
        Database.getInstance().setCurrentMovies(new MovieList());
        Database.getInstance().setCurrentUser(null);
        Database.getInstance().setCurrentPage(new LoggedOutHomepage());
        Database.getInstance().setCommander(null);
    }

    /**
     * Runs an action from input
     * @param output ArrayNode where the result (if any) of the action is placed
     */
    public static void runAction(final ArrayNode output) {
        ObjectNode result = null;
        switch (Database.getInstance().getCurrentAction().getType()) {
            case "change page" -> {
                final String target = Database.getInstance().getCurrentAction().getPage();
                if (Database.getInstance().getCurrentUser() != null) {
                    Database.getInstance().getCommander().addSnapshot(Database.getInstance());
                }
                result = Database.getInstance().getCurrentPage().changePage(target);
                if (result != null && result.get("Error") != null) {
                    Database.getInstance().getCommander().removeSnapshot();
                }
            }
            case "on page" -> {
                final String feature = Database.getInstance().getCurrentAction().getFeature();
                result = Database.getInstance().getCurrentPage().action(feature);
            }
            case "database" -> {
                final String feature = Database.getInstance().getCurrentAction().getFeature();
                Database.getInstance().runAction(feature);
            }
            case "back" -> {
                result = Database.getInstance().undoPage();
            }
            default -> System.out.println("Unknown action type " + Database.getInstance().getCurrentAction().getType());
        }
        if (result != null) {
            output.add(result);
        }
    }
}
