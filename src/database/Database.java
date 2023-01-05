package database;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.node.ObjectNode;

import command.Commander;
import command.DatabaseSnapshot;
import database.Credentials.AccountType;
import helpers.Helpers;
import input.ActionsInput;
import input.Input;
import input.UserInput;
import pages.LoggedOutHomepage;
import pages.MovieDetailsPage;
import pages.MoviesPage;
import pages.Page;

public final class Database {
    private static Database instance = null;

    private ArrayList<User> users;
    private MovieList allMovies;
    private MovieList currentMovies;
    private User currentUser;
    private Page currentPage;
    private ActionsInput currentAction;
    private Commander commander;

    private Database() {
        users = new ArrayList<>();
        currentMovies = new MovieList();
        currentUser = null;
        currentPage = new LoggedOutHomepage();
        currentAction = null;
        commander = null;
    }

    /**
     * @return current instance of Singleton database
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Function that reads users and movies from input
     * and initializes current Database instance with those values
     * @param input object from which to read users and movies
     */
    public static void initializeDatabase(final Input input) {
        for (final UserInput userInput : input.getUsers()) {
            Database.getInstance().getUsers().add(new User(userInput));
        }
        Database.getInstance().allMovies = new MovieList(input.getMovies());
    }

    /**
     * Cleans current Database instance
     */
    public static void cleanupDatabase() {
        Database.instance = null;
    }

    public ObjectNode runAction(final String feature) {
        switch(feature) {
            case "add" -> {
                return addMovie();
            }
            case "delete" -> {
                return deleteMovie();
            }
            default -> {
                System.out.println("Unknown database feature " + feature);
                return Helpers.createError(true);
            }
        }
    }

    private ObjectNode addMovie() {
        Movie newMovie = new Movie(Database.getInstance().getCurrentAction().getAddedMovie());
        for (Movie movie : Database.getInstance().getAllMovies().getMovies()) {
            if (movie.getName().equals(newMovie.getName())) {
                return Helpers.createError(true);
            }
        }
        Database.getInstance().getAllMovies().getMovies().add(newMovie);
        // TODO: notify observers of each genre
        return null;
    }

    private ObjectNode deleteMovie() {
        String movieName = Database.getInstance().getCurrentAction().getDeletedMovie();
        Movie removedMovie = null;
        for (Movie movie : Database.getInstance().getAllMovies().getMovies()) {
            if (movie.getName().equals(movieName)) {
                removedMovie = movie;
            }
        }
        if (removedMovie == null) {
            return Helpers.createError(true);
        }
        for (User user : Database.getInstance().getUsers()) {
            if (user.getPurchasedMovies().getMovies().contains(removedMovie)) {
                if (user.getCredentials().getAccountType().equals(AccountType.premium)) {
                    user.setNumFreeMovies(user.getNumFreeMovies() + 1);
                } else {
                    user.setTokens(user.getTokens() + 2);
                }
                user.getPurchasedMovies().getMovies().remove(removedMovie);
            }
            user.getWatchedMovies().getMovies().remove(removedMovie);
            user.getLikedMovies().getMovies().remove(removedMovie);
            user.getRatedMovies().getMovies().remove(removedMovie);
        }
        Database.getInstance().getAllMovies().getMovies().remove(removedMovie);
        return null;
    }

    public ObjectNode undoPage() {
        if (commander == null) {
            return Helpers.createError(true);
        }
        if (commander.getQueue().size() == 0) {
            return Helpers.createError(true);
        }

        DatabaseSnapshot lastSnapshot = commander.getQueue().remove(0);
        Database.getInstance().setCurrentPage(lastSnapshot.getCurrentPage());
        if (lastSnapshot.getCurrentPage() instanceof MoviesPage) {
            Database.getInstance().setCurrentPage(new MoviesPage());
            return Helpers.createError(false);
        }
        if (lastSnapshot.getCurrentPage() instanceof MovieDetailsPage) {
            Database.getInstance().setCurrentPage(new MovieDetailsPage(
                ((MovieDetailsPage)lastSnapshot.getCurrentPage()).getSelectedMovie()
            ));
            return Helpers.createError(false);
        }

        return null;
    }

    public MovieList getCurrentMovies() {
        return currentMovies;
    }

    public void setCurrentMovies(final MovieList currentMovies) {
        this.currentMovies = currentMovies;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final Page currentPage) {
        this.currentPage = currentPage;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    public MovieList getAllMovies() {
        return allMovies;
    }

    public void setAllMovies(final MovieList allMovies) {
        this.allMovies = allMovies;
    }

    public ActionsInput getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(final ActionsInput currentAction) {
        this.currentAction = currentAction;
    }

    public Commander getCommander() {
        return commander;
    }

    public void setCommander(Commander commander) {
        this.commander = commander;
    }    
}
