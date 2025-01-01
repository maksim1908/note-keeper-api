package org.example.NoteKeeperApi.Repository;

import org.example.NoteKeeperApi.Entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {
    Page<Note> findAllByUserId(Long userId, Pageable pageable);

    Note findByIdAndUserId(Long id, Long userId);

    @Query("""
                SELECT n
                FROM Note n
                WHERE (:title IS NULL OR LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%')))
                  AND n.user.id = :userId
            """)
    List<Note> findByFilter(
            @Param("title") String title,
            @Param("userId") Long userId
    );
}
