package org.ukma.spring.crooodle.reservationsvc.domain;

import org.springframework.data.jpa.domain.Specification;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationCriteria;
import org.ukma.spring.crooodle.reservationsvc.entity.ReservationEntity;

import java.util.UUID;

public class ReservationSpecification {
	ReservationSpecification() {}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Specification<ReservationEntity> spec = Specification.unrestricted();

		public Builder withCriteria(ReservationCriteria criteria) {
			if (criteria == null) return this;

			if (criteria.checkInDate() != null) {
				spec = spec.and((root, query, cb) ->
					cb.greaterThanOrEqualTo(root.get("checkInDate"), criteria.checkInDate()));
			}
			if (criteria.checkOutDate() != null) {
				spec = spec.and((root, query, cb) ->
					cb.lessThanOrEqualTo(root.get("checkOutDate"), criteria.checkOutDate()));
			}
			return this;
		}

		public Builder withUserId(UUID userId) {
			if (userId != null) {
				spec = spec.and((root, query, cb) ->
					cb.equal(root.get("userId"), userId));
			}
			return this;
		}

		public Builder withRoomId(UUID roomId) {
			if (roomId != null) {
				spec = spec.and((root, query, cb) ->
					cb.equal(root.get("roomId"), roomId));
			}
			return this;
		}

		public Builder withHotelId(UUID hotelId) {
			if (hotelId != null) {
				spec = spec.and((root, query, cb) ->
					cb.equal(root.get("hotelId"), hotelId));
			}
			return this;
		}

		public Specification<ReservationEntity> build() {
			return spec;
		}
	}
}
