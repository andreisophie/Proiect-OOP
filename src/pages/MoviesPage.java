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
            case "see details" -> {
                String movieName = Database.getInstance().getCurrentAction().getMovie();
                for (Movie movie : Database.getInstance().getCurrentMovies().getMovies()) {
                    if (movie.getName().equals(movieName)) {
                        MovieList selectedMovie = new MovieList();
                        selectedMovie.getMovies().add(movie);
                        Database.getInstance().setCurrentMovies(selectedMovie);
                        Database.getInstance().setCurrentPage(new MovieDetailsPage(movie));
                        return Helpers.createError(false);
                    }
                }
                return Helpers.createError(true);
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
        boolean validMovie;
        if (criteria.getContains() == null || 
            (criteria.getContains().getActors() == null &&
            criteria.getContains().getGenre() == null)) {
                filteredMovies.addAll(availableMovies);
        } else {
            for (Movie movie : availableMovies) {
                validMovie = true;
                if (criteria.getContains().getActors() != null) {
                    for (String actor : criteria.getContains().getActors()) {
                        if (!movie.getActors().contains(actor)) {
                            validMovie = false;
                            break;
                        }
                    }
                }
                if (criteria.getContains().getGenre() != null) {
                    for (String genre : criteria.getContains().getGenre()) {
                        if (!movie.getGenres().contains(genre)) {
                            validMovie = false;
                            break;
                        }
                    }
                }
                if (validMovie) {
                    filteredMovies.add(movie);
                }
            }
        }
        filteredMovies.sort(new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                FiltersInput criteria = Database.getInstance().getCurrentAction().getFilters();
                int ratingOrder, durationOrder;
                if (criteria.getSort() == null) {
                    return 0;
                }
                if (criteria.getSort().getRating() == null) {
                    ratingOrder = 0;
                } else {
                    if (criteria.getSort().getRating().equals("decreasing")) {
                        ratingOrder = -1;
                    } else {
                        ratingOrder = 1;
                    }
                }
                if (criteria.getSort().getDuration() == null) {
                    durationOrder = 0;
                } else {
                    if (criteria.getSort().getDuration().equals("decreasing")) {
                        durationOrder = -1;
                    } else {
                        durationOrder = 1;
                    }
                }
                int ratingCmp =Double.compare(o1.getRating(), o2.getRating());
                int durationCmp = o1.getDuration() - o2.getDuration();
                if (durationOrder != 0 && durationCmp != 0) {
                    return durationOrder * durationCmp;
                }
                if (ratingOrder == 0) {
                    return 0;
                }
                return ratingOrder * ratingCmp;
            }
            
        });
        return filteredMovies;
    }
}
