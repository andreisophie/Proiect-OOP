package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Database;
import database.Movie;
import database.MovieList;
import database.Credentials.AccountType;
import helpers.Helpers;

public class MovieDetailsPage extends Page {
    private Movie selectedMovie;

    public MovieDetailsPage(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
    }

    @Override
    public ObjectNode action(String feature) {
        switch (feature) {
            case "purchase" -> {
                int price = 2;
                int numFreeMovies = Database.getInstance().getCurrentUser().getNumFreeMovies();
                if (Database.getInstance().getCurrentUser().getCredentials().getAccountType().equals(AccountType.premium) && 
                    numFreeMovies > 0) {
                        Database.getInstance().getCurrentUser().setNumFreeMovies(numFreeMovies - 1);
                        Database.getInstance().getCurrentUser().getPurchasedMovies().getMovies().add(this.selectedMovie);
                        return Helpers.createError(false);
                }
                int userTokens = Database.getInstance().getCurrentUser().getTokens();
                if (userTokens < price) {
                    return Helpers.createError(true);
                }
                Database.getInstance().getCurrentUser().setTokens(userTokens - price);
                Database.getInstance().getCurrentUser().getPurchasedMovies().getMovies().add(this.selectedMovie);
                return Helpers.createError(false);
            }
            case "watch" -> {
                if (!Database.getInstance().getCurrentUser().getPurchasedMovies().getMovies().contains(this.selectedMovie)) {
                    return Helpers.createError(true);
                }
                Database.getInstance().getCurrentUser().getWatchedMovies().getMovies().add(this.selectedMovie);
                return Helpers.createError(false);
            }
            case "like" -> {
                if (!Database.getInstance().getCurrentUser().getWatchedMovies().getMovies().contains(this.selectedMovie)) {
                    return Helpers.createError(true);
                }
                Database.getInstance().getCurrentUser().getLikedMovies().getMovies().add(this.selectedMovie);
                this.selectedMovie.setNumLikes(this.selectedMovie.getNumLikes() + 1);
                return Helpers.createError(false);
            }
            case "rate" -> {
                if (!Database.getInstance().getCurrentUser().getWatchedMovies().getMovies().contains(this.selectedMovie)) {
                    return Helpers.createError(true);
                }
                int rating = Integer.parseInt(Database.getInstance().getCurrentAction().getRate());
                if (rating > 5 || rating < 0) {
                    return Helpers.createError(true);
                }
                Database.getInstance().getCurrentUser().getRatedMovies().getMovies().add(this.selectedMovie);
                this.selectedMovie.setNumRatings(this.selectedMovie.getNumRatings() + 1);
                this.selectedMovie.setSumRatings(this.selectedMovie.getSumRatings() + rating);
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
            case "movies" -> {
                MovieList availableMovies = new MovieList();
                availableMovies.getMovies().addAll(MoviesPage.getAvailableMovies());
                Database.getInstance().setCurrentMovies(availableMovies);
                Database.getInstance().setCurrentPage(new MoviesPage());
                return Helpers.createError(false);
            }
            case "upgrades" -> {
                Database.getInstance().setCurrentMovies(new MovieList());
                Database.getInstance().setCurrentPage(new UpgradesPage());
                return null;
            }
            default -> { return Helpers.createError(true); }
        }
    }

    public Movie getSelectedMovie() {
        return selectedMovie;
    }

    public void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
    }

      
}
