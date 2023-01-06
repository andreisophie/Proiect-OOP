package notifications.observer;

import java.util.ArrayList;

import database.Movie;

public abstract class MySubject {
    private final ArrayList<MyObserver> observers = new ArrayList<>();

    /**
     * Attaches a new observer to this subject
     * @param observer the observer to be attached
     */
    public void attach(final MyObserver observer) {
        observers.add(observer);
    }

    /**
     * Notifies all observers of a change, executing a specific action
     */
    public void notifyObservers(final Movie newMovie) {
        for (final MyObserver observer : observers) {
            observer.update(newMovie);
        }
    }
}
