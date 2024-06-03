package com.example.note_keeper_api.Controllers;

import com.example.note_keeper_api.DTO.NoteDTO;
import com.example.note_keeper_api.Entities.NoteEntity;
import com.example.note_keeper_api.Services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class NoteController{

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/notes")
    public List<NoteEntity> getAllNotes(){
        return noteService.getAllNotes();
    }

    @GetMapping("/notes/{id}")
    public NoteEntity getNoteById(@PathVariable Long id){
        return noteService.getNoteById(id);
    }

    @PostMapping("/notes/create")
    public NoteEntity createNote(@RequestBody NoteDTO noteDTO){
        return noteService.createNote(new NoteEntity(noteDTO.getTitle(),noteDTO.getContent()));
    }

    @PutMapping("notes/update/{id}")
    public NoteEntity updateNote(@PathVariable Long id,@RequestBody NoteDTO noteDTO){
        return noteService.updateNote(
                id,
                NoteDTO.toNoteEntity(noteDTO)
        );
    }

    @DeleteMapping("/notes/delete/{id}")
    public void deleteNote(@PathVariable Long id){
        noteService.deleteNote(id);
    }
}
