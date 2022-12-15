package database;

import java.util.ArrayList;

import input.ActionsInput;
import input.Input;
import input.UserInput;
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

    private Database() {
        users = new ArrayList<>();
        currentMovies = new MovieList();
        currentUser = null;
        currentPage = new LoggedOutHomepage();
        currentAction = null;
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
}
