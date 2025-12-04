package org.ukma.spring.crooodle.usersvc.mapper;

import org.mapstruct.Mapper;
import org.ukma.spring.crooodle.usersvc.dto.ProfileDto;
import org.ukma.spring.crooodle.usersvc.dto.ProfileUpsertDto;
import org.ukma.spring.crooodle.usersvc.entity.ProfileEntity;

@Mapper(
    componentModel = "spring"
)
public interface ProfileMapper {
    ProfileDto profileEntityToProfileDto(ProfileEntity profileEntity);
    ProfileEntity profileUpsertDtoToProfileEntity(ProfileUpsertDto profileUpsertDto);
}
