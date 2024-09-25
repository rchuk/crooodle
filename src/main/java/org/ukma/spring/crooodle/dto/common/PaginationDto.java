package org.ukma.spring.crooodle.dto.common;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@Value
public class PaginationDto {
    int page;
    int limit;

    public PaginationDto() {
        this(0, 10);
    }

    public Pageable toPageable() {
        return PageRequest.of(this.page, this.limit);
    }
}
