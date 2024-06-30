package com.ali.controllers;


import com.ali.dtos.PokemonDto;
import com.ali.dtos.ReviewDto;
import com.ali.models.Pokemon;
import com.ali.models.Review;
import com.ali.services.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReviewControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewService reviewService;
    @Autowired
    private ObjectMapper objectMapper;
    private Pokemon pokemon;
    private Review review;
    private ReviewDto reviewDto;
    private PokemonDto pokemonDto;

    @BeforeEach
    public void init() {
        pokemon = Pokemon.builder().name("pikachu").type("electric").build();
        pokemonDto = PokemonDto.builder().name("pickachu").type("electric").build();
        review = Review.builder().title("title").content("content").stars(5).build();
        reviewDto = ReviewDto.builder().title("review title").content("test content").stars(5).build();
    }

    @Test
    public void ReviewController_GetReviewsByPokemonId_ReturnReviewDto() throws Exception {
        Long pokemonId = 1L;
        when(reviewService.getReviewsByPokemonId(pokemonId)).thenReturn(Arrays.asList(reviewDto));

        ResultActions response = mockMvc.perform(get("/pokemon/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(reviewDto).size())));
    }

    @Test
    public void ReviewController_UpdateReview_ReturnReviewDto() throws Exception {
        Long pokemonId = 1L;
        Long reviewId = 1L;
        when(reviewService.updateReview(pokemonId, reviewId, reviewDto)).thenReturn(reviewDto);

        ResultActions response = mockMvc.perform(put("/pokemon/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }


    @Test
    public void ReviewController_CreateReview_ReturnReviewDto() throws Exception {
        Long pokemonId = 1L;
        when(reviewService.createReview(pokemonId, reviewDto)).thenReturn(reviewDto);

        ResultActions response = mockMvc.perform(post("/pokemon/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }

    @Test
    public void ReviewController_GetReviewId_ReturnReviewDto() throws Exception {
        Long pokemonId = 1L;
        Long reviewId = 1L;
        when(reviewService.getReviewById(reviewId, pokemonId)).thenReturn(reviewDto);

        ResultActions response = mockMvc.perform(get("/pokemon/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }

    @Test
    public void ReviewController_DeleteReview_ReturnOk() throws Exception {
        Long pokemonId = 1L;
        Long reviewId = 1L;

        doNothing().when(reviewService).deleteReview(pokemonId, reviewId);

        ResultActions response = mockMvc.perform(delete("/pokemon/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }




}