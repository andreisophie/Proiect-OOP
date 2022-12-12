package helpers;
import database.MovieList;
import database.User;
import pages.LoggedOutHomepage;
import pages.Page;

public class GlobalState {
    private static GlobalState instance = null;

    private MovieList currentMovies;
    private User currentUser;
    private Page currentPage;

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

    private GlobalState() {
        currentMovies = new MovieList();
        currentUser = null;
        currentPage = new LoggedOutHomepage();
    }

    public static GlobalState getInstance() {
        if (instance == null) {
            instance = new GlobalState();
        }
        return instance;
    }
}
