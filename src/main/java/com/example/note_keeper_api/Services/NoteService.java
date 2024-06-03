package com.example.note_keeper_api.Services;

import com.example.note_keeper_api.Entities.NoteEntity;
import com.example.note_keeper_api.Repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepo;

    @Autowired
    public NoteService(NoteRepository noteRepo) {
        this.noteRepo = noteRepo;
    }

    public List<NoteEntity> getAllNotes(){
        return noteRepo.findAll();
    }

    public NoteEntity getNoteById(Long id){
        return noteRepo.findById(id).orElse(null);
    }

    public NoteEntity createNote(NoteEntity note){
        return noteRepo.save(note);
    }

    public NoteEntity updateNote(NoteEntity note){
        return noteRepo.save(note);
    }

    public void deleteNote(Long id){
        noteRepo.deleteById(id);
    }

}
