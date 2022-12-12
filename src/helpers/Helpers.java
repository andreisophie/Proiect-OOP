package helpers;

import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Database;
import database.MovieList;
import pages.LoggedOutHomepage;

public class Helpers {
    private Helpers() {}

    public static final ObjectMapper objectMapper = new ObjectMapper();

    static public ArrayNode StringListToJSON(ArrayList<String> list) {
        ArrayNode listNode = Helpers.objectMapper.createArrayNode();
        for (String string : list) {
            listNode.add(string);
        }
        return listNode;
    }

    public static ObjectNode createError(boolean errorType) {
        ObjectNode errorNode = Helpers.objectMapper.createObjectNode();

        errorNode.put("error", errorType ? "Error" : null);
        errorNode.set("currentMoviesList", Database.getInstance().getCurrentMovies().toJSON());
        errorNode.set("currentUser", errorType ? null : Database.getInstance().getCurrentUser() != null ? Database.getInstance().getCurrentUser().toJSON() : null);

        return errorNode;
    }

    public static void logout() {
        Database.getInstance().setCurrentMovies(new MovieList());
        Database.getInstance().setCurrentUser(null);
        Database.getInstance().setCurrentPage(new LoggedOutHomepage());
    }

    public static void runAction(ArrayNode output) {
        ObjectNode result = null;
        if (Database.getInstance().getCurrentAction().getType().equals("change page")) {
            String target = Database.getInstance().getCurrentAction().getPage();
            result = Database.getInstance().getCurrentPage().changePage(target);
        } else {
            String feature = Database.getInstance().getCurrentAction().getFeature();
            result = Database.getInstance().getCurrentPage().action(feature);
        }
        if (result != null) {
            output.add(result);
        }
    }
}
