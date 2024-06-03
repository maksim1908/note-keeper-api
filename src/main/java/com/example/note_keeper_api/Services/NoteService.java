package com.example.note_keeper_api.Services;

import com.example.note_keeper_api.Entities.NoteEntity;
import com.example.note_keeper_api.Repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public NoteEntity updateNote(Long id,NoteEntity noteDetails){
        return  noteRepo.findById(id).map(note->{
                    note.setContent(noteDetails.getContent());
                    note.setTitle(noteDetails.getTitle());
                    note.setTimeOfLastModification(LocalDateTime.now());
                    return noteRepo.save(note);
                })
                .orElse(null);
    }

    public void deleteNote(Long id){
        noteRepo.deleteById(id);
    }

}
