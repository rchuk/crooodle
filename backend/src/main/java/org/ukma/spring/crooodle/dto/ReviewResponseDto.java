package org.ukma.spring.crooodle.dto;


import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;


@Value
@Builder
public class ReviewResponseDto {

    long id;
    int ranking;
    String description;
    LocalDate createdAt;
    LocalDate updatedAt;
    String hotelName;
    String userName;



}










