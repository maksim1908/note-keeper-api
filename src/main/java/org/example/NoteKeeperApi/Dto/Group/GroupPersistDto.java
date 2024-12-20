package org.example.NoteKeeperApi.Dto.Group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupPersistDto {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 255, message = "")
    private String title;
}
