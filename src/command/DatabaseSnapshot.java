package command;

import database.Database;
import database.MovieList;
import pages.Page;

public final class DatabaseSnapshot {
    private Page currentPage;
    private MovieList currentMovies;

    public DatabaseSnapshot(final Database instance) {
        this.currentPage = Database.getInstance().getCurrentPage();
        this.currentMovies = new MovieList(Database.getInstance().getCurrentMovies());
    }

    /**
     * @return the page in this database snapshot
     */
    public Page getCurrentPage() {
        return currentPage;
    }

    /**
     * Sets a new page as current
     * @param currentPage new page to be set as current
     */
    public void setCurrentPage(final Page currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @return the movie list in this database snapshot
     */
    public MovieList getCurrentMovies() {
        return currentMovies;
    }

    /**
     * Sets a new movie list
     * @param currentPage new movie list
     */
    public void setCurrentMovies(final MovieList currentMovies) {
        this.currentMovies = currentMovies;
    }
}
