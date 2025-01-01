package org.example.NoteKeeperApi.Dto.Note;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteFilterDto {
    @Nullable
    private String title;
}
