package org.example.NoteKeeperApi.Repository;

import org.example.NoteKeeperApi.Entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {
    Page<Note> findAllByUserId(Long userId, Pageable pageable);
    Note findByIdAndUserId(Long id, Long userId);
}
