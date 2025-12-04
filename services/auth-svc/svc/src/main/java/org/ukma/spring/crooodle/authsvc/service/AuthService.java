package org.ukma.spring.crooodle.authsvc.service;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.authsvc.dto.LoginRequestDto;
import org.ukma.spring.crooodle.authsvc.dto.LoginResponseDto;
import org.ukma.spring.crooodle.authsvc.dto.UserRegisterDto;
import org.ukma.spring.crooodle.authsvc.entity.UserEntity;
import org.ukma.spring.crooodle.authsvc.mapper.UserMapper;
import org.ukma.spring.crooodle.authsvc.repository.UserRepository;
import org.ukma.spring.crooodle.authsvc.util.JwtUtil;
import org.ukma.spring.crooodle.errors.SvcError;
import org.ukma.spring.crooodle.errors.SvcException;
import org.ukma.spring.crooodle.usersvc.grpc.ProfileServiceGrpc;
import org.ukma.spring.crooodle.usersvc.grpc.ProfileUpsert;

@RequiredArgsConstructor
@Service
public class AuthService {
	private final UserMapper userMapper;
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@GrpcClient("profileService")
	private ProfileServiceGrpc.ProfileServiceBlockingStub profileService;

	public LoginResponseDto login(LoginRequestDto request) {
		var user = userRepository.findByUsername(request.username()).orElseThrow(() -> new SvcException(SvcError.NOT_FOUND));
		if (!passwordEncoder.matches(request.password(), user.getPassword()))
			throw new SvcException(SvcError.UNAUTHORIZED, "Invalid username or password");

		return LoginResponseDto.builder()
			.token(jwtUtil.generateToken(user.getUsername()))
			.build();
	}

	public void register(UserRegisterDto request) {
		if (userRepository.existsByUsername(request.username()))
			throw new SvcException(SvcError.INVALID_REQUEST, "User already exists");

		var user = UserEntity.builder()
			.username(request.username())
			.password(passwordEncoder.encode(request.password()))
			.role(userMapper.registerRoleDtoToUserRole(request.role()))
			.build();
		user = userRepository.save(user);

		var profile = ProfileUpsert.newBuilder().setUserId(user.getId().toString()).build();
		profileService.createProfile(profile);
	}

	public UserEntity getUserEntity(String token) {
		var username = jwtUtil.validateTokenAndGetUsername(token);
		if (username == null)
			throw new SvcException(SvcError.UNAUTHORIZED, "Invalid token");

		var user = userRepository.findByUsername(username);

		return user.orElseThrow(() -> new SvcException(SvcError.UNAUTHORIZED, "User no longer exists"));
	}
}
