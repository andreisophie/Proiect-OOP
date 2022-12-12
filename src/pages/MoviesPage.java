package pages;

import java.util.ArrayList;
import java.util.Comparator;

import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Database;
import database.Movie;
import database.MovieList;
import helpers.Helpers;
import input.FiltersInput;

public class MoviesPage extends Page {
    @Override
    public ObjectNode action(String feature) {
        switch (feature) {
            case "search" -> {
                MovieList searchedMovies = new MovieList();
                searchedMovies.getMovies().addAll(getSearchMovies(Database.getInstance().getCurrentAction().getStartsWith()));
                Database.getInstance().setCurrentMovies(searchedMovies);
                return Helpers.createError(false);
            }
            case "filter" -> {
                MovieList filteredMovies = new MovieList();
                filteredMovies.getMovies().addAll(getFilteredMovies());
                Database.getInstance().setCurrentMovies(filteredMovies);
                return Helpers.createError(false);
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

    public static ArrayList<Movie> getFilteredMovies() {
        ArrayList<Movie> availableMovies = getAvailableMovies();
        ArrayList<Movie> filteredMovies = new ArrayList<>();
        FiltersInput criteria = Database.getInstance().getCurrentAction().getFilters();
        for (Movie movie : availableMovies) {
            // filter by contains
            if (criteria.getContains().getActors().size() == 0 &&
                criteria.getContains().getGenre().size() == 0) {
                    filteredMovies.addAll(availableMovies);
            }
        }
        filteredMovies.sort(new Comparator<Movie>() {
            enum Order {
                decreasing,
                increasing
            }
            @Override
            public int compare(Movie o1, Movie o2) {
                FiltersInput criteria = Database.getInstance().getCurrentAction().getFilters();
                Order ratingOrder, durationOrder;
                if (criteria.getSort().getRating().equals("decreasing")) {
                    ratingOrder = Order.decreasing;
                } else {
                    ratingOrder = Order.increasing;
                }
                if (criteria.getSort().getDuration().equals("decreasing")) {
                    durationOrder = Order.decreasing;
                } else {
                    durationOrder = Order.increasing;
                }
                int ratingCmp;
                if (o1.getRating() > o2.getRating()) {
                    ratingCmp = 1;
                } else {
                    if (o1.getRating() == o2.getRating()) {
                        ratingCmp = 0;
                    } else {
                        ratingCmp = -1;
                    }
                }
                int durationCmp = o1.getDuration() - o2.getDuration();
                if (ratingCmp != 0) {
                    return ratingOrder.equals(Order.increasing) ? ratingCmp : -ratingCmp;
                }
                return durationOrder.equals(Order.increasing) ? durationCmp : -durationCmp;
            }
            
        });
        return filteredMovies;
    }
}
