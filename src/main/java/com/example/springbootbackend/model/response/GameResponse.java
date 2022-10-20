package com.example.springbootbackend.model.response;

import lombok.Data;

@Data
public class GameResponse {
    private byte[] image;
    private String title;

    public GameResponse(byte[] pixelateImage, String concatenatedTitle) {
        this.image = pixelateImage;
        this.title = concatenatedTitle;
    }
}
