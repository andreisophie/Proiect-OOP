package database;

import java.util.ArrayList;

import input.ActionsInput;
import input.Input;
import input.UserInput;
import pages.LoggedOutHomepage;
import pages.Page;

public class Database {
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

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public static void initializeDatabase(Input input) {
        for (UserInput userInput : input.getUsers()) {
            Database.getInstance().getUsers().add(new User(userInput));
        }
        Database.getInstance().allMovies = new MovieList(input.getMovies());
    }

    public static void cleanupDatabase() {
        Database.instance = null;
    }

    public MovieList getCurrentMovies() {
        return currentMovies;
    }

    public void setCurrentMovies(MovieList currentMovies) {
        this.currentMovies = currentMovies;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public MovieList getAllMovies() {
        return allMovies;
    }

    public void setAllMovies(MovieList allMovies) {
        this.allMovies = allMovies;
    }

    public ActionsInput getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(ActionsInput currentAction) {
        this.currentAction = currentAction;
    }
}
