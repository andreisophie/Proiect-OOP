package notifications.observer;

import java.util.ArrayList;

public abstract class MySubject {
    private ArrayList<MyObserver> observers = new ArrayList<>();

    public void attach(MyObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (MyObserver observer : observers) {
            observer.update();
        }
    }
}
