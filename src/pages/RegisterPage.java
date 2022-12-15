package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Database;
import database.User;
import helpers.Helpers;

public class RegisterPage extends Page {
    public RegisterPage() {
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
            case "register" -> {
                final User newUser =
                    new User(Database.getInstance().getCurrentAction().getCredentials());
                for (final User user : Database.getInstance().getUsers()) {
                    if (user.getCredentials().getName().equals(
                        newUser.getCredentials().getName()
                    )) {
                        return Helpers.createError(true);
                    }
                }
                Database.getInstance().getUsers().add(newUser);
                Database.getInstance().setCurrentUser(newUser);
                Database.getInstance().setCurrentPage(new LoggedInHomepage());
                return Helpers.createError(false);
            }
            default -> {
                return Helpers.createError(true);
            }
        }
    }
}
