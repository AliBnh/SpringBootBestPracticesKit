package com.ali.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long id;
    @NotBlank(message = "Title must not be blank")
    private String title;
    @NotBlank(message = "Content must not be blank")
    private String content;
    @Positive(message = "Stars must be positive")
    private int stars;
}