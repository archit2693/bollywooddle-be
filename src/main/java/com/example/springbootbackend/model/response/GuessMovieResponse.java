package com.example.springbootbackend.model.response;

import lombok.Data;

@Data
public class GuessMovieResponse {
    private Boolean isCorrect;
    private String message;
    private Integer retryLeft;
    private Integer resolution;

    public GuessMovieResponse(Boolean isCorrect, String message, Integer retryLeft, Integer resolution) {
        this.isCorrect = isCorrect;
        this.message = message;
        this.retryLeft = retryLeft;
        this.resolution = resolution;
    }
}
