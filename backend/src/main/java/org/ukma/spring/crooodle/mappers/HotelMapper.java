package org.ukma.spring.crooodle.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.entities.CountryEntity;
import org.ukma.spring.crooodle.entities.HotelEntity;
import org.ukma.spring.crooodle.exception.PublicNotFoundException;
import org.ukma.spring.crooodle.repository.CountryRepository;

@Component
@Mapper(componentModel = "spring")
public abstract class HotelMapper {
    @Autowired
    private CountryRepository countryRepository;

    @Mapping(source = "countryId", target = "country", qualifiedByName = "mapCountryIdToCountry")
    public abstract HotelEntity dtoToEntity(HotelCreateRequestDto requestDto);
    @Mapping(source = "countryId", target = "country", qualifiedByName = "mapCountryIdToCountry")
    public abstract void update(@MappingTarget HotelEntity entity, HotelEditRequestDto requestDto);

    public abstract HotelResponseDto entityToDto(HotelEntity entity);
    public abstract HotelAdminResponseDto entityToAdminDto(HotelEntity entity);

    public Specification<HotelEntity> criteriaToSpec(HotelCriteriaDto criteriaDto) {
        return (root, r1, builder) -> {
            var query = criteriaDto.getQuery() != null
                ? builder.like(builder.lower(root.get("name")), "%" + criteriaDto.getQuery().toLowerCase() + "%")
                : builder.conjunction();
            var hotelId = criteriaDto.getCountryId() != null
                ? builder.equal(root.get("country_id"), criteriaDto.getCountryId())
                : builder.conjunction();

            return builder.and(query, hotelId);
        };
    }

    @Named("mapCountryIdToCountry")
    public CountryEntity mapCountryIdToCountry(Integer countryId) {
        return countryRepository.findById(countryId).orElseThrow(PublicNotFoundException::new);
    }
}
