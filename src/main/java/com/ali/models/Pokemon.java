package com.ali.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Pokemon name must not be blank")
    @Size(min = 2, max = 50, message = "Product name must be between 3 and 50 characters")
    @Column(unique = true, nullable = false)
    private String name;

    @NotBlank(message = "Pokemon type must not be blank")
    private String type;

    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

}
