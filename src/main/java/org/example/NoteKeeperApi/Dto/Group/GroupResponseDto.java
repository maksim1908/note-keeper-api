package org.example.NoteKeeperApi.Dto.Group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.NoteKeeperApi.Dto.Note.NoteResponseDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponseDto {
    private Long id;
    private String title;
    private List<NoteResponseDto> notes;
}
