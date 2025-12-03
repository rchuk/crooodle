package org.ukma.spring.crooodle.hotelsvc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room")
public class RoomEntity {
	@Id
	@GeneratedValue
	private UUID id;

	@NotBlank
	@Column(nullable = false)
	private String name;

	@Positive
	private int price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hotel_id")
	private HotelEntity hotel;
}
