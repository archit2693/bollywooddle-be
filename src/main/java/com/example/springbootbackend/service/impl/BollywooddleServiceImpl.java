package com.example.springbootbackend.service.impl;

import com.example.springbootbackend.model.request.GuessMovieRequest;
import com.example.springbootbackend.model.response.GameResponse;
import com.example.springbootbackend.model.Movie;
import com.example.springbootbackend.model.response.GuessMovieResponse;
import com.example.springbootbackend.service.BollywooddleService;
import com.example.springbootbackend.service.MovieService;
import com.example.springbootbackend.util.PixelateImage;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class BollywooddleServiceImpl implements BollywooddleService {

    private PixelateImage pixelateImage;
    private MovieService movieService;
    private final String BASE_PATH = "src/main/resources/images/";
    private final String IMAGE_TYPE = ".jpg";
    private final int FULL_RESOLUTION = 1;

    public BollywooddleServiceImpl(MovieService movieService) {
        super();
        this.pixelateImage = new PixelateImage();
        this.movieService = movieService;
    }

    @Override
    public GameResponse getPixelateImage(int resolution, String movieTitle) throws IOException {
        if (movieTitle.equals("")) {
            Movie movie = movieService.getRandomMovie();
            movieTitle = movie.getConcatenatedTitle();
        }
        var path = BASE_PATH + movieTitle + IMAGE_TYPE;
        File image = new File(path);
        return new GameResponse(pixelateImage.pixelateImage(image, resolution), movieTitle);
    }

    @Override
    public GuessMovieResponse guessMovie(GuessMovieRequest guessMovieRequest) {

        if(guessMovieRequest.getMovieTitle().equals("skip")) {
            if (guessMovieRequest.getRetryLeft() <= 1) {
                return new GuessMovieResponse(false,
                        "You have no more guesses left. The movie was " + guessMovieRequest.getCurrentSessionMovie(),
                        guessMovieRequest.getRetryLeft() - 1,
                        FULL_RESOLUTION);
            }
            return new GuessMovieResponse(false, "Skipping guess!",
                    guessMovieRequest.getRetryLeft() - 1, guessMovieRequest.getResolution()-5);
        }
        Movie movie = movieService.getMovieByTitle(guessMovieRequest.getMovieTitle());
        if (movie.getConcatenatedTitle().equals(guessMovieRequest.getCurrentSessionMovie())) {
            return new GuessMovieResponse(true, "You guessed it right!",
                    guessMovieRequest.getRetryLeft(), FULL_RESOLUTION);
        } else {
            if (guessMovieRequest.getRetryLeft() <= 1) {
                return new GuessMovieResponse(false,
                        "You have no more guesses left. The movie was " + guessMovieRequest.getCurrentSessionMovie(),
                        guessMovieRequest.getRetryLeft() - 1,
                        FULL_RESOLUTION);
            }
            return new GuessMovieResponse(false, "You guessed it wrong!",
                    guessMovieRequest.getRetryLeft() - 1, guessMovieRequest.getResolution()-5);
        }
    }
}
