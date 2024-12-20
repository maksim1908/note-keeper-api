package org.example.NoteKeeperApi.Mapper;

import org.example.NoteKeeperApi.Dto.Note.NotePersistDto;
import org.example.NoteKeeperApi.Dto.Note.NoteResponseDto;
import org.example.NoteKeeperApi.Entity.Note;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {
    @Autowired
    private ModelMapper modelMapper;

    public Note toEntity(NotePersistDto noteDto) {
        if (noteDto == null) {
            return null;
        }
        return modelMapper.map(noteDto, Note.class);
    }

    public NoteResponseDto toDto(Note note) {
        if (note == null) {
            return null;
        }
        NoteResponseDto noteDto = modelMapper.map(note, NoteResponseDto.class);
        if (note.getGroup() != null) {
            noteDto.setGroupId(note.getGroup().getId());
        }
        return noteDto;
    }
}
