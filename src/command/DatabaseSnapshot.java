package command;

import database.Database;
import database.MovieList;
import pages.Page;

public class DatabaseSnapshot {
    private Page currentPage;
    private MovieList currentMovies;

    public DatabaseSnapshot(Database instance) {
        this.currentPage = Database.getInstance().getCurrentPage();
        this.currentMovies = new MovieList(Database.getInstance().getCurrentMovies());
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
    }

    public MovieList getCurrentMovies() {
        return currentMovies;
    }

    public void setCurrentMovies(MovieList currentMovies) {
        this.currentMovies = currentMovies;
    }
}
