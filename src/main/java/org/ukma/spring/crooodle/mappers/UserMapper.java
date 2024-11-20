package org.ukma.spring.crooodle.mappers;

import org.mapstruct.Mapper;
import org.ukma.spring.crooodle.entities.UserEntity;
import org.ukma.spring.crooodle.entities.UserPermissionEntity;
import org.ukma.spring.crooodle.entities.enums.UserPermission;
import org.ukma.spring.crooodle.model.User;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User entityToModel(UserEntity userEntity);

    Set<UserPermission> map(Set<UserPermissionEntity> value);

    default UserPermission map(UserPermissionEntity value) {
        return value.getKind();
    }
}
