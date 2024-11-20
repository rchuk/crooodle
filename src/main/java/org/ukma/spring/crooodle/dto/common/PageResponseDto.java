package org.ukma.spring.crooodle.dto.common;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class PageResponseDto<T> {
    long total;
    List<T> items;
}
