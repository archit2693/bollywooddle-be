package com.example.springbootbackend.model.request;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class GuessMovieRequest {
    private String movieTitle;
    @Nullable
    private int retryLeft;
    @Nullable
    private int resolution;
    @Nullable
    private String currentSessionMovie;
}
