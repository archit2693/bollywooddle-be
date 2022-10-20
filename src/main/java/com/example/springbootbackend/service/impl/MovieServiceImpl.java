package com.example.springbootbackend.service.impl;

import com.example.springbootbackend.exception.ResourceNotFoundException;
import com.example.springbootbackend.model.Movie;
import com.example.springbootbackend.repository.MovieRepository;
import com.example.springbootbackend.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;


    public MovieServiceImpl(MovieRepository movieRepository) {
        super();
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(Long movieID) {
        return movieRepository.findById(movieID).
                orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieID));
    }

    @Override
    public Movie getMovieByTitle(String movieTitle) {
        return movieRepository.findByTitle(movieTitle).
                orElseThrow(() -> new ResourceNotFoundException("Movie", "title", movieTitle));
    }

    @Override
    public Movie updateMovie(Movie movie, Long movieID) {
        Movie movie1 = movieRepository.findById(movieID).
                orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieID));
        movie1.setTitle(movie.getTitle());
        movie1.setDirector(movie.getDirector());
        movie1.setActors(movie.getActors());
        movie1.setGenre(movie.getGenre());
        movie1.setYear(movie.getYear());
        movie1.setImage(movie.getImage());
        return movieRepository.save(movie1);
    }

    @Override
    public void deleteMovie(Long movieID) {
        Movie movie = movieRepository.findById(movieID).
                orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieID));
        movieRepository.delete(movie);
    }

    @Override
    public Movie getRandomMovie() {
        var Movies = movieRepository.findAll();
        var random = (int) (Math.random() * Movies.size());
        return Movies.get(random);
    }

    @Override
    public List<String> getMovieTitles() {
        var Movies = movieRepository.findAll();
        var titles = new ArrayList<String>();
        for (Movie movie : Movies) {
            titles.add(movie.getTitle());
        }
        return titles;
    }
}
