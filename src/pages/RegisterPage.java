package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Database;
import database.User;
import helpers.Helpers;

public class RegisterPage extends Page {

    @Override
    public ObjectNode changePage(String target) {
        System.out.println("invalid change page");
        return Helpers.createError(true);
    }

    @Override
    public ObjectNode action(String feature) {
        switch (feature) {
            case "register" -> {
                User newUser = new User(Database.getInstance().getCurrentAction().getCredentials());
                Database.getInstance().getUsers().add(newUser);
                Database.getInstance().setCurrentUser(newUser);
                return Helpers.createError(false);
            }
            default -> { return Helpers.createError(true);}
        }
    }

    
}
