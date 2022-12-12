package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Database;
import database.MovieList;
import helpers.Helpers;

public class LoggedInHomepage extends Page {
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
