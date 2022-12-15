package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Database;
import database.Credentials.AccountType;
import helpers.Helpers;

public class UpgradesPage extends Page {
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
        switch (feature) {
            case "buy tokens" -> {
                final int count =
                    Integer.parseInt(Database.getInstance().getCurrentAction().getCount());
                final int userBalance =
                    Database.getInstance().getCurrentUser().getCredentials().getBalance();
                if (userBalance < count) {
                    return Helpers.createError(true);
                }
                final int userTokens = Database.getInstance().getCurrentUser().getTokens();
                Database.getInstance().getCurrentUser().getCredentials().setBalance(
                    userBalance - count
                );
                Database.getInstance().getCurrentUser().setTokens(userTokens + count);
                return null;
            }
            case "buy premium account" -> {
                final int price = 10;
                final int userTokens = Database.getInstance().getCurrentUser().getTokens();
                if (userTokens < price) {
                    return Helpers.createError(true);
                }
                Database.getInstance().getCurrentUser().setTokens(userTokens - price);
                Database.getInstance().getCurrentUser().getCredentials().setAccountType(
                    AccountType.premium
                );
                return null;
            }
            default -> {
                return Helpers.createError(true);
            }
        }
    }
}
