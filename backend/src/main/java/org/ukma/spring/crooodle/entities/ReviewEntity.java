package org.ukma.spring.crooodle.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(
    name = "reviews",
    uniqueConstraints = {
    @UniqueConstraint(columnNames = {"hotel_id", "user_id"}) // user submits only one review to one hotel to avoid spaam
    }
)
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel; // Hotel that received the review

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user; // User that submitted the review

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updatedAt; // Dates of the review


    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer ranking; // 1-5 обмеження

    @Column
    @Size(max = 500)
    private String description;



}










