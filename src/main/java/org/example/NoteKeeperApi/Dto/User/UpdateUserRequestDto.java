package org.example.NoteKeeperApi.Dto.User;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDto {
    @Nullable
    @Size(min = 1, max = 50)
    private String username;

    @Nullable
    @Size(min = 1, max = 35)
    private String name;

    @Email
    @Nullable
    private String email;
}
