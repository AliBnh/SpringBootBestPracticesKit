package com.ali.services;

import com.ali.dtos.PokemonDto;
import com.ali.dtos.ReviewDto;
import com.ali.models.Pokemon;
import com.ali.models.Review;
import com.ali.repositories.PokemonRepository;
import com.ali.repositories.ReviewRepository;
import com.ali.services.implementation.ReviewServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private PokemonRepository pokemonRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Pokemon pokemon;
    private PokemonDto pokemonDto;
    private Review review;
    private ReviewDto reviewDto;

    @BeforeEach
    public void init() {
        pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();
        pokemonDto = PokemonDto.builder().name("Pikachu").type("Electric").build();
        review = Review.builder().title("title").content("Good").stars(5).build();
        reviewDto = ReviewDto.builder().title("title").content("Good").stars(5).build();
    }

    @Test
    public void reviewService_CreateReview_ReturnsReviewDto() {
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto savedReview = reviewService.createReview(pokemon.getId(), reviewDto);

        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getTitle()).isEqualTo(reviewDto.getTitle());
        Assertions.assertThat(savedReview.getContent()).isEqualTo(reviewDto.getContent());
        Assertions.assertThat(savedReview.getStars()).isEqualTo(reviewDto.getStars());
    }

    @Test
    public void reviewService_GetReviewsByPokemonId_ReturnsReviewsListDto() {
        when(reviewRepository.findByPokemonId(1L)).thenReturn(Arrays.asList(review));
        List<ReviewDto> pokemonReviews = reviewService.getReviewsByPokemonId(1L);
        Assertions.assertThat(pokemonReviews).isNotNull();
    }


    @Test
    public void reviewService_GetReviewById_ReturnsReviewDto() {
        Long reviewId = 1L;
        Long pokemonId = 1L;
        review.setPokemon(pokemon);
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        ReviewDto reviewReturn = reviewService.getReviewById(reviewId, pokemonId);

        Assertions.assertThat(reviewReturn).isNotNull();
    }

    @Test
    public void reviewService_UpdateReview_ReturnsReviewDto() {
        Long reviewId = 1L;
        Long pokemonId = 1L;
        review.setPokemon(pokemon);
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto reviewReturn = reviewService.updateReview(reviewId, pokemonId, reviewDto);

        Assertions.assertThat(reviewReturn).isNotNull();
    }

    @Test
    public void reviewService_DeleteReview_ReturnsReviewDto() {
        Long reviewId = 1L;
        Long pokemonId = 1L;
        pokemon.setReviews(Arrays.asList(review));
        review.setPokemon(pokemon);
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        assertAll(() -> reviewService.deleteReview(reviewId, pokemonId));



    }

}
