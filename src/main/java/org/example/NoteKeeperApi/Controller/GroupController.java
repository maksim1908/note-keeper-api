package org.example.NoteKeeperApi.Controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.NoteKeeperApi.Dto.Group.GroupPersistDto;
import org.example.NoteKeeperApi.Dto.Group.GroupResponseDto;
import org.example.NoteKeeperApi.Service.GroupService.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.NoteKeeperApi.Controller.GroupController.ROOT_PATH;

@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    @Operation(summary = "Get all groups")
    public List<GroupResponseDto> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get group by id")
    public GroupResponseDto getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id);
    }

    @PostMapping
    @Operation(summary = "Create new group")
    public GroupResponseDto createGroup(@RequestBody @Valid GroupPersistDto group) {
        return groupService.createGroup(group);
    }

    @PutMapping("{id}")
    @Operation(summary = "Edit group")
    public GroupResponseDto editGroup(@PathVariable Long id, @RequestBody @Valid GroupPersistDto group) {
        return groupService.editGroup(id, group);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete group by id")
    public void deleteGroup(@PathVariable Long id) {
        groupService.removeGroup(id);
    }

    public static final String  ROOT_PATH = "/api/group";
}
