package org.example.NoteKeeperApi.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.NoteKeeperApi.Dto.User.ChangePasswordRequestDto;
import org.example.NoteKeeperApi.Dto.User.UpdateUserRequestDto;
import org.example.NoteKeeperApi.Dto.User.UserResponseDto;
import org.example.NoteKeeperApi.Exception.ServiceExceptions.UserNotFoundException;
import org.example.NoteKeeperApi.Service.UserService.UserService;
import org.springframework.web.bind.annotation.*;

import static org.example.NoteKeeperApi.Controller.UserController.ROOT_PATH;

@Tag(name = "User Management", description = "Endpoints for managing users, including fetching, updating, and changing passwords.")
@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get user by ID", description = "Fetch user details by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = UserNotFoundException.class)))
    })
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Change user password", description = "Change the password of the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping("/change-password")
    public void changeUserPassword(@RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto) {
        userService.changeUserPassword(changePasswordRequestDto);
    }

    @Operation(summary = "Update user information", description = "Update user profile details such as name or email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping
    public UserResponseDto updateUser(@RequestBody @Valid UpdateUserRequestDto updateUserRequestDto) {
        return userService.updateUserInfo(updateUserRequestDto);
    }

    public static final String ROOT_PATH = "/api/user";
}
