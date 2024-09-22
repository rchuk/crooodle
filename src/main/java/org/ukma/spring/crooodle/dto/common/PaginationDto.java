package org.ukma.spring.crooodle.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@Builder
public class PaginationDto {
    private final int page;
    private final int limit;

    public PaginationDto() {
        this(0, 10);
    }

    public Pageable toPageable() {
        return PageRequest.of(this.page, this.limit);
    }
}
