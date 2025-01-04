package org.example.NoteKeeperApi.Dto.Note;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotePersistDto {
    @NotNull
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters.")
    private String title;

    @Nullable
    private String content;

    @Nullable
    private LocalDateTime reminderTime;

    @Nullable
    private Long groupId;
}
