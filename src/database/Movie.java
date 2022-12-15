package database;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.node.ObjectNode;

import helpers.Helpers;
import input.MovieInput;

public class Movie implements JSONable {
    private final String name;
    private final int year;
    private final int duration;
    private final ArrayList<String> genres;
    private final ArrayList<String> actors;
    private final ArrayList<String> countriesBanned;
    private int numLikes;
    private int numRatings;
    private int sumRatings;

    public Movie(final MovieInput movieInput) {
        this.name = movieInput.getName();
        this.year = movieInput.getYear();
        this.duration = movieInput.getDuration();
        this.genres = new ArrayList<>(movieInput.getGenres());
        this.actors = new ArrayList<>(movieInput.getActors());
        this.countriesBanned = new ArrayList<>(movieInput.getCountriesBanned());
        this.numLikes = 0;
        this.numRatings = 0;
        this.sumRatings = 0;
    }

    /**
     * @return number of likes received by this movie
     */
    public int getNumLikes() {
        return numLikes;
    }

    /**
     * Sets the number of likes in this instance to a new value
     * @param numLikes new number of likes to be set
     */
    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    /**
     * @return number of ratings received by this movie
     */
    public int getNumRatings() {
        return numRatings;
    }

    /**
     * Sets the number of ratings in this instance to a new value
     * @param numLikes new number of ratings to be set
     */
    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

    /**
     * @return sum of ratings received by this movie
     */
    public int getSumRatings() {
        return sumRatings;
    }

    /**
     * Sets the sum of ratings in this instance to a new value
     * @param numLikes new sum to be set
     */
    public void setSumRatings(final int sumRatings) {
        this.sumRatings = sumRatings;
    }

    /**
     * @return name of this movie
     */
    public String getName() {
        return name;
    }

    /**
     * @return production year this movie
     */
    public int getYear() {
        return year;
    }

    /**
     * @return duration of this movie
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @return genreas associated this movie
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * @return actors starring in this movie
     */
    public ArrayList<String> getActors() {
        return actors;
    }

    /**
     * @return countries where this movie is banned
     */
    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    /**
     * @return rating of this movie, calculated dynamically
     */
    public double getRating() {
        return numRatings == 0 ? 0 : (double) sumRatings / numRatings;
    }

    /**
     * Returns a JsonNode object which contains relevant data from this class
     * To be used for output
     */
    @Override
    public ObjectNode toJSON() {
        final ObjectNode output = Helpers.OBJECT_MAPPER.createObjectNode();

        output.put("name", this.name);
        output.put("year", this.year);
        output.put("duration", this.duration);
        output.set("genres", Helpers.stringListToJSON(this.genres));
        output.set("actors", Helpers.stringListToJSON(this.actors));
        output.set("countriesBanned", Helpers.stringListToJSON(this.countriesBanned));
        output.put("numLikes", this.numLikes);
        output.put("rating", getRating());
        output.put("numRatings", this.numRatings);

        return output;
    }
}
