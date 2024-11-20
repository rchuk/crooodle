package org.ukma.spring.crooodle.model;

import lombok.Builder;
import lombok.Data;
import org.ukma.spring.crooodle.entities.enums.UserPermission;

import java.util.Set;

@Builder
@Data
public class User {
    String name;
    String email;

    Set<UserPermission> permissions;
}
