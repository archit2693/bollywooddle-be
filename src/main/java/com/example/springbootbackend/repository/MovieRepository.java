package com.example.springbootbackend.repository;

import com.example.springbootbackend.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTitle(String movieTitle);
}
