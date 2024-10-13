package org.ukma.spring.crooodle.dto.common;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Builder
@Value
public class PublicValidationErrorDto {
    String message;
    Map<String, String> errors;
}
