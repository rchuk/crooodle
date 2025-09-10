package org.ukma.spring.crooodle.usersvc.internal;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.ukma.spring.crooodle.usersvc.Role;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 100)
    private Role role;

    public GrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(role.name());
    }
}