package org.ukma.spring.crooodle.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.ukma.spring.crooodle.entities.enums.UserPermission;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "permissions")
public class UserPermissionEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private UserPermission kind;

    @ManyToMany(mappedBy = "permissions")
    private Set<UserEntity> users;

    @Override
    public String getAuthority() {
        return kind.name();
    }
}
