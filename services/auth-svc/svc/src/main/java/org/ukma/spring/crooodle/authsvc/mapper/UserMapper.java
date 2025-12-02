package org.ukma.spring.crooodle.authsvc.mapper;

import org.mapstruct.Mapper;
import org.ukma.spring.crooodle.authsvc.dto.RegisterRoleDto;
import org.ukma.spring.crooodle.authsvc.dto.UserDto;
import org.ukma.spring.crooodle.authsvc.entity.UserEntity;
import org.ukma.spring.crooodle.authsvc.entity.UserRole;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserDto userToUserDto(UserEntity user);

	UserRole registerRoleDtoToUserRole(RegisterRoleDto registerRoleDto);
}
