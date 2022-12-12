package database;

import java.util.ArrayList;
import com.fasterxml.jackson.databind.node.ObjectNode;

import database.Credentials.AccountType;
import helpers.Helpers;
import input.UserInput;

public class User implements JSONable{
    private Credentials credentials;
    private int tokens;
    private int numFreeMovies;
    ArrayList<Movie> purchasedMovies;
    ArrayList<Movie> watchedMovies;
    ArrayList<Movie> likedMovies;
    ArrayList<Movie> ratedMovies;

    public User(UserInput userInput) {
        this.credentials = new Credentials(userInput.getCredentials());
        this.tokens = 0;
        if (this.credentials.getAccountType() == AccountType.premium) {
            this.numFreeMovies = 15;
        } else {
            this.numFreeMovies = 0;
        }
        purchasedMovies = new ArrayList<>();
        watchedMovies = new ArrayList<>();
        likedMovies = new ArrayList<>();
        ratedMovies = new ArrayList<>();
    }

    @Override
    public ObjectNode toJSON() {
        ObjectNode output = Helpers.objectMapper.createObjectNode();
        
        output.set("credentials", this.credentials.toJSON());
        output.put("tokensCount", this.tokens);
        output.put("numFreePremiumMovies", this.numFreeMovies);
        // output.put("purchasedMovies", this.purchasedMovies);
        // output.put("watchedMovies", this.watchedMovies);
        // output.put("likedMovies", this.likedMovies);
        // output.put("ratedMovies", this.ratedMovies);
        
        return output;
    }

    
    
}
