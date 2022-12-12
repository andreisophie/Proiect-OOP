package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

import helpers.Helpers;

public class LoggedInHomepage extends Page {
    @Override
    public ObjectNode changePage(String target) {
        switch (target) {
            case "logout" -> {
                Helpers.logout();
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
