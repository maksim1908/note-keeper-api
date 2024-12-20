package org.example.NoteKeeperApi.Dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterDto {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 35)
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 128)
    private String password;

    @Email
    @NotNull
    private String email;
}
