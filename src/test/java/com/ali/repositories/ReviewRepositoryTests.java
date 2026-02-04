package com.ali.repositories;
//
import com.ali.models.Pokemon;
import com.ali.models.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTests {

    private final ReviewRepository reviewRepository;
    private final PokemonRepository pokemonRepository;

    @Autowired
    public ReviewRepositoryTests(ReviewRepository reviewRepository, PokemonRepository pokemonRepository) {
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
    }

    /**
     * Crée un Pokemon avec un nom UNIQUE pour éviter les violations de contrainte.
     * Si ton entity Pokemon n'a pas builder(), remplace par setters.
     */
    private Pokemon savedPokemon() {
        String uniqueName = "Pikachu-" + UUID.randomUUID();
        Pokemon p = Pokemon.builder()
                .name(uniqueName)
                .type("Electric")
                .build();
        return pokemonRepository.save(p);
    }

    @Test
    public void reviewRepository_saveAll_ReturnSavedReview() {
        Pokemon pokemon = savedPokemon();

        Review review = Review.builder()
                .title("Great Review")
                .content("This is a great review")
                .stars(5)
                .pokemon(pokemon)
                .build();

        Review savedReview = reviewRepository.save(review);

        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    public void reviewRepository_getAll_ReturnAllReviews() {
        // IMPORTANT: on utilise un seul Pokemon pour les 2 reviews (ou noms uniques)
        Pokemon pokemon = savedPokemon();

        Review review1 = Review.builder()
                .title("Great Review")
                .content("This is a great review")
                .stars(5)
                .pokemon(pokemon)
                .build();

        Review review2 = Review.builder()
                .title("Bad Review")
                .content("This is a bad review")
                .stars(1)
                .pokemon(pokemon)
                .build();

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        List<Review> reviews = reviewRepository.findAll();

        Assertions.assertThat(reviews).isNotEmpty();
        Assertions.assertThat(reviews.size()).isEqualTo(2);
    }

    @Test
    public void reviewRepository_FindById_ReturnReview() {
        Pokemon pokemon = savedPokemon();

        Review review = Review.builder()
                .title("Great Review")
                .content("This is a great review")
                .stars(5)
                .pokemon(pokemon)
                .build();

        Review savedReview = reviewRepository.save(review);

        Review foundReview = reviewRepository.findById(savedReview.getId()).orElseThrow();

        Assertions.assertThat(foundReview).isNotNull();
        Assertions.assertThat(foundReview.getId()).isEqualTo(savedReview.getId());
    }

    @Test
    public void reviewRepository_deleteById_ReturnNull() {
        Pokemon pokemon = savedPokemon();

        Review review = Review.builder()
                .title("Great Review")
                .content("This is a great review")
                .stars(5)
                .pokemon(pokemon)
                .build();

        Review savedReview = reviewRepository.save(review);

        reviewRepository.deleteById(savedReview.getId());
        Optional<Review> deletedReview = reviewRepository.findById(savedReview.getId());

        Assertions.assertThat(deletedReview).isEmpty();
    }

    @Test
    public void reviewRepository_UpdateReview_ReturnUpdatedReview() {
        Pokemon pokemon = savedPokemon();

        Review review = Review.builder()
                .title("Great Review")
                .content("This is a great review")
                .stars(5)
                .pokemon(pokemon)
                .build();

        Review savedReview = reviewRepository.save(review);

        savedReview.setTitle("Updated Review");
        Review updatedReview = reviewRepository.save(savedReview);

        Assertions.assertThat(updatedReview).isNotNull();
        Assertions.assertThat(updatedReview.getId()).isEqualTo(savedReview.getId());
        Assertions.assertThat(updatedReview.getTitle()).isEqualTo("Updated Review");
    }
}
