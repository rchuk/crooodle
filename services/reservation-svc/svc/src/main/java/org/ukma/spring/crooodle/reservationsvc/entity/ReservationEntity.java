package org.ukma.spring.crooodle.reservationsvc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "reservations")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private UUID roomId;
		@NotNull
		private UUID hotelId;
    @NotNull
    private UUID profileId;

    @NotNull
    private Date checkInDate;
    @NotNull
    private Date checkOutDate;
}
