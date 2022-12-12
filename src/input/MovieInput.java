package input;

import java.util.ArrayList;

public class MovieInput {
    private String name;
    private int year;
    private int duration;
    ArrayList<String> genres;
    ArrayList<String> actors;
    ArrayList<String> countriesBanned;
    
    public MovieInput() {
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

    @Override
    public String toString() {
        return "MoviesInput [name=" + name + ", year=" + year + ", duration=" + duration + ", genres=" + genres
                + ", actors=" + actors + ", countriesBanned=" + countriesBanned + "]";
    }

    
}
