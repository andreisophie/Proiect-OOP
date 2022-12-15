package database;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.node.ArrayNode;
import helpers.Helpers;
import input.MovieInput;

public class MovieList implements JSONable{
    ArrayList<Movie> movies;

    public MovieList() {
        movies = new ArrayList<>();
    }

    public MovieList(final ArrayList<MovieInput> movieInputs) {
        this();
        for (final MovieInput movieInput : movieInputs) {
            movies.add(new Movie(movieInput));
        }
    }

    public MovieList(final MovieList movieList) {
        movies = new ArrayList<>(movieList.getMovies());
    }

    /**
     * @return an ArrayList of movie objects in this instance
     */
    public ArrayList<Movie> getMovies() {
        return movies;
    }

    /**
     * Sets the movies field to a new ArrayList of movies
     * @param movies ArrayList to be set as new value
     */
    public void setMovies(final ArrayList<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Returns a JsonNode object which contains relevant data from this class
     * To be used for output
     */
    @Override
    public ArrayNode toJSON() {
        final ArrayNode output = Helpers.objectMapper.createArrayNode();

        for (final Movie movie : movies) {
            output.add(movie.toJSON());
        }

        return output;
    }

    
}
