package database;

import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Credentials.AccountType;
import helpers.Helpers;
import input.UserInput;

public class User implements JSONable{
    private Credentials credentials;
    private int tokens;
    private int numFreeMovies;
    MovieList purchasedMovies;
    MovieList watchedMovies;
    MovieList likedMovies;
    MovieList ratedMovies;

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

    
    
}
