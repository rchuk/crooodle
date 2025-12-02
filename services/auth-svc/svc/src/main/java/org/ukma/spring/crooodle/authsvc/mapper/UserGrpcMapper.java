package org.ukma.spring.crooodle.authsvc.mapper;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.ukma.spring.crooodle.authsvc.grpc.User;
import org.ukma.spring.crooodle.authsvc.entity.UserEntity;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface UserGrpcMapper {
	User userToUserDto(UserEntity user);
}
