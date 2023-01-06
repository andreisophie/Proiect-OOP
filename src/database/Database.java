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
import notifications.Genre;
import pages.LoggedOutHomepage;
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
    private ArrayList<Genre> genreSubjects;

    private Database() {
        users = new ArrayList<>();
        currentMovies = new MovieList();
        currentUser = null;
        currentPage = new LoggedOutHomepage();
        currentAction = null;
        commander = null;
        genreSubjects = new ArrayList<>();
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
        for (Movie movie : Database.getInstance().allMovies.getMovies()) {
            for (String genreName : movie.getGenres()) {
                if (!Helpers.containsGenre(genreName)) {
                    Database.getInstance().getGenreSubjects().add(new Genre(genreName));
                }
            }
        }
    }

    /**
     * Cleans current Database instance
     */
    public static void cleanupDatabase() {
        Database.instance = null;
    }

    /**
     * runs a database action (add and delete movie)
     * @param feature the action to be executed
     * @return an object node containing output (if any)
     */
    public ObjectNode runAction(final String feature) {
        switch (feature) {
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

    /**
     * Adds a move to the database
     * @return an object node containing output (if any)
     */
    private ObjectNode addMovie() {
        final Movie newMovie = new Movie(Database.getInstance().getCurrentAction().getAddedMovie());
        for (final Movie movie : Database.getInstance().getAllMovies().getMovies()) {
            if (movie.getName().equals(newMovie.getName())) {
                return Helpers.createError(true);
            }
        }
        Database.getInstance().getAllMovies().getMovies().add(newMovie);
        for (String genreName : newMovie.getGenres()) {
            if (!Helpers.containsGenre(genreName)) {
                Database.getInstance().getGenreSubjects().add(new Genre(genreName));
            }
        }
        for (Genre genre : Database.getInstance().getGenreSubjects()) {
            if (newMovie.getGenres().contains(genre.getGenreName())) {
                genre.notifyObservers(newMovie);
            }
        }
        return null;
    }

    /**
     * Delete a move from the database
     * @return an object node containing output (if any)
     */
    private ObjectNode deleteMovie() {
        final String movieName = Database.getInstance().getCurrentAction().getDeletedMovie();
        Movie removedMovie = null;
        for (final Movie movie : Database.getInstance().getAllMovies().getMovies()) {
            if (movie.getName().equals(movieName)) {
                removedMovie = movie;
            }
        }
        if (removedMovie == null) {
            return Helpers.createError(true);
        }
        for (final User user : Database.getInstance().getUsers()) {
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

    /**
     * Returns the user to the last accessed page, if any
     * @return an object node containing output (if any)
     */
    public ObjectNode undoPage() {
        if (commander == null) {
            return Helpers.createError(true);
        }
        if (commander.getStack().size() == 1) {
            return Helpers.createError(true);
        }
        commander.popSnapshot();
        final DatabaseSnapshot lastSnapshot = commander.peekSnapshot();
        Database.getInstance().setCurrentPage(lastSnapshot.getCurrentPage());
        Database.getInstance().setCurrentMovies(lastSnapshot.getCurrentMovies());
        if (Database.getInstance().getCurrentMovies().getMovies().size() != 0) {
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

    public void setCommander(final Commander commander) {
        this.commander = commander;
    }

    public ArrayList<Genre> getGenreSubjects() {
        return genreSubjects;
    }

    public void setGenreSubjects(ArrayList<Genre> genreSubjects) {
        this.genreSubjects = genreSubjects;
    }
}
