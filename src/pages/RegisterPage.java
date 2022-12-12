package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Database;
import database.User;
import helpers.Helpers;

public class RegisterPage extends Page {

    @Override
    public ObjectNode changePage(String target) {
        return Helpers.createError(true);
    }

    @Override
    public ObjectNode action(String feature) {
        switch (feature) {
            case "register" -> {
                User newUser = new User(Database.getInstance().getCurrentAction().getCredentials());
                for (User user : Database.getInstance().getUsers()) {
                    if (user.getCredentials().getName().equals(newUser.getCredentials().getName())) {
                        return Helpers.createError(true);
                    }
                }
                Database.getInstance().getUsers().add(newUser);
                Database.getInstance().setCurrentUser(newUser);
                Database.getInstance().setCurrentPage(new LoggedInHomepage());
                return Helpers.createError(false);
            }
            default -> { return Helpers.createError(true);}
        }
    }

    
}
