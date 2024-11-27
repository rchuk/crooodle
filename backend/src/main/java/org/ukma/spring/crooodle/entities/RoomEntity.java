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

    @ManyToOne
    @JoinColumn(name = "room_group_id")
    private RoomGroupEntity roomGroup; // Зв'язок із групою кімнат

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationEntity> reservations; // Список бронювань
    @PositiveOrZero
    @Column(nullable = false)
    private int rankSum; // Додано поле для суми рейтингу

    @PositiveOrZero
    @Column(nullable = false)
    private int rankCount; // Додано поле для кількості рейтингів
}
