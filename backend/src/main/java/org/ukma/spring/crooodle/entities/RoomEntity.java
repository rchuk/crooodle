package org.ukma.spring.crooodle.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "rooms")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(min = 1, max = 100)
    @Column(nullable = false)
    private String name; // Назва кімнати, наприклад, "Room 101"

    @PositiveOrZero
    @Column(nullable = false)
    private int capacity; // Максимальна кількість гостей

    @PositiveOrZero
    @Column(nullable = false)
    private double pricePerNight; // Ціна за ніч

    @Length(max = 500)
    private String description; // Опис кімнати (опціонально)

    @PositiveOrZero
    @Column(nullable = false)
    private int rankSum; // Сума всіх рейтингів кімнати

    @PositiveOrZero
    @Column(nullable = false)
    private int rankCount; // Кількість рейтингів для обчислення середнього

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel; // Зв'язок із готелем

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationEntity> reservations; // Список бронювань

    @Embedded
    private AmenitiesEntity amenities; // Зручності (Wi-Fi, кондиціонер тощо)

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomTypeEntity roomType; // Зв'язок із RoomTypeEntity

    @Column(nullable = false)
    private boolean available; // Доступність кімнати
}
