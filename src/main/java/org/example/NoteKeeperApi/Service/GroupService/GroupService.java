package org.example.NoteKeeperApi.Service.GroupService;

import org.example.NoteKeeperApi.Dto.Group.GroupPersistDto;
import org.example.NoteKeeperApi.Dto.Group.GroupResponseDto;
import org.example.NoteKeeperApi.Dto.Group.GroupWithoutNotesDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface GroupService {
    Page<GroupWithoutNotesDto> getAllGroups(Pageable pageable);

    GroupResponseDto getGroupById(Long id);

    GroupResponseDto createGroup(GroupPersistDto groupPersistDto);

    void removeGroup(Long id);

    GroupResponseDto editGroup(Long id, GroupPersistDto groupPersistDto);
}
