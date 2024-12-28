package org.example.NoteKeeperApi.Service.UserService;

import lombok.RequiredArgsConstructor;
import org.example.NoteKeeperApi.Dto.User.ChangePasswordRequestDto;
import org.example.NoteKeeperApi.Dto.User.UpdateUserRequestDto;
import org.example.NoteKeeperApi.Dto.User.UserResponseDto;
import org.example.NoteKeeperApi.Entity.User;
import org.example.NoteKeeperApi.Exception.ServiceExceptions.UserAlreadyExistException;
import org.example.NoteKeeperApi.Exception.ServiceExceptions.UserNotFoundException;
import org.example.NoteKeeperApi.Mapper.UserMapper;
import org.example.NoteKeeperApi.Repository.UserRepo;
import org.example.NoteKeeperApi.Service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseService implements UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetailsService getDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            }
        };
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return userMapper.toDto(userRepo.findById(id)
                .orElseThrow(UserNotFoundException::new)
        );
    }

    @Override
    public void changeUserPassword(ChangePasswordRequestDto changePasswordRequestDto) {
        if (!passwordEncoder.matches(changePasswordRequestDto.getOldPassword(), getActiveUser().getPassword())) {
            throw new BadCredentialsException("Old Password do not match");
        }
        if (!changePasswordRequestDto.getNewPassword().equals(changePasswordRequestDto.getConfirmPassword())) {
            throw new BadCredentialsException("Confirm Password do not match");
        }
        User activeUser = getActiveUser();
        activeUser.setPassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));
        userRepo.save(activeUser);
    }

    @Override
    public UserResponseDto updateUserInfo(UpdateUserRequestDto updateUserRequestDto) {
        User activeUser = getActiveUser();

        if (updateUserRequestDto.getUsername() != null) {
            if (userRepo.findByUsername(updateUserRequestDto.getUsername()).isPresent()) {
                throw new UserAlreadyExistException();
            }
            activeUser.setUsername(updateUserRequestDto.getUsername());
        }

        if (updateUserRequestDto.getEmail() != null) {
            if (userRepo.findByEmail(updateUserRequestDto.getEmail()) != null) {
                throw new UserAlreadyExistException("User with email already exists");
            }
            activeUser.setEmail(updateUserRequestDto.getEmail());
        }

        if (updateUserRequestDto.getName() != null) {
            activeUser.setName(updateUserRequestDto.getName());
        }

        return userMapper.toDto(userRepo.save(activeUser));
    }
}
