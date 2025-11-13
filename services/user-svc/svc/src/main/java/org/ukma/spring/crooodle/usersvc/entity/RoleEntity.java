package org.ukma.spring.crooodle.usersvc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.ukma.spring.crooodle.usersvc.dto.UserRole;

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
		@Column(name = "role", nullable = false, unique = true, length = 100)
    private UserRole userRole;

    public GrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(userRole.name());
    }
}
