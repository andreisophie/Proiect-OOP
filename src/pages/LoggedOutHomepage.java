package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Database;
import helpers.Helpers;

public class LoggedOutHomepage extends Page {
    public LoggedOutHomepage() {
    }

    /**
     * changes a page, if possible, depending on the argument received
     * @param target name of target page
     * @return JsonNode containing relevant information (if any) after exectuing instruction
     */
    @Override
    public ObjectNode changePage(final String target) {
        switch (target) {
            case "login" -> {
                Database.getInstance().setCurrentPage(new LoginPage());
                return null;
            }
            case "register" -> {
                Database.getInstance().setCurrentPage(new RegisterPage());
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
