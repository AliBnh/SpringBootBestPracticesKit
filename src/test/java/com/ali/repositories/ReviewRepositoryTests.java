package com.ali.repositories;

import com.ali.models.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTests {

        private ReviewRepository reviewRepository;

        @Autowired
        public ReviewRepositoryTests(ReviewRepository reviewRepository) {
            this.reviewRepository = reviewRepository;
        }

        @Test
        public void reviewRepository_saveAll_ReturnSavedReview(){
            // Given
            Review review = Review.builder()
                    .title("Great Review")
                    .content("This is a great review")
                    .stars(5)
                    .build();
            // When
            Review savedReview = reviewRepository.save(review);
            // Then
            Assertions.assertThat(savedReview).isNotNull();
            Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
        }

        @Test
        public void reviewRepository_getAll_ReturnAllReviews(){
            // Given
            Review review1 = Review.builder()
                    .title("Great Review")
                    .content("This is a great review")
                    .stars(5)
                    .build();
            Review review2 = Review.builder()
                    .title("Bad Review")
                    .content("This is a bad review")
                    .stars(1)
                    .build();
            reviewRepository.save(review1);
            reviewRepository.save(review2);
            // When
            List <Review> reviews = reviewRepository.findAll();
            // Then
            Assertions.assertThat(reviews).isNotEmpty();
            Assertions.assertThat(reviews.size()).isEqualTo(2);
        }

        @Test
        public void reviewRepository_FindById_ReturnReview(){
            // Given
            Review review = Review.builder()
                    .title("Great Review")
                    .content("This is a great review")
                    .stars(5)
                    .build();
            Review savedReview = reviewRepository.save(review);
            // When
            Review foundReview = reviewRepository.findById(savedReview.getId()).get();
            // Then
            Assertions.assertThat(foundReview).isNotNull();
            Assertions.assertThat(foundReview.getId()).isEqualTo(savedReview.getId());
        }

        @Test
        public void reviewRepository_deleteById_ReturnNull(){
            // Given
            Review review = Review.builder()
                    .title("Great Review")
                    .content("This is a great review")
                    .stars(5)
                    .build();
            Review savedReview = reviewRepository.save(review);
            // When
            reviewRepository.deleteById(savedReview.getId());
            Optional<Review> deletedReview = reviewRepository.findById(savedReview.getId());
            // Then
            Assertions.assertThat(deletedReview).isEmpty();
        }

        @Test
        public void reviewRepository_UpdateReview_ReturnUpdatedReview(){
            // Given
            Review review = Review.builder()
                    .title("Great Review")
                    .content("This is a great review")
                    .stars(5)
                    .build();
            Review savedReview = reviewRepository.save(review);
            // When
            savedReview.setTitle("Updated Review");
            Review updatedReview = reviewRepository.save(savedReview);
            // Then
            Assertions.assertThat(updatedReview).isNotNull();
            Assertions.assertThat(updatedReview.getId()).isEqualTo(savedReview.getId());
            Assertions.assertThat(updatedReview.getTitle()).isEqualTo("Updated Review");
        }


}
