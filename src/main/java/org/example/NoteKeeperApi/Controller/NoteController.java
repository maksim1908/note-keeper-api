package org.example.NoteKeeperApi.Controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.NoteKeeperApi.Dto.Note.NotePersistDto;
import org.example.NoteKeeperApi.Dto.Note.NoteResponseDto;
import org.example.NoteKeeperApi.Service.NoteService.NoteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static org.example.NoteKeeperApi.Controller.NoteController.ROOT_PATH;

@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping
    @Operation(summary = "Get all notes for user")
    public Page<NoteResponseDto> getAllNotes(Pageable pageable) {
        return noteService.getAllNotes(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Note By id")
    public NoteResponseDto getNoteById(@PathVariable Long id) {
        return noteService.getNoteById(id);
    }

    @PostMapping
    @Operation(summary = "Create new note")
    public NoteResponseDto createNote(@RequestBody @Valid NotePersistDto notePersistDto) {
        return noteService.createNote(notePersistDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update note by id")
    public NoteResponseDto updateNote(@PathVariable Long id, @RequestBody @Valid NotePersistDto notePersistDto) {
        return noteService.editNote(id, notePersistDto);
    }

    @PutMapping("/{id}/moveTo")
    public NoteResponseDto moveNoteToGroup(@PathVariable Long id,
                                           @RequestParam(required = true, name = "groupId") Long groupId) {
        return noteService.moveNoteToGroup(id, groupId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete note by id")
    public void deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
    }

    public static final String ROOT_PATH = "/api/note";
}