package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Database;
import helpers.Helpers;

public class LoggedInHomepage extends Page {
    public LoggedInHomepage() {
    }

    /**
     * changes a page, if possible, depending on the argument received
     * @param target name of target page
     * @return JsonNode containing relevant information (if any) after exectuing instruction
     */
    @Override
    public ObjectNode changePage(final String target) {
        switch (target) {
            case "logout" -> {
                Helpers.logout();
                return null;
            }
            case "movies" -> {
                Database.getInstance().setCurrentPage(new MoviesPage());
                return Helpers.createError(false);
            }
            case "upgrades" -> {
                Database.getInstance().setCurrentPage(new UpgradesPage());
                return null;
            }
            default -> {
                return Helpers.createError(true);
            }
        }
    }

    /**
     * executes an action, is possible
     * @param feature name of action to be executed
     * @return JsonNode containing relevant information (if any) after exectuing instruction
     */
    @Override
    public ObjectNode action(final String feature) {
        return Helpers.createError(true);
    }
}
