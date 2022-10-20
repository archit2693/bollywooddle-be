package com.example.springbootbackend.controller;

import com.example.springbootbackend.model.Movie;
import com.example.springbootbackend.service.MovieService;
import com.example.springbootbackend.util.PixelateImage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MovieController {
    private MovieService movieService;

    public MovieController(MovieService movieService) {
        super();
        this.movieService = movieService;
    }

    @PostMapping("/movie")
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        return new ResponseEntity<>(movieService.saveMovie(movie), HttpStatus.CREATED);
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @DeleteMapping("/movie/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable(value = "id") Long movieID) {
        movieService.deleteMovie(movieID);
        return new ResponseEntity<String>("Movie deleted successfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/loadImage", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@RequestParam int resolution) throws IOException {
        var imgFile = new ClassPathResource("images/A_thursday.jpg").getFile();
        var pixelateImage = new PixelateImage();
        byte[] bytes = pixelateImage.pixelateImage(imgFile, resolution);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }

    @GetMapping("/movieTitles")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<String>> getMovieTitles() {
        return new ResponseEntity<>(movieService.getMovieTitles(), HttpStatus.OK);
    }
}
