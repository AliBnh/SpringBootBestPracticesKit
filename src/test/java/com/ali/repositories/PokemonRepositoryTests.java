package com.ali.repositories;

import com.ali.models.Pokemon;
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
public class PokemonRepositoryTests {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void pokemonRepository_saveAll_returnSavedPokemon(){
        // Given / Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("Electric")
                .build();

        // When / Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        // Then / Assert
        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isNotNull();
        Assertions.assertThat(savedPokemon.getName()).isEqualTo(pokemon.getName());
        Assertions.assertThat(savedPokemon.getType()).isEqualTo(pokemon.getType());
    }

    @Test
    public void pokemonRepository_getAll_ReturnAllPokemons(){
        Pokemon pokemon1 = Pokemon.builder()
                .name("pikachu")
                .type("Electric")
                .build();
        Pokemon pokemon2 = Pokemon.builder()
                .name("charmander")
                .type("Fire")
                .build();
        pokemonRepository.save(pokemon1);
        pokemonRepository.save(pokemon2);

        List<Pokemon> pokemonList = pokemonRepository.findAll();

        Assertions.assertThat(pokemonList).isNotNull();
        Assertions.assertThat(pokemonList.size()).isEqualTo(2);
    }

    @Test
    public void pokemonRepository_getById_ReturnPokemon(){
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("Electric")
                .build();
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        Pokemon foundPokemon = pokemonRepository.findById(pokemon.getId()).orElse(null);

        Assertions.assertThat(foundPokemon).isNotNull();
        Assertions.assertThat(foundPokemon.getId()).isEqualTo(savedPokemon.getId());
        Assertions.assertThat(foundPokemon.getName()).isEqualTo(savedPokemon.getName());
        Assertions.assertThat(foundPokemon.getType()).isEqualTo(savedPokemon.getType());
    }

    @Test
    public void pokemonRepository_findByType_ReturnPokemon(){
        Pokemon pokemon1 = Pokemon.builder()
                .name("pikachu")
                .type("Electric")
                .build();
        Pokemon pokemon2 = Pokemon.builder()
                .name("charmander")
                .type("Fire")
                .build();
        pokemonRepository.save(pokemon1);
        pokemonRepository.save(pokemon2);

        Pokemon pokemonList = pokemonRepository.findByType("Electric").get();

        Assertions.assertThat(pokemonList).isNotNull();

    }


    @Test
    public void pokemonRepository_UpdatePokemon_ReturnUpdatedPokemon(){
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("Electric")
                .build();
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        savedPokemon.setName("raichu");
        savedPokemon.setType("Electric");

        Pokemon updatedPokemon = pokemonRepository.save(savedPokemon);

        Assertions.assertThat(updatedPokemon).isNotNull();
        Assertions.assertThat(updatedPokemon.getId()).isEqualTo(savedPokemon.getId());
        Assertions.assertThat(updatedPokemon.getName()).isEqualTo(savedPokemon.getName());
        Assertions.assertThat(updatedPokemon.getType()).isEqualTo(savedPokemon.getType());
    }

    @Test
    public void pokemonRepository_deleteById_ReturnNull(){
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("Electric")
                .build();
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        pokemonRepository.deleteById(savedPokemon.getId());

        Optional<Pokemon> deletedPokemon = pokemonRepository.findById(savedPokemon.getId());

        Assertions.assertThat(deletedPokemon).isEmpty();
    }
}
