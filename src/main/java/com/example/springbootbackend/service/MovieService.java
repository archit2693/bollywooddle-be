package com.example.springbootbackend.service;

import com.example.springbootbackend.model.Movie;

import java.util.List;

public interface MovieService {
    Movie saveMovie(Movie movie);
    List<Movie> getAllMovies();
    Movie getMovieById(Long movieID);
    Movie getMovieByTitle(String movieTitle);
    Movie updateMovie(Movie movie, Long movieID);
    void deleteMovie(Long movieID);
    Movie getRandomMovie();
    List<String> getMovieTitles();
}
