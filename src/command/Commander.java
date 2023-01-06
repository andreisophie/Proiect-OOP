package command;

import java.util.ArrayList;

import database.Database;

public class Commander {
    private final ArrayList<DatabaseSnapshot> stack;

    public Commander() {
        stack = new ArrayList<>();
        pushSnapshot();
    }

    /**
     * Adds the current page to the stack of pages
     */
    public void pushSnapshot() {
        stack.add(0, new DatabaseSnapshot(Database.getInstance()));
    }

    /**
     * @return the latest DatabaseSnapshot at the top of the stack
     * and removes it from the stack
     */
    public DatabaseSnapshot popSnapshot() {
        return stack.remove(0);
    }

    /**
     * @return the latest DatabaseSnapshot at the top of the stack
     * but doesn't remove it
     */
    public DatabaseSnapshot peekSnapshot() {
        return stack.get(0);
    }

    /**
     * @return the stack in this Commander instance
     */
    public ArrayList<DatabaseSnapshot> getStack() {
        return stack;
    }
}
