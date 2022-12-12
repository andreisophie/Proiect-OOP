package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Database;
import database.User;
import helpers.Helpers;

public class LoginPage extends Page {

    @Override
    public ObjectNode changePage(String target) {
        return Helpers.createError(true);
    }

    @Override
    public ObjectNode action(String feature) {
        switch (feature) {
            case "login" -> {
                String username = Database.getInstance().getCurrentAction().getCredentials().getName();
                String password = Database.getInstance().getCurrentAction().getCredentials().getPassword();
                for (User user : Database.getInstance().getUsers()) {
                    if (user.getCredentials().getName().equals(username)) {
                        if (user.getCredentials().getPassword().equals(password)) {
                            Database.getInstance().setCurrentUser(user);
                            Database.getInstance().setCurrentPage(new LoggedInHomepage());
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
            default -> { return Helpers.createError(true);}
        }
    }

    
       
}
