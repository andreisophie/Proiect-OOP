package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Database;
import helpers.Helpers;

public class LoggedOutHomepage extends Page {
    public ObjectNode changePage(RegisterPage target) {
        Database.getInstance().setCurrentPage(target);
        return null;
    }

    @Override
    public ObjectNode changePage(String target) {
        switch (target) {
            case "login" -> {
                Database.getInstance().setCurrentPage(new LoginPage());
                return null;
            }
            case "register" -> {
                Database.getInstance().setCurrentPage(new RegisterPage());
                return null;
            }
            default -> { return Helpers.createError(true); }
        }
    }

    @Override
    public ObjectNode action(String feature) {
        return Helpers.createError(true);
    }

    
}
