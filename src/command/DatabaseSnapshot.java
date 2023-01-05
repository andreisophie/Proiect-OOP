package command;

import database.Database;
import pages.Page;

public class DatabaseSnapshot {
    private Page currentPage;

    public DatabaseSnapshot(Database instance) {
        this.currentPage = Database.getInstance().getCurrentPage();
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
    }
}
