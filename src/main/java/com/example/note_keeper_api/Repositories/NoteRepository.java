package com.example.note_keeper_api.Repositories;

import com.example.note_keeper_api.Entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity,Long> {
    List<NoteEntity> findByContentContainingIgnoreCaseOrTitleContainingIgnoreCase(String content, String title);
    List<NoteEntity> findByTitle(String title);
}