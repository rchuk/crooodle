package org.ukma.spring.crooodle.dto;


import jakarta.validation.constraints.*;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class ReviewEditRequestDto {

    @NotNull
    @Min(1)
    @Max(5)
    int ranking;

    @Size(max = 500)
    String description;



}










