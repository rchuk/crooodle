package org.ukma.spring.crooodle.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * DTO for receiving pagination information
 * in various list endpoints
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PaginationDto {
    @Schema(example = "0")
    Integer page;
    @Schema(example = "10")
    Integer limit;

    public Pageable toPageable() {
        return PageRequest.of(
            this.page != null ? this.page : 0,
            this.limit != null ? this.limit : 10
        );
    }
}
