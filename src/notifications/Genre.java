package notifications;

import notifications.observer.MySubject;

public class Genre extends MySubject {
    private String genreName;

    public Genre(final String genreName) {
        this.genreName = genreName;
    }

    /**
     * @return name of the genre
     */
    public String getGenreName() {
        return genreName;
    }
}
