package database;

import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Credentials.AccountType;
import helpers.Helpers;
import input.CredentialsInput;
import input.UserInput;

public class User implements JSONable{
    private Credentials credentials;
    private int tokens;
    private int numFreeMovies;
    private MovieList purchasedMovies;
    private MovieList watchedMovies;
    private MovieList likedMovies;
    private MovieList ratedMovies;

    public User(UserInput userInput) {
        this.credentials = new Credentials(userInput.getCredentials());
        this.tokens = 0;
        if (this.credentials.getAccountType() == AccountType.premium) {
            this.numFreeMovies = 15;
        } else {
            this.numFreeMovies = 0;
        }
        purchasedMovies = new MovieList();
        watchedMovies = new MovieList();
        likedMovies = new MovieList();
        ratedMovies = new MovieList();
    }

    public User(CredentialsInput credentialsInput) {
        this.credentials = new Credentials(credentialsInput);
        this.tokens = 0;
        if (this.credentials.getAccountType() == AccountType.premium) {
            this.numFreeMovies = 15;
        } else {
            this.numFreeMovies = 0;
        }
        purchasedMovies = new MovieList();
        watchedMovies = new MovieList();
        likedMovies = new MovieList();
        ratedMovies = new MovieList();
    }

    @Override
    public ObjectNode toJSON() {
        ObjectNode output = Helpers.objectMapper.createObjectNode();
        
        output.set("credentials", this.credentials.toJSON());
        output.put("tokensCount", this.tokens);
        output.put("numFreePremiumMovies", this.numFreeMovies);
        output.set("purchasedMovies", this.purchasedMovies.toJSON());
        output.set("watchedMovies", this.watchedMovies.toJSON());
        output.set("likedMovies", this.likedMovies.toJSON());
        output.set("ratedMovies", this.ratedMovies.toJSON());
        
        return output;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public int getNumFreeMovies() {
        return numFreeMovies;
    }

    public void setNumFreeMovies(int numFreeMovies) {
        this.numFreeMovies = numFreeMovies;
    }

    public MovieList getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(MovieList purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public MovieList getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(MovieList watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public MovieList getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(MovieList likedMovies) {
        this.likedMovies = likedMovies;
    }

    public MovieList getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(MovieList ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    
    
}
