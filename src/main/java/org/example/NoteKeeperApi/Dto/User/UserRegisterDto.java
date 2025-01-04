package org.example.NoteKeeperApi.Dto.User;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegisterDto {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters.")
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 35, message = "Username must be between 1 and 35 characters.")
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters.")
    private String password;

    @Email
    @NotNull
    @Pattern(regexp = ".+@.+\\..+", message = "Please enter a valid email address.")
    private String email;
}
