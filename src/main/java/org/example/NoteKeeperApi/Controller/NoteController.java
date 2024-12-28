package org.example.NoteKeeperApi.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.NoteKeeperApi.Dto.Note.NotePersistDto;
import org.example.NoteKeeperApi.Dto.Note.NoteResponseDto;
import org.example.NoteKeeperApi.Service.NoteService.NoteService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.example.NoteKeeperApi.Controller.NoteController.ROOT_PATH;

@Tag(name = "Notes Management", description = "Endpoints for managing notes, including creating, updating, deleting, and grouping notes.")
@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping
    @Operation(summary = "Get all notes for user")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved all notes for the user."),
                    @ApiResponse(responseCode = "401", description = "Authorization is required.")}
    )
    @PageableAsQueryParam
    public Page<NoteResponseDto> getAllNotes(Pageable pageable) {
        return noteService.getAllNotes(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Note By id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the note with the specified ID."),
                    @ApiResponse(responseCode = "404", description = "Note not found."),
                    @ApiResponse(responseCode = "401", description = "Authorization is required")
            }
    )
    public NoteResponseDto getNoteById(@PathVariable Long id) {
        return noteService.getNoteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new note")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Note successfully created."),
                    @ApiResponse(responseCode = "400", description = "Invalid request. Please ensure all required fields are correctly filled."),
                    @ApiResponse(responseCode = "401", description = "Authorization is required.")
            }
    )
    public NoteResponseDto createNote(@RequestBody @Valid NotePersistDto notePersistDto) {
        return noteService.createNote(notePersistDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update note by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Note successfully created."),
                    @ApiResponse(responseCode = "404", description = "Note not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid request. Please ensure all required fields are correctly filled."),
                    @ApiResponse(responseCode = "401", description = "Authorization is required.")
            }
    )
    public NoteResponseDto updateNote(@PathVariable Long id, @RequestBody @Valid NotePersistDto notePersistDto) {
        return noteService.editNote(id, notePersistDto);
    }

    @PutMapping("/{id}/moveTo")
    @Operation(summary = "Move note to selected group")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Note successfully moved to selected group."),
                    @ApiResponse(responseCode = "404", description = "Note/Group not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid request. Please ensure all required fields are correctly filled."),
                    @ApiResponse(responseCode = "401", description = "Authorization is required.")
            }
    )
    public NoteResponseDto moveNoteToGroup(@PathVariable Long id,
                                           @RequestParam(required = true, name = "groupId") Long groupId) {
        return noteService.moveNoteToGroup(id, groupId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete note by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Note successfully deleted."),
                    @ApiResponse(responseCode = "404", description = "Note not found"),
                    @ApiResponse(responseCode = "401", description = "Authorization is required.")
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
    }

    public static final String ROOT_PATH = "/api/note";
}