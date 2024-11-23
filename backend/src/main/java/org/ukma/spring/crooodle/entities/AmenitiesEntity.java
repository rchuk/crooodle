package org.ukma.spring.crooodle.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Embeddable
public class AmenitiesEntity {
    private boolean wifi; // Чи є Wi-Fi
    private boolean airConditioning; // Чи є кондиціонер
    private boolean tv; // Чи є телевізор
    private boolean minibar; // Чи є міні-бар
    private boolean breakfastIncluded; // Чи включений сніданок
    private boolean poolAccess; // Чи є доступ до басейну
    private boolean parking; // Чи є парковка
    private boolean gymAccess; // Чи є доступ до спортзалу
}
