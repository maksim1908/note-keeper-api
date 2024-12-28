package org.example.NoteKeeperApi.Controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.NoteKeeperApi.Dto.User.*;
import org.example.NoteKeeperApi.Service.AuthService.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.example.NoteKeeperApi.Controller.AuthController.ROOT_PATH;

@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Register a new user", description = "Register a new user with the provided credentials.")
    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRegisterDto registerRequestDto) {
        return authService.register(registerRequestDto);
    }

    @Operation(summary = "Sign in", description = "Authenticate a user and return a JWT token.")
    @PostMapping("/sign-in")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    @Operation(summary = "Refresh JWT token", description = "Refresh an existing JWT token using a valid refresh token.")
    @PostMapping("/refresh-token")
    public RefreshTokenResponseDto refresh(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return authService.refresh(refreshTokenRequestDto);
    }

    public final static String ROOT_PATH = "/api/auth";
}
