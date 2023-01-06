package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

import command.Commander;
import database.Database;
import database.User;
import helpers.Helpers;

public class LoginPage extends Page {
    public LoginPage() {
    }

    /**
     * changes a page, if possible, depending on the argument received
     * @param target name of target page
     * @return JsonNode containing relevant information (if any) after exectuing instruction
     */
    @Override
    public ObjectNode changePage(final String target) {
        return Helpers.createError(true);
    }

    /**
     * executes an action, is possible
     * @param feature name of action to be executed
     * @return JsonNode containing relevant information (if any) after exectuing instruction
     */
    @Override
    public ObjectNode action(final String feature) {
        switch (feature) {
            case "login" -> {
                final String username =
                    Database.getInstance().getCurrentAction().getCredentials().getName();
                final String password =
                    Database.getInstance().getCurrentAction().getCredentials().getPassword();
                for (final User user : Database.getInstance().getUsers()) {
                    if (user.getCredentials().getName().equals(username)) {
                        if (user.getCredentials().getPassword().equals(password)) {
                            Database.getInstance().setCurrentUser(user);
                            Database.getInstance().setCurrentPage(new LoggedInHomepage());
                            Database.getInstance().setCommander(new Commander());
                            return Helpers.createError(false);
                        } else {
                            Database.getInstance().setCurrentPage(new LoggedOutHomepage());
                            return Helpers.createError(true);
                        }
                    }
                }
                Database.getInstance().setCurrentPage(new LoggedOutHomepage());
                return Helpers.createError(true);
            }
            default -> {
                return Helpers.createError(true);
            }
        }
    }
}
