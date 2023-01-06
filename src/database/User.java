package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;

import helpers.Constants;
import helpers.Helpers;
import input.CredentialsInput;
import input.UserInput;
import notifications.Notification;

public class User implements JSONable {
    private final Credentials credentials;
    private int tokens;
    private int numFreeMovies;
    private MovieList purchasedMovies;
    private MovieList watchedMovies;
    private MovieList likedMovies;
    private MovieList ratedMovies;
    private final Map<Movie, Integer> ratingsMap;
    private final ArrayList<Notification> notifications;

    public User(final UserInput userInput) {
        this.credentials = new Credentials(userInput.getCredentials());
        this.tokens = 0;
        this.numFreeMovies = Constants.NUM_FREE_MOVIES;
        purchasedMovies = new MovieList();
        watchedMovies = new MovieList();
        likedMovies = new MovieList();
        ratedMovies = new MovieList();
        ratingsMap = new HashMap<>();
        notifications = new ArrayList<>();
    }

    public User(final CredentialsInput credentialsInput) {
        this.credentials = new Credentials(credentialsInput);
        this.tokens = 0;
        this.numFreeMovies = Constants.NUM_FREE_MOVIES;
        purchasedMovies = new MovieList();
        watchedMovies = new MovieList();
        likedMovies = new MovieList();
        ratedMovies = new MovieList();
        ratingsMap = new HashMap<>();
        notifications = new ArrayList<>();
    }

    /**
     * Returns a JsonNode object which contains relevant data from this class
     * To be used for output
     */
    @Override
    public ObjectNode toJSON() {
        final ObjectNode output = Helpers.OBJECT_MAPPER.createObjectNode();

        output.set("credentials", this.credentials.toJSON());
        output.put("tokensCount", this.tokens);
        output.put("numFreePremiumMovies", this.numFreeMovies);
        output.set("purchasedMovies", this.purchasedMovies.toJSON());
        output.set("watchedMovies", this.watchedMovies.toJSON());
        output.set("likedMovies", this.likedMovies.toJSON());
        output.set("ratedMovies", this.ratedMovies.toJSON());
        output.set("notifications", Helpers.objectListToJSON(notifications));

        return output;
    }

    /**
     * @return credentials object associated with current user
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * @return number of tokens owned by current user
     */
    public int getTokens() {
        return tokens;
    }

    /**
     * changes number of tokens owned by current user
     * @param tokens new number of tokens to be set
     */
    public void setTokens(final int tokens) {
        this.tokens = tokens;
    }

    /**
     * @return number of free movies current user has access to
     */
    public int getNumFreeMovies() {
        return numFreeMovies;
    }

    /**
     * changes number of free movies current user has access to
     * @param tokens new number of free movies to be set
     */
    public void setNumFreeMovies(final int numFreeMovies) {
        this.numFreeMovies = numFreeMovies;
    }

    /**
     * @return list of movies purchased by current user
     */
    public MovieList getPurchasedMovies() {
        return purchasedMovies;
    }

    /**
     * changes list of movies purchased by current user
     * @param tokens new list of movies to be set
     */
    public void setPurchasedMovies(final MovieList purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    /**
     * @return list of movies watched by current user
     */
    public MovieList getWatchedMovies() {
        return watchedMovies;
    }

    /**
     * changes list of movies watched by current user
     * @param tokens new list of movies to be set
     */
    public void setWatchedMovies(final MovieList watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    /**
     * @return list of movies liked by current user
     */
    public MovieList getLikedMovies() {
        return likedMovies;
    }

    /**
     * changes list of movies liked by current user
     * @param tokens new list of movies to be set
     */
    public void setLikedMovies(final MovieList likedMovies) {
        this.likedMovies = likedMovies;
    }

    /**
     * @return list of movies rated by current user
     */
    public MovieList getRatedMovies() {
        return ratedMovies;
    }

    /**
     * changes list of movies rated by current user
     * @param tokens new list of movies to be set
     */
    public void setRatedMovies(final MovieList ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    /**
     * get map containing ratings this used instance gave to movies
     * @return map with information described above
     */
    public Map<Movie, Integer> getRatingsMap() {
        return ratingsMap;
    }

    /**
     * get the botifications this user has received
     * @return an ArrayList of notifications
     */
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    /**
     * Adds a new notification to the end of this user's list
     * @param notification the new notiication to be added
     */
    public void addNotification(final Notification notification) {
        this.notifications.add(notification);
    }
}
