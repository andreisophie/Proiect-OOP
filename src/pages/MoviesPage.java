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
    public MoviesPage() {
        final MovieList availableMovies = new MovieList();
        availableMovies.getMovies().addAll(MoviesPage.getAvailableMovies());
        Database.getInstance().setCurrentMovies(availableMovies);
    }

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
            case "see details" -> {
                final String movieName = Database.getInstance().getCurrentAction().getMovie();
                for (final Movie movie : Database.getInstance().getCurrentMovies().getMovies()) {
                    if (movie.getName().equals(movieName)) {
                        Database.getInstance().setCurrentPage(new MovieDetailsPage(movie));
                        return Helpers.createError(false);
                    }
                }
                return Helpers.createError(true);
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
            case "search" -> {
                final MovieList searchedMovies = new MovieList();
                searchedMovies.getMovies().addAll(
                    getSearchMovies(Database.getInstance().getCurrentAction().getStartsWith())
                );
                Database.getInstance().setCurrentMovies(searchedMovies);
                return Helpers.createError(false);
            }
            case "filter" -> {
                final MovieList filteredMovies = new MovieList();
                filteredMovies.getMovies().addAll(getFilteredMovies());
                Database.getInstance().setCurrentMovies(filteredMovies);
                return Helpers.createError(false);
            }
            default -> {
                return Helpers.createError(true);
            }
        }
    }

    /**
     * @return an ArrayList of movies available to the current user
     */
    public static ArrayList<Movie> getAvailableMovies() {
        final ArrayList<Movie> availablMovies = new ArrayList<>();
        final String userCountry =
            Database.getInstance().getCurrentUser().getCredentials().getCountry();
        for (final Movie movie : Database.getInstance().getAllMovies().getMovies()) {
            if (!movie.getCountriesBanned().contains(userCountry)) {
                availablMovies.add(movie);
            }
        }
        return availablMovies;
    }

    /**
     * performs a search in the list of available movies
     * @param startsWith String that search result Strings must start with
     * @return ArrayList of movies that fir crteria
     */
    public static ArrayList<Movie> getSearchMovies(final String startsWith) {
        final ArrayList<Movie> availableMovies = getAvailableMovies();
        final ArrayList<Movie> searchedMovies = new ArrayList<>();
        for (final Movie movie : availableMovies) {
            if (movie.getName().startsWith(startsWith)) {
                searchedMovies.add(movie);
            }
        }
        return searchedMovies;
    }

    /**
     * filters movies that are currently available to user and sorts them, if necessary
     * @return ArrayList of filteres and sorted movies
     */
    public static ArrayList<Movie> getFilteredMovies() {
        final ArrayList<Movie> availableMovies = getAvailableMovies();
        final ArrayList<Movie> filteredMovies = new ArrayList<>();
        final FiltersInput criteria = Database.getInstance().getCurrentAction().getFilters();
        boolean validMovie;
        if (criteria.getContains() == null || (
            criteria.getContains().getActors() == null
            && criteria.getContains().getGenre() == null
        )) {
            filteredMovies.addAll(availableMovies);
        } else {
            for (final Movie movie : availableMovies) {
                validMovie = true;
                if (criteria.getContains().getActors() != null) {
                    for (final String actor : criteria.getContains().getActors()) {
                        if (!movie.getActors().contains(actor)) {
                            validMovie = false;
                            break;
                        }
                    }
                }
                if (criteria.getContains().getGenre() != null) {
                    for (final String genre : criteria.getContains().getGenre()) {
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
            public int compare(final Movie o1, final Movie o2) {
                final FiltersInput criteria =
                    Database.getInstance().getCurrentAction().getFilters();
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
                final int ratingCmp = Double.compare(o1.getRating(), o2.getRating());
                final int durationCmp = o1.getDuration() - o2.getDuration();
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
