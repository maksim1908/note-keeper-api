package org.example.NoteKeeperApi.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.NoteKeeperApi.Dto.Group.GroupPersistDto;
import org.example.NoteKeeperApi.Dto.Group.GroupResponseDto;
import org.example.NoteKeeperApi.Dto.Group.GroupWithoutNotesDto;
import org.example.NoteKeeperApi.Service.GroupService.GroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.example.NoteKeeperApi.Controller.GroupController.ROOT_PATH;

@Tag(name = "Group Management", description = "Endpoints for managing groups, including creating, updating, deleting, and grouping notes.")
@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @Operation(summary = "Get all groups", description = "Retrieve a paginated list of all groups.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Groups retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public Page<GroupWithoutNotesDto> getAllGroups(Pageable pageable) {
        return groupService.getAllGroups(pageable);
    }

    @Operation(summary = "Get group by ID", description = "Fetch details of a specific group by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group retrieved successfully",
                    content = @Content(schema = @Schema(implementation = GroupResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Group not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/{id}")
    public GroupResponseDto getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id);
    }

    @Operation(summary = "Create a new group", description = "Create a new group with the provided details.")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Group created successfully",
                    content = @Content(schema = @Schema(implementation = GroupResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResponseDto createGroup(@RequestBody @Valid GroupPersistDto group) {
        return groupService.createGroup(group);
    }

    @Operation(summary = "Update group by ID", description = "Update the details of an existing group by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group updated successfully",
                    content = @Content(schema = @Schema(implementation = GroupResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Group not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping("{id}")
    public GroupResponseDto editGroup(@PathVariable Long id, @RequestBody @Valid GroupPersistDto group) {
        return groupService.editGroup(id, group);
    }

    @Operation(summary = "Delete group by ID", description = "Delete a specific group by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Group deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Group not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@PathVariable Long id) {
        groupService.removeGroup(id);
    }

    public static final String ROOT_PATH = "/api/group";
}
