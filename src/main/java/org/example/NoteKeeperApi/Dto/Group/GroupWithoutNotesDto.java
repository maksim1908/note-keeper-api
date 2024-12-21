package org.example.NoteKeeperApi.Dto.Group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupWithoutNotesDto {
    private Long id;
    private String title;
}
