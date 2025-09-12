package org.ukma.spring.crooodle.usersvc;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final UserSvc userSvc;

    // TODO: Replace with actual main page
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void home() {

    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(@Valid @RequestBody UserRegisterDto requestDto) {
        return userSvc.register(requestDto);
    }
}