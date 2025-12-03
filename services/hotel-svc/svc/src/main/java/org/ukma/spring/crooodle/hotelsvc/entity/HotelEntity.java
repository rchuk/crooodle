package org.ukma.spring.crooodle.hotelsvc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotel")
public class HotelEntity {
	@Id
	@GeneratedValue
	private UUID id;

	@NotNull
	@Column(nullable = false)
	private UUID ownerId;

	@NotBlank
	@Column(nullable = false)
	private String name;

	@NotBlank
	@Column(nullable = false)
	private String address;

	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<RoomEntity> rooms = new ArrayList<>();

	@Formula("(SELECT count(*) FROM room r WHERE r.hotel_id = id)")
	private long roomCount;
}
