package org.example.NoteKeeperApi.Dto.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequestDto {
    @NotNull
    @NotBlank
    @Size(min = 8, max = 128)
    private String oldPassword;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 128)
    private String newPassword;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 128)
    private String confirmPassword;
}
