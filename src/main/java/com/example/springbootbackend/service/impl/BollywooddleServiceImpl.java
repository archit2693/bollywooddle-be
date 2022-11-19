package com.example.springbootbackend.service.impl;

import com.example.springbootbackend.model.Movie;
import com.example.springbootbackend.model.request.GuessMovieRequest;
import com.example.springbootbackend.model.response.GameResponse;
import com.example.springbootbackend.model.response.GuessMovieResponse;
import com.example.springbootbackend.service.BollywooddleService;
import com.example.springbootbackend.service.MovieService;
import com.example.springbootbackend.util.PixelateImage;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

@Service
public class BollywooddleServiceImpl implements BollywooddleService {

    private final PixelateImage pixelateImage;
    private final MovieService movieService;

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
        long start = System.currentTimeMillis();
        InputStream inputStream = getFileFromResourceAsStream("images/" + movieTitle + ".jpg");
        long end = System.currentTimeMillis();
        System.out.println("Reading image takes " +
                (end - start) + "ms");
        return new GameResponse(pixelateImage.pixelateImage(ImageIO.read(inputStream), resolution), movieTitle);

    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }


    @Override
    public GuessMovieResponse guessMovie(GuessMovieRequest guessMovieRequest) {
        int FULL_RESOLUTION = 1;
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
