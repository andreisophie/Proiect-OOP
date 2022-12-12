package database;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.node.ArrayNode;
import helpers.Helpers;
import input.MovieInput;

public class MovieList implements JSONable{
    ArrayList<Movie> movies;

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public MovieList() {
        movies = new ArrayList<>();
    }

    public MovieList(ArrayList<MovieInput> movieInputs) {
        this();
        for (MovieInput movieInput : movieInputs) {
            movies.add(new Movie(movieInput));
        }
    }

    @Override
    public ArrayNode toJSON() {
        ArrayNode output = Helpers.objectMapper.createArrayNode();

        for (Movie movie : movies) {
            output.add(movie.toJSON());
        }

        return output;
    }

    
}
