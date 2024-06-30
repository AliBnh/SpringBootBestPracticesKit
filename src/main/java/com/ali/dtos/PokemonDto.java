package com.ali.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class PokemonDto {
    private Long id;
    @NotBlank(message = "Pokemon name must not be blank")
    @Size(min = 2, max = 50, message = "Pokemon name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Pokemon type must not be blank")
    private String type;

}
