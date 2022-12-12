package pages;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Database;
import database.Movie;
import database.MovieList;
import helpers.Helpers;

public class MoviesPage extends Page {
    @Override
    public ObjectNode action(String feature) {
        switch (feature) {
            case "search" -> {
                MovieList searchedMovies = new MovieList();
                searchedMovies.getMovies().addAll(getSearchMovies(Database.getInstance().getCurrentAction().getStartsWith()));
                Database.getInstance().setCurrentMovies(searchedMovies);
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
            default -> { return Helpers.createError(true); }
        }
    }

    public static ArrayList<Movie> getAvailableMovies() {
        ArrayList<Movie> availablMovies = new ArrayList<>();
        String userCountry = Database.getInstance().getCurrentUser().getCredentials().getCountry();
        for (Movie movie : Database.getInstance().getAllMovies().getMovies()) {
            if (!movie.getCountriesBanned().contains(userCountry)) {
                availablMovies.add(movie);
            }
        }
        return availablMovies;
    }

    public static ArrayList<Movie> getSearchMovies(String startsWith) {
        ArrayList<Movie> availableMovies = getAvailableMovies();
        ArrayList<Movie> searchedMovies = new ArrayList<>();
        for (Movie movie : availableMovies) {
            if (movie.getName().startsWith(startsWith)) {
                searchedMovies.add(movie);
            }
        }
        return searchedMovies;
    }
}
