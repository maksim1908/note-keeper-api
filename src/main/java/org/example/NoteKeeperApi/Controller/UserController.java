package org.example.NoteKeeperApi.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.NoteKeeperApi.Dto.User.ChangePasswordRequestDto;
import org.example.NoteKeeperApi.Dto.User.UpdateUserRequestDto;
import org.example.NoteKeeperApi.Dto.User.UserResponseDto;
import org.example.NoteKeeperApi.Service.UserService.UserService;
import org.springframework.web.bind.annotation.*;

import static org.example.NoteKeeperApi.Controller.UserController.ROOT_PATH;

@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/change-password")
    public void changeUserPassword(@RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto) {
        userService.changeUserPassword(changePasswordRequestDto);
    }

    @PutMapping
    public UserResponseDto updateUser(@RequestBody @Valid UpdateUserRequestDto updateUserRequestDto) {
        return userService.updateUserInfo(updateUserRequestDto);
    }

    public static final String ROOT_PATH = "/api/user";
}
