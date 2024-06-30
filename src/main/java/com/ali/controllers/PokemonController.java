package com.ali.controllers;

import com.ali.dtos.PokemonDto;
import com.ali.dtos.PokemonResponsePage;
import com.ali.services.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pokemon")
@Tag(name = "Pokemon", description = "The Pokemon API")
public class PokemonController {

    private PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping
    public ResponseEntity<PokemonResponsePage> getPokemons(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(pokemonService.getAllPokemon(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PokemonDto> pokemonDetail(@PathVariable Long id) {
        return ResponseEntity.ok(pokemonService.getPokemonById(id));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDto> createPokemon(@Valid @RequestBody PokemonDto pokemonDto) {
        return new ResponseEntity<>(pokemonService.createPokemon(pokemonDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PokemonDto> updatePokemon(@Valid @RequestBody PokemonDto pokemonDto, @PathVariable("id") Long pokemonId) {
        PokemonDto response = pokemonService.updatePokemon(pokemonDto, pokemonId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a pokemon by its id",
            description = "If the order isn't found it'll return a 404 status code",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pokemon deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Pokemon not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePokemon(@PathVariable("id") Long pokemonId) {
        pokemonService.deletePokemonId(pokemonId);
        return new ResponseEntity<>("Pokemon delete", HttpStatus.OK);
    }


}
