package org.ukma.spring.crooodle.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ukma.spring.crooodle.entities.enums.ReservationStatus;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "reservations")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room; // Зв'язок із кімнатою

    @NotNull
    @Column(nullable = false)
    private String guestName; // Ім'я гостя

    @NotNull
    @Column(nullable = false)
    private String guestEmail; // Email гостя

    @FutureOrPresent
    @Column(nullable = false)
    private LocalDate checkInDate; // Дата заселення (повинна бути у майбутньому або сьогодні)

    @FutureOrPresent
    @Column(nullable = false)
    private LocalDate checkOutDate; // Дата виселення (повинна бути у майбутньому або сьогодні)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status; // Статус бронювання (CONFIRMED, CANCELLED тощо)
}
