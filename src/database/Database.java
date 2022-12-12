package database;

import java.util.ArrayList;

import input.Input;
import input.MovieInput;
import input.UserInput;

public class Database {
    private static Database instance = null;

    ArrayList<User> users;
    ArrayList<Movie> movies;

    private Database() {
        users = new ArrayList<>();
        movies = new ArrayList<>();
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

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public static void initializeDatabase(Input input) {
        for (UserInput userInput : input.getUsers()) {
            Database.getInstance().getUsers().add(new User(userInput));
        }
        for (MovieInput movieInput : input.getMovies()) {
            Database.getInstance().getMovies().add(new Movie(movieInput));
        }
    }
}
