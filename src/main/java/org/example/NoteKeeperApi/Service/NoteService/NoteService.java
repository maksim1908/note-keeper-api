package org.example.NoteKeeperApi.Service.NoteService;

import org.example.NoteKeeperApi.Dto.Note.NoteFilterDto;
import org.example.NoteKeeperApi.Dto.Note.NotePersistDto;
import org.example.NoteKeeperApi.Dto.Note.NoteResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoteService {
    Page<NoteResponseDto> getAllNotes(Pageable pageable);

    NoteResponseDto getNoteById(Long noteId);

    NoteResponseDto createNote(NotePersistDto notePersistDto);

    NoteResponseDto editNote(Long id, NotePersistDto notePersistDto);

    void deleteNote(Long id);

    NoteResponseDto moveNoteToGroup(Long noteId, Long destGroupId);

    List<NoteResponseDto> getNotesByFilter(NoteFilterDto noteFilterDto);
}
