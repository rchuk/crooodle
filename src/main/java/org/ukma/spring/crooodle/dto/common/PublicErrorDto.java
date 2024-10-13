package org.ukma.spring.crooodle.dto.common;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PublicErrorDto {
    String message;
}
