package org.ukma.spring.crooodle.dtos;

import org.springframework.data.domain.Page;
import lombok.Builder;

import java.util.List;

@Builder
public record PageDto<T>(
	long total,
	int totalPages,
	List<T> items
) {
	public static <T> PageDto<T> of(Page<T> page) {
		return new PageDto<>(
			page.getTotalElements(),
			page.getTotalPages(),
			page.getContent()
		);
	}
}
