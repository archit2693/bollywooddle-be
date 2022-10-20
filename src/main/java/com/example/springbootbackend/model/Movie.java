package com.example.springbootbackend.model;

import com.example.springbootbackend.util.StringListConverter;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Locale;

@Data
@Entity
@Table(name = "movie", uniqueConstraints = {@UniqueConstraint(columnNames = {"imdbid"})})
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "imdbid", unique = true, nullable = false)
    private String imdbID;
    private String contentType;
    private String title;
    @Convert(converter = StringListConverter.class)
    private List<String> genre;
    private String year;
    @Convert(converter = StringListConverter.class)
    private List<String> director;
    @Convert(converter = StringListConverter.class)
    private List<String> actors;
    private String plot;
    private String image;
    private long ratingCount;
    private double rating;

    public String getConcatenatedTitle() {
        return title.replaceAll("\\s", "_").toLowerCase();
    }
}
