package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

import helpers.GlobalState;
import helpers.Helpers;

public abstract class Page {
    public ObjectNode changePage(Page target) {
        ObjectNode errorNode = Helpers.objectMapper.createObjectNode();

        errorNode.put("error", "Error");
        errorNode.set("currentMoviesList", GlobalState.getInstance().getCurrentMovies().toJSON());
        errorNode.set("currentUser", GlobalState.getInstance().getCurrentUser().toJSON());

        return errorNode;
    }
}
