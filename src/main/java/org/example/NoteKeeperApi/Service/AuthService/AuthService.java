package org.example.NoteKeeperApi.Service.AuthService;

import lombok.RequiredArgsConstructor;
import org.example.NoteKeeperApi.Dto.User.*;
import org.example.NoteKeeperApi.Entity.AppRole;
import org.example.NoteKeeperApi.Entity.User;
import org.example.NoteKeeperApi.Exception.ServiceExceptions.UserAlreadyExistException;
import org.example.NoteKeeperApi.Exception.ServiceExceptions.UserNotFoundException;
import org.example.NoteKeeperApi.Mapper.UserMapper;
import org.example.NoteKeeperApi.Repository.UserRepo;
import org.example.NoteKeeperApi.Service.JwtService.JwtSecurityService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserMapper userMapper;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    private final JwtSecurityService jwtSecurityService;
    private final AuthenticationManager authenticationManager;

    public UserResponseDto register(UserRegisterDto registerRequestDto) {
        if (userRepo.findByUsername(registerRequestDto.getUsername()) != null) {
            throw new UserAlreadyExistException();
        }
        if (userRepo.findByEmail(registerRequestDto.getEmail()) != null) {
            throw new UserAlreadyExistException();
        }
        User appUser = User.builder()
                .username(registerRequestDto.getUsername())
                .email(registerRequestDto.getEmail())
                .name(registerRequestDto.getName())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .role(AppRole.USER)
                .build();
        return userMapper.toDto(userRepo.save(appUser));
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequestDto.getUsername(),
                                loginRequestDto.getPassword()
                        )
                );
        User user = findUserByUsername(loginRequestDto.getUsername());
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Passwords do not match");
        }
        String token = jwtSecurityService.generateToken(user);
        String refreshToken = jwtSecurityService.generateRefreshToken(new HashMap<>(), user);

        return LoginResponseDto
                .builder()
                .username(loginRequestDto.getUsername())
                .jwtToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    public RefreshTokenResponseDto refresh(RefreshTokenRequestDto refreshTokenRequestDto) {
        String jwt = refreshTokenRequestDto.getRefreshToken();
        String username = jwtSecurityService.extractUsername(jwt);

        User user = findUserByUsername(username);

        if (jwtSecurityService.validateToken(jwt, user)) {
            RefreshTokenResponseDto refreshTokenResponseDto = new RefreshTokenResponseDto();
            refreshTokenResponseDto.setJwtToken(jwtSecurityService.generateToken(user));
            refreshTokenResponseDto.setRefreshToken(jwtSecurityService.generateRefreshToken(new HashMap<>(), user));
            return refreshTokenResponseDto;
        }
        return null;
    }

    private User findUserByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }
}
