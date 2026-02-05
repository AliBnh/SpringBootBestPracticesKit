package com.ali.controllers;

import com.ali.dtos.PokemonDto;
import com.ali.dtos.PokemonResponsePage;
import com.ali.dtos.ReviewDto;
import com.ali.models.Pokemon;
import com.ali.models.Review;
import com.ali.services.PokemonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = PokemonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PokemonControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PokemonService pokemonService;
    @Autowired
    private ObjectMapper objectMapper;

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
    public void pokemonController_CreatePokemon_ReturnsPokemon() throws Exception {
        given(pokemonService.createPokemon(ArgumentMatchers.any())).willAnswer((invocation->invocation.getArgument(0)));
        ResultActions response = mockMvc.perform(post("/pokemon")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(pokemonDto)));
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Pikachu"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("Electric"));
    }
    @Test
    public void PokemonController_GetAllPokemon_ReturnResponseDto() throws Exception {
        PokemonResponsePage responseDto = PokemonResponsePage.builder().pageSize(10).last(true).pageNo(1).content(Arrays.asList(pokemonDto)).build();
        when(pokemonService.getAllPokemon(1,10)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/pokemon")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo","1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));
    }

    @Test
    public void PokemonController_PokemonDetail_ReturnPokemonDto() throws Exception {
        Long pokemonId = 1L;
        when(pokemonService.getPokemonById(pokemonId)).thenReturn(pokemonDto);

        ResultActions response = mockMvc.perform(get("/pokemon/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())));
    }

    @Test
    public void PokemonController_UpdatePokemon_ReturnPokemonDto() throws Exception {
        Long pokemonId = 1L;
        when(pokemonService.updatePokemon(pokemonDto, pokemonId)).thenReturn(pokemonDto);

        ResultActions response = mockMvc.perform(put("/pokemon/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())));
    }

    @Test
    public void PokemonController_DeletePokemon_ReturnString() throws Exception {
        Long pokemonId = 1L;
        doNothing().when(pokemonService).deletePokemonId(pokemonId);

        ResultActions response = mockMvc.perform(delete("/pokemon/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }


}
