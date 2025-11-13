package org.ukma.spring.crooodle.usersvc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.usersvc.dto.*;
import org.ukma.spring.crooodle.usersvc.entity.UserEntity;
//import org.ukma.spring.crooodle.usersvc.messaging.p2p.UserProducer;
import org.ukma.spring.crooodle.usersvc.service.JwtService;
import org.ukma.spring.crooodle.usersvc.service.UserSvc;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AuthController {
	private final UserSvc userSvc;
	private final AuthenticationManager auth;
	private final JwtService jwt;
//	private final UserProducer userProducer;

	// TODO: Replace with actual main page
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void home() {

    }

	@PostMapping("/login")
	public LoginResponseDto login(@RequestBody LoginRequestDto req) {
		var result = auth.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.password()));
		var principal = (UserEntity)result.getPrincipal();
		var token = jwt.issue(principal.getEmail(), principal.getRole().getUserRole().name(), Map.of("uid", principal.getId().toString()));
//		userProducer.sendLoggedInEvent(req.email());
		return LoginResponseDto.builder().token(token).build();
	}

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(@Valid @RequestBody UserRegisterDto requestDto) {
        return userSvc.register(requestDto);
    }

    @GetMapping("/me")
    public UserResponseDto getCurrentUser() {
        return userSvc.getCurrentUser();
    }

    @GetMapping("/me/role")
    public UserRole getCurrentUserRole() {
        return userSvc.getCurrentUserRole();
    }
}
