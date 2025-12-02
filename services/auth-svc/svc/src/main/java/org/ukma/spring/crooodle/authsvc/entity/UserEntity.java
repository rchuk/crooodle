package org.ukma.spring.crooodle.authsvc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(unique = true)
	private String username;
	private String password;

	@Enumerated(EnumType.STRING)
	private UserRole role;
}

