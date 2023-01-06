package notifications.observer;

import database.Movie;

public interface MyObserver {
    /**
     * Action executed by an observer when notified by the subject
     */
    void update(Movie newMovie);
}
