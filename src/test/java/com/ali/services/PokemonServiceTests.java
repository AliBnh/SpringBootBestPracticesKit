package com.ali.services;

import com.ali.dtos.PokemonDto;
import com.ali.dtos.PokemonResponsePage;
import com.ali.models.Pokemon;
import com.ali.repositories.PokemonRepository;
import com.ali.services.implementation.PokemonServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTests {

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @Test
    public void pokemonService_CreatePokemon_ReturnPokemonDto() {
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();
        PokemonDto pokemonDto = PokemonDto.builder()
                .name("Pikachu")
                .type("Electric")
                .build();

        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto createdPokemon = pokemonService.createPokemon(pokemonDto);
        Assertions.assertThat(createdPokemon).isNotNull();
    }

    @Test
    public void pokemonService_GetAllPokemon_ReturnPokemonResponsePage(){
        Page<Pokemon> pokemons = Mockito.mock(Page.class);

        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemons);

        PokemonResponsePage savePokemon = pokemonService.getAllPokemon(1,10);
        Assertions.assertThat(savePokemon).isNotNull();
    }

    @Test
    public void pokemonService_GetPokemonById_ReturnPokemonDto(){
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();

        when(pokemonRepository.findById(1L)).thenReturn(Optional.ofNullable(pokemon));

        PokemonDto createdPokemon = pokemonService.getPokemonById(1L);
        Assertions.assertThat(createdPokemon).isNotNull();
    }

    @Test
    public void pokemonService_UpdatePokemon_ReturnPokemonDto(){
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();
        PokemonDto pokemonDto = PokemonDto.builder()
                .name("Pikachu")
                .type("Electric")
                .build();

        when(pokemonRepository.findById(1L)).thenReturn(Optional.ofNullable(pokemon));
        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto createdPokemon = pokemonService.updatePokemon(pokemonDto,1L);
        Assertions.assertThat(createdPokemon).isNotNull();
    }

    @Test
    public void pokemonService_DeletePokemon_ReturnNull(){
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();

        when(pokemonRepository.findById(1L)).thenReturn(Optional.ofNullable(pokemon));

        assertAll(() -> pokemonService.deletePokemonId(1L));
    }
}
