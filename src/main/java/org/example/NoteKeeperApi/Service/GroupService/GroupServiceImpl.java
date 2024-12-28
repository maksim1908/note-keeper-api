package org.example.NoteKeeperApi.Service.GroupService;

import lombok.RequiredArgsConstructor;
import org.example.NoteKeeperApi.Dto.Group.GroupPersistDto;
import org.example.NoteKeeperApi.Dto.Group.GroupResponseDto;
import org.example.NoteKeeperApi.Dto.Group.GroupWithoutNotesDto;
import org.example.NoteKeeperApi.Entity.Group;
import org.example.NoteKeeperApi.Exception.ServiceExceptions.GroupAlreadyExistException;
import org.example.NoteKeeperApi.Exception.ServiceExceptions.GroupNotFoundException;
import org.example.NoteKeeperApi.Mapper.GroupMapper;
import org.example.NoteKeeperApi.Repository.GroupRepo;
import org.example.NoteKeeperApi.Service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl extends BaseService implements GroupService {
    private final GroupRepo groupRepo;
    private final GroupMapper groupMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Override
    public Page<GroupWithoutNotesDto> getAllGroups(Pageable pageable) {
        LOGGER.debug("Get all groups");
        return groupRepo.findAllByUserId(getActiveUser().getId(), pageable)
                .map(groupMapper::toDtoWithoutNotes);
    }

    @Override
    public GroupResponseDto getGroupById(Long groupId) {
        LOGGER.debug("Get group by id = {}", groupId);
        Group group = Optional.ofNullable(groupRepo.findByIdAndUserId(groupId, getActiveUser().getId()))
                .orElseThrow(GroupNotFoundException::new);
        LOGGER.debug("Group found {}", group);
        return groupMapper.toDto(group);
    }

    @Override
    public GroupResponseDto createGroup(GroupPersistDto groupPersistDto) {
        LOGGER.debug("Create group {}", groupPersistDto);
        if (groupRepo.findByTitle(groupPersistDto.getTitle()) != null) {
            throw new GroupAlreadyExistException();
        }
        Group newGroup = groupMapper.toEntity(groupPersistDto);
        newGroup.setUser(getActiveUser());
        return groupMapper.toDto(groupRepo.save(newGroup));
    }

    @Override
    public void removeGroup(Long groupId) {
        LOGGER.debug("Remove group ID = {}", groupId);
        Group group = Optional.ofNullable(groupRepo.findByIdAndUserId(groupId, getActiveUser().getId()))
                .orElseThrow(GroupNotFoundException::new);
        groupRepo.delete(group);
    }

    @Override
    public GroupResponseDto editGroup(Long groupId, GroupPersistDto groupPersistDto) {
        LOGGER.debug("Edit group ID = {}", groupId);
        Group group = Optional.ofNullable(groupRepo.findByIdAndUserId(groupId, getActiveUser().getId()))
                .orElseThrow(GroupNotFoundException::new);
        if (groupRepo.findByTitle(groupPersistDto.getTitle()) != null) {
            throw new GroupAlreadyExistException();
        }
        group.setTitle(groupPersistDto.getTitle());
        return groupMapper.toDto(groupRepo.save(group));
    }
}