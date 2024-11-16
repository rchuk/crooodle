package org.ukma.spring.crooodle.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * DTO for receiving pagination information
 * in various list endpoints
 */
@AllArgsConstructor
@Builder
@Value
public class PaginationDto {
    int page;
    int limit;

    public Pageable toPageable() {
        return PageRequest.of(this.page, this.limit);
    }
}
