package org.ukma.spring.crooodle.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.AccessTokenResponseDto;
import org.ukma.spring.crooodle.dto.UserLoginRequestDto;
import org.ukma.spring.crooodle.dto.UserRegisterRequestDto;
import org.ukma.spring.crooodle.dto.common.PublicErrorDto;
import org.ukma.spring.crooodle.dto.common.PublicValidationErrorDto;
import org.ukma.spring.crooodle.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ok",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccessTokenResponseDto.class)
                )
            }
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PublicValidationErrorDto.class)
                )
            }
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PublicErrorDto.class)
                )
            }
        )
    })
    @SecurityRequirements
    @Operation(operationId = "register")
    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public AccessTokenResponseDto register(@RequestBody @Valid UserRegisterRequestDto requestDto) {
        return service.register(requestDto);
    }

    @SecurityRequirements
    @Operation(operationId = "login")
    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public AccessTokenResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return service.login(requestDto);
    }
}
