package com.example.springbootbackend.service;

import com.example.springbootbackend.model.request.GuessMovieRequest;
import com.example.springbootbackend.model.response.GameResponse;
import com.example.springbootbackend.model.response.GuessMovieResponse;

import java.io.IOException;


public interface BollywooddleService {
    GameResponse getPixelateImage(int resolution, String movieTitle) throws IOException;
    GuessMovieResponse guessMovie(GuessMovieRequest guessMovieRequest);
}
