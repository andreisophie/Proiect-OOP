package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Database;
import database.MovieList;
import database.Credentials.AccountType;
import helpers.Helpers;

public class UpgradesPage extends Page {

    @Override
    public ObjectNode action(String feature) {
        switch (feature) {
            case "buy tokens" -> {
                int count = Integer.parseInt(Database.getInstance().getCurrentAction().getCount());
                int userBalance = Database.getInstance().getCurrentUser().getCredentials().getBalance();
                if ( userBalance < count) {
                    return Helpers.createError(true);
                }
                int userTokens = Database.getInstance().getCurrentUser().getTokens();
                Database.getInstance().getCurrentUser().getCredentials().setBalance(userBalance - count);
                Database.getInstance().getCurrentUser().setTokens(userTokens + count);
                return null;
            }
            case "buy premium account" -> {
                int price = 10;
                int userTokens = Database.getInstance().getCurrentUser().getTokens();
                if ( userTokens < price) {
                    return Helpers.createError(true);
                }
                Database.getInstance().getCurrentUser().setTokens(userTokens - price);
                Database.getInstance().getCurrentUser().getCredentials().setAccountType(AccountType.premium);
                return null;
            }
            default -> { return Helpers.createError(true); }
        }
    }

    @Override
    public ObjectNode changePage(String target) {
        switch (target) {
            case "logout" -> {
                Helpers.logout();
                return null;
            }
            case "movies" -> {
                MovieList availableMovies = new MovieList();
                availableMovies.getMovies().addAll(MoviesPage.getAvailableMovies());
                Database.getInstance().setCurrentMovies(availableMovies);
                Database.getInstance().setCurrentPage(new MoviesPage());
                return Helpers.createError(false);
            }
            default -> { return Helpers.createError(true); }
        }
    }
    
}
