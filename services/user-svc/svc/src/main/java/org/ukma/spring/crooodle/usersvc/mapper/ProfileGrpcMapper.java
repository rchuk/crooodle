package org.ukma.spring.crooodle.usersvc.mapper;

import org.mapstruct.Mapper;
import org.ukma.spring.crooodle.usersvc.dto.ProfileDto;
import org.ukma.spring.crooodle.usersvc.dto.ProfileUpsertDto;
import org.ukma.spring.crooodle.usersvc.grpc.Profile;
import org.ukma.spring.crooodle.usersvc.grpc.ProfileUpsert;

@Mapper(
    componentModel = "spring"
)
public interface ProfileGrpcMapper {
    ProfileUpsertDto profileUpsertToProfileUpsertDto (ProfileUpsert profileUpsert);
    Profile profileDtoToProfile (ProfileDto profile);
}
