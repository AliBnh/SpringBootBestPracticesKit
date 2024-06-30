package com.ali.controllers;
import com.ali.dtos.ReviewDto;
import com.ali.services.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Review", description = "The review API")
@RestController @AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/pokemon/{pokemonId}/reviews")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "pokemonId") Long pokemonId,@Valid @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.createReview(pokemonId, reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("/pokemon/{pokemonId}/reviews")
    public List<ReviewDto> getReviewsByPokemonId(@PathVariable(value = "pokemonId") Long pokemonId) {
        return reviewService.getReviewsByPokemonId(pokemonId);
    }

    @GetMapping("/pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "pokemonId") Long pokemonId, @PathVariable(value = "id") Long reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(pokemonId, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PutMapping("/pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(value = "pokemonId") Long pokemonId, @PathVariable(value = "id") Long reviewId,
                                                 @Valid @RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReview = reviewService.updateReview(pokemonId, reviewId, reviewDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "pokemonId") Long pokemonId, @PathVariable(value = "id") Long reviewId) {
        reviewService.deleteReview(pokemonId, reviewId);
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }
}