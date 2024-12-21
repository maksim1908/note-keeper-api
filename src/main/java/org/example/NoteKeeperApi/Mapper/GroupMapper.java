package org.example.NoteKeeperApi.Mapper;

import jakarta.persistence.Column;
import org.example.NoteKeeperApi.Dto.Group.GroupPersistDto;
import org.example.NoteKeeperApi.Dto.Group.GroupResponseDto;
import org.example.NoteKeeperApi.Dto.Group.GroupWithoutNotesDto;
import org.example.NoteKeeperApi.Dto.Note.NoteResponseDto;
import org.example.NoteKeeperApi.Entity.Group;
import org.example.NoteKeeperApi.Entity.Note;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupMapper {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private NoteMapper noteMapper;

    public GroupResponseDto toDto(Group group) {
        if (group == null) {
            return null;
        }
        GroupResponseDto groupResponseDto = modelMapper.map(group, GroupResponseDto.class);
        groupResponseDto.setNotes(notesToDto(group.getNotes()));
        return groupResponseDto;
    }

    public GroupWithoutNotesDto toDtoWithoutNotes(Group group) {
        if (group == null) {
            return null;
        }
        return modelMapper.map(group, GroupWithoutNotesDto.class);
    }

    public Group toEntity(GroupPersistDto groupDto) {
        if (groupDto == null) {
            return null;
        }
        return modelMapper.map(groupDto, Group.class);
    }

    private List<NoteResponseDto> notesToDto(List<Note> notes) {
        return notes == null || notes.isEmpty() ? null : notes.stream()
                .map(note -> noteMapper.toDto(note))
                .collect(Collectors.toList());
    }
}
