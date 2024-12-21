package org.example.NoteKeeperApi.Service.GroupService;

import org.example.NoteKeeperApi.Dto.Group.GroupPersistDto;
import org.example.NoteKeeperApi.Dto.Group.GroupResponseDto;
import org.example.NoteKeeperApi.Dto.Group.GroupWithoutNotesDto;

import java.util.List;

public interface GroupService {
    List<GroupWithoutNotesDto> getAllGroups();
    GroupResponseDto getGroupById(Long id);
    GroupResponseDto createGroup(GroupPersistDto groupPersistDto);
    void removeGroup(Long id);
    GroupResponseDto editGroup(Long id, GroupPersistDto groupPersistDto);
}
