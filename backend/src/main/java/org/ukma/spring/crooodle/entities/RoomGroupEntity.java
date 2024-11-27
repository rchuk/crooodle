package org.ukma.spring.crooodle.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.ukma.spring.crooodle.entities.embedded.AmenitiesEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "room_groups")
public class RoomGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 1, max = 100)
    @Column(nullable = false)
    private String name;

    @PositiveOrZero
    @Column(nullable = false)
    private int capacity;

    @PositiveOrZero
    @Column(nullable = false)
    private double pricePerNight;

    @Length(max = 500)
    private String description;

    @PositiveOrZero
    @Column(nullable = false)
    private int rankSum;

    @PositiveOrZero
    @Column(nullable = false)
    private int rankCount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomEntity> rooms;

    @Embedded
    private AmenitiesEntity amenities;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomTypeEntity roomType;

    // Метод для обчислення середнього рейтингу
    public double calculateAverageRank() {
        if (rankCount == 0) return 0.0;
        return (double) rankSum / rankCount;
    }

    // Оновлення рейтингу на основі кімнат
    public void updateRankFromRooms() {
        rankSum = rooms.stream().mapToInt(RoomEntity::getRankSum).sum();
        rankCount = rooms.stream().mapToInt(RoomEntity::getRankCount).sum();
    }
}