package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class AccessTokenResponseDto {
    String accessToken;
}

