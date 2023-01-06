package command;

import java.util.ArrayList;

import database.Database;

public class Commander {
    private ArrayList<DatabaseSnapshot> queue;

    public Commander() {
        queue = new ArrayList<>();
        pushSnapshot();
    }

    public void pushSnapshot() {
        queue.add(0, new DatabaseSnapshot(Database.getInstance()));
    }

    public void popSnapshot() {
        queue.remove(0);
    }

    public DatabaseSnapshot peekSnapshot() {
        return queue.get(0);
    }

    public ArrayList<DatabaseSnapshot> getQueue() {
        return queue;
    }

    public void setQueue(ArrayList<DatabaseSnapshot> queue) {
        this.queue = queue;
    }
}
