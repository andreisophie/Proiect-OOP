package command;

import java.util.ArrayList;

import database.Database;

public class Commander {
    private ArrayList<DatabaseSnapshot> queue;

    public Commander() {
        queue = new ArrayList<>();
    }

    public void addSnapshot(Database instance) {
        queue.add(0, new DatabaseSnapshot(instance));
    }

    public void removeSnapshot() {
        queue.remove(0);
    }

    public ArrayList<DatabaseSnapshot> getQueue() {
        return queue;
    }

    public void setQueue(ArrayList<DatabaseSnapshot> queue) {
        this.queue = queue;
    }
}
