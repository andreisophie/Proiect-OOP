package database;

import java.util.ArrayList;

import input.MovieInput;

public class Movie {
    private String name;
    private int year;
    private int duration;
    ArrayList<String> genres;
    ArrayList<String> actors;
    ArrayList<String> countriesBanned;

    public Movie(MovieInput movieInput) {
        this.name = movieInput.getName();
        this.year = movieInput.getYear();
        this.duration = movieInput.getDuration();
        this.genres = new ArrayList<>(movieInput.getGenres());
        this.actors = new ArrayList<>(movieInput.getActors());
        this.countriesBanned = new ArrayList<>(movieInput.getCountriesBanned());
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
}
