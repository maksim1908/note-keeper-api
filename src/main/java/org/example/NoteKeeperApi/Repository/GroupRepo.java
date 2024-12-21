package org.example.NoteKeeperApi.Repository;

import org.example.NoteKeeperApi.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepo extends JpaRepository<Group, Long> {
    Group findByTitle(String title);

    Group findByIdAndUserId(Long id, Long userId);

    List<Group> findAllByUserId(Long userId);
}
