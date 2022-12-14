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

    public void setMovies(final ArrayList<Movie> movies) {
        this.movies = movies;
    }

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

    @Override
    public ArrayNode toJSON() {
        final ArrayNode output = Helpers.objectMapper.createArrayNode();

        for (final Movie movie : movies) {
            output.add(movie.toJSON());
        }

        return output;
    }

    
}
