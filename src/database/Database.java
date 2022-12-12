package database;

import java.util.ArrayList;

import input.Input;
import input.UserInput;

public class Database {
    private static Database instance = null;

    ArrayList<User> users;
    MovieList allMovies;

    private Database() {
        users = new ArrayList<>();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
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

    public static void initializeDatabase(Input input) {
        for (UserInput userInput : input.getUsers()) {
            Database.getInstance().getUsers().add(new User(userInput));
        }
        Database.getInstance().allMovies = new MovieList(input.getMovies());
    }
}
