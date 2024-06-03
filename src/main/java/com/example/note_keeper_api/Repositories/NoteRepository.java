package com.example.note_keeper_api.Repositories;

import com.example.note_keeper_api.Entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity,Long> {

}
