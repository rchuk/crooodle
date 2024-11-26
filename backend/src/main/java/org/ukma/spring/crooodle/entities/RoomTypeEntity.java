package org.ukma.spring.crooodle.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "room_types")
public class RoomTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 50)
    private String name; // Назва типу кімнати, наприклад, "Single", "Double"

    @Column(length = 500)
    private String description; // Опис типу кімнати

    @PositiveOrZero
    @Column(nullable = false)
    private double basePrice; // Базова ціна для цього типу кімнати

    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomEntity> rooms; // Кімнати цього типу
}
