package com.example.springbootbackend.controller;

import com.example.springbootbackend.model.request.GuessMovieRequest;
import com.example.springbootbackend.model.response.GameResponse;
import com.example.springbootbackend.model.response.GuessMovieResponse;
import com.example.springbootbackend.service.BollywooddleService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class BollywooddleController {
    private BollywooddleService bollywooddleService;

    public BollywooddleController(BollywooddleService bollywooddleService) {
        super();
        this.bollywooddleService = bollywooddleService;
    }

    @RequestMapping(value = "/nextImage", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<byte[]> getImage(HttpServletRequest request) throws IOException {
        GameResponse response = null;
        try {
            if (request.getSession().getAttribute("resolution") == null) {
                request.getSession().invalidate();
            }
            if (request.getSession().isNew()) {
                request.getSession().setAttribute("retry_left", 5);
                request.getSession().setAttribute("resolution", 25);
                request.getSession().setAttribute("guessed", false);
            }

            int resolution = (int) request.getSession().getAttribute("resolution");
            response = bollywooddleService.getPixelateImage(resolution,
                    request.getSession().getAttribute("current_movie") == null ? "" :
                            request.getSession().getAttribute("current_movie").toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        request.getSession().setAttribute("current_movie", response.getTitle());
        System.out.println("cookies value" + request.getCookies());
        if((request.getSession().getAttribute("guessed") != null &&
                (boolean) request.getSession().getAttribute("guessed"))){
            request.getSession().invalidate();
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header("Access-Control-Allow-Credentials", "true")
                .body(response.getImage());
    }

    @RequestMapping(value = "/guess", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<GuessMovieResponse> guessMovie(@RequestBody GuessMovieRequest guessMovieRequest, HttpServletRequest request) {
        if (request.getSession().isNew() || request.getSession().getAttribute("current_movie") == null) {
            return ResponseEntity.ok().body(new GuessMovieResponse(false,
                    "New movie", 5, 1));
        }
        guessMovieRequest.setCurrentSessionMovie(request.getSession().getAttribute("current_movie").toString());
        guessMovieRequest.setRetryLeft(Integer.parseInt(request.getSession().getAttribute("retry_left").toString()));
        guessMovieRequest.setResolution(Integer.parseInt(request.getSession().getAttribute("resolution").toString()));

        GuessMovieResponse response = bollywooddleService.guessMovie(guessMovieRequest);
        if (response.getRetryLeft() < 0) {
            request.getSession().invalidate();
        } else {
            request.getSession().setAttribute("retry_left", response.getRetryLeft());
            request.getSession().setAttribute("resolution", response.getResolution());
        }
        if(response.getIsCorrect()){
            request.getSession().setAttribute("guessed", true);
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/healthCheck")
    public String test(){
        return "healthy";
    }
}
