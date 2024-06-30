package com.ali.services;

import com.ali.dtos.PokemonDto;
import com.ali.dtos.PokemonResponsePage;
import org.apache.coyote.BadRequestException;

public  interface PokemonService {
    PokemonDto createPokemon(PokemonDto pokemonDto);
    PokemonResponsePage getAllPokemon(int pageNo, int pageSize);
    PokemonDto getPokemonById(Long id);
    PokemonDto updatePokemon(PokemonDto pokemonDto, Long id);
    void deletePokemonId(Long id);
}
