package org.ukma.spring.crooodle.utils.exceptions.handler;

import lombok.Builder;

import java.util.Map;

@Builder
public record ValidationExceptionDto(String message, Map<String, String> errors) {

}
