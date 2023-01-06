package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Database;
import database.Movie;
import database.MovieList;
import database.User;
import database.Credentials.AccountType;
import helpers.Constants;
import helpers.Helpers;

public class MovieDetailsPage extends Page {
    private final Movie selectedMovie;

    public MovieDetailsPage(final Movie selectedMovie) {
        final MovieList newAvailableMovies = new MovieList();
        newAvailableMovies.getMovies().add(selectedMovie);
        Database.getInstance().setCurrentMovies(newAvailableMovies);
        this.selectedMovie = selectedMovie;
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
            case "movies" -> {
                Database.getInstance().setCurrentPage(new MoviesPage());
                return Helpers.createError(false);
            }
            case "upgrades" -> {
                // need to clear the list of available movies first
                Database.getInstance().setCurrentMovies(new MovieList());
                Database.getInstance().setCurrentPage(new UpgradesPage());
                return null;
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
            case "purchase" -> {
                final int price = 2;
                final User currentUser = Database.getInstance().getCurrentUser();
                final int numFreeMovies = currentUser.getNumFreeMovies();

                if (currentUser.getPurchasedMovies().getMovies().contains(this.selectedMovie)) {
                    return Helpers.createError(true);
                }

                if (currentUser.getCredentials().getAccountType().equals(AccountType.premium)
                    && numFreeMovies > 0) {
                        currentUser.setNumFreeMovies(numFreeMovies - 1);
                        currentUser.getPurchasedMovies().getMovies().add(this.selectedMovie);
                        return Helpers.createError(false);
                }
                final int userTokens = currentUser.getTokens();
                if (userTokens < price) {
                    return Helpers.createError(true);
                }
                currentUser.setTokens(userTokens - price);
                currentUser.getPurchasedMovies().getMovies().add(this.selectedMovie);
                return Helpers.createError(false);
            }
            case "watch" -> {
                final User currentUser = Database.getInstance().getCurrentUser();
                if (!currentUser.getPurchasedMovies().getMovies().contains(this.selectedMovie)) {
                    return Helpers.createError(true);
                }
                if (currentUser.getWatchedMovies().getMovies().contains(this.selectedMovie)) {
                    return Helpers.createError(false);
                }
                currentUser.getWatchedMovies().getMovies().add(this.selectedMovie);
                return Helpers.createError(false);
            }
            case "like" -> {
                final User currentUser = Database.getInstance().getCurrentUser();
                if (!currentUser.getWatchedMovies().getMovies().contains(this.selectedMovie)) {
                    return Helpers.createError(true);
                }
                if (currentUser.getLikedMovies().getMovies().contains(this.selectedMovie)) {
                    return Helpers.createError(false);
                }
                currentUser.getLikedMovies().getMovies().add(this.selectedMovie);
                this.selectedMovie.setNumLikes(this.selectedMovie.getNumLikes() + 1);
                return Helpers.createError(false);
            }
            case "rate" -> {
                final User currentUser = Database.getInstance().getCurrentUser();
                if (!currentUser.getWatchedMovies().getMovies().contains(this.selectedMovie)) {
                    return Helpers.createError(true);
                }
                final int rating =
                    Integer.parseInt(Database.getInstance().getCurrentAction().getRate());
                if (rating > Constants.MAX_RATING || rating < 0) {
                    return Helpers.createError(true);
                }

                if (currentUser.getRatedMovies().getMovies().contains(this.selectedMovie)) {
                    int oldRating = currentUser.getRatingsMap().get(this.selectedMovie).intValue();
                    this.selectedMovie.setSumRatings(
                        (this.selectedMovie.getSumRatings() - oldRating + rating)
                    );
                    return Helpers.createError(false);
                }

                currentUser.getRatedMovies().getMovies().add(this.selectedMovie);
                this.selectedMovie.setNumRatings(this.selectedMovie.getNumRatings() + 1);
                this.selectedMovie.setSumRatings(this.selectedMovie.getSumRatings() + rating);
                currentUser.getRatingsMap().put(this.selectedMovie, Integer.valueOf(rating));
                return Helpers.createError(false);
            }
            default -> {
                return Helpers.createError(true);
            }
        }
    }

    /**
     * @return selected movie on current page
     */
    public Movie getSelectedMovie() {
        return selectedMovie;
    }
}
