package database;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.node.ObjectNode;

import helpers.Helpers;
import input.MovieInput;

public class Movie implements JSONable{
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private int numLikes;
    private int numRatings;
    private int sumRatings;

    public Movie(MovieInput movieInput) {
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

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    public int getSumRatings() {
        return sumRatings;
    }

    public void setSumRatings(int sumRatings) {
        this.sumRatings = sumRatings;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public ArrayList<String> getGenres() {
        return genres;
    }
    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }
    public ArrayList<String> getActors() {
        return actors;
    }
    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }
    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }
    public void setCountriesBanned(ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public double getRating() {
        return numRatings == 0 ? 0 : (double)sumRatings / numRatings;
    }

    @Override
    public ObjectNode toJSON() {
        ObjectNode output = Helpers.objectMapper.createObjectNode();

        output.put("name", this.name);
        output.put("year", this.year);
        output.put("duration", this.duration);
        output.set("genres", Helpers.StringListToJSON(this.genres));
        output.set("actors", Helpers.StringListToJSON(this.actors));
        output.set("countriesBanned", Helpers.StringListToJSON(this.countriesBanned));
        output.put("numLikes", this.numLikes);
        output.put("rating", getRating());
        output.put("numRatings", this.numRatings);

        return output;
    }

    
}
