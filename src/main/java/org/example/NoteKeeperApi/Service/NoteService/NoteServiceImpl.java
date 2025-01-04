package org.example.NoteKeeperApi.Service.NoteService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.NoteKeeperApi.Dto.Note.NoteFilterDto;
import org.example.NoteKeeperApi.Dto.Note.NotePersistDto;
import org.example.NoteKeeperApi.Dto.Note.NoteResponseDto;
import org.example.NoteKeeperApi.Entity.Group;
import org.example.NoteKeeperApi.Entity.Note;
import org.example.NoteKeeperApi.Exception.ServiceExceptions.GroupNotFoundException;
import org.example.NoteKeeperApi.Exception.ServiceExceptions.NoteNotFoundException;
import org.example.NoteKeeperApi.Mapper.NoteMapper;
import org.example.NoteKeeperApi.Repository.GroupRepo;
import org.example.NoteKeeperApi.Repository.NoteRepo;
import org.example.NoteKeeperApi.Service.BaseService;
import org.example.NoteKeeperApi.Service.SchedulerService.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl extends BaseService implements NoteService {
    private final NoteRepo noteRepo;
    private final GroupRepo groupRepo;
    private final SchedulerService schedulerService;
    private final Logger LOGGER = LoggerFactory.getLogger(NoteService.class);
    private final NoteMapper noteMapper;


    @Override
    public Page<NoteResponseDto> getAllNotes(Pageable pageable) {
        LOGGER.debug("getAllNotes");
        return noteRepo.findAllByUserIdOrderByCreatedAtDesc(getActiveUser().getId(), pageable)
                .map(noteMapper::toDto);
    }

    @Override
    public NoteResponseDto getNoteById(Long noteId) {
        LOGGER.debug("getNoteById {}", noteId);
        Note foundedNote = findNoteById(noteId);
        LOGGER.debug("founded note {}", foundedNote);
        return noteMapper.toDto(foundedNote);
    }

    private Note findNoteById(Long noteId) {
        Note foundedNote = noteRepo.findByIdAndUserId(noteId, getActiveUser().getId());
        if (foundedNote == null) throw new NoteNotFoundException();
        return foundedNote;
    }

    @Override
    @SneakyThrows
    public NoteResponseDto createNote(NotePersistDto notePersistDto) {
        LOGGER.debug("createNote {}", notePersistDto);
        Note newNote = noteMapper.toEntity(notePersistDto);
        newNote.setCreatedAt(LocalDateTime.now());
        newNote.setUpdatedAt(LocalDateTime.now());
        newNote.setUser(getActiveUser());
        if (notePersistDto.getGroupId() != null) {
            Group group = groupRepo.findById(notePersistDto.getGroupId())
                    .orElseThrow(GroupNotFoundException::new);
            newNote.setGroup(group);
        }
        Note createdNote = noteRepo.save(newNote);
        schedulerService.scheduleEmailReminder(createdNote);
        LOGGER.debug("created note {}", createdNote);
        return noteMapper.toDto(createdNote);
    }

    @Override
    public NoteResponseDto editNote(Long noteId, NotePersistDto notePersistDto) {
        LOGGER.debug("updateNote {}", noteId);
        Note foundedNote = findNoteById(noteId);
        foundedNote.setTitle(notePersistDto.getTitle());
        foundedNote.setContent(notePersistDto.getContent());
        foundedNote.setUpdatedAt(LocalDateTime.now());
        updateNoteGroup(foundedNote, notePersistDto.getGroupId());
        updateReminderTime(foundedNote, notePersistDto.getReminderTime());
        LOGGER.debug("updated note {}", foundedNote);
        return noteMapper.toDto(noteRepo.save(foundedNote));
    }

    @SneakyThrows
    private void updateReminderTime(Note note, LocalDateTime newReminderTime) {
        if (newReminderTime != null) {
            if (note.getReminderTime() != null) {
                schedulerService.cancelEmailReminder(note.getId());
            }
            note.setReminderTime(newReminderTime);
            schedulerService.scheduleEmailReminder(note);
        } else {
            note.setReminderTime(null);
            schedulerService.cancelEmailReminder(note.getId());
        }
    }

    private void updateNoteGroup(Note note, Long groupId) {
        if (groupId != null) {
            Group group = groupRepo.findByIdAndUserId(groupId, getActiveUser().getId());
            if (group == null) {
                throw new GroupNotFoundException();
            }
            note.setGroup(group);
        } else {
            note.setGroup(null);
        }
    }

    @Override
    @SneakyThrows
    public void deleteNote(Long noteId) {
        Note foundedNote = findNoteById(noteId);
        if (foundedNote.getReminderTime() != null) {
            schedulerService.cancelEmailReminder(foundedNote.getId());
        }
        noteRepo.delete(foundedNote);
    }

    @Override
    public NoteResponseDto moveNoteToGroup(Long noteId, Long destGroupId) {
        LOGGER.debug("move note {} to group {}", noteId, destGroupId);
        Note foundedNote = findNoteById(noteId);
        Group foundedGroup = groupRepo.findById(destGroupId)
                .orElseThrow(GroupNotFoundException::new);
        foundedNote.setGroup(foundedGroup);
        LOGGER.debug("moved note {} to group {}", noteId, destGroupId);
        return noteMapper.toDto(noteRepo.save(foundedNote));
    }

    @Override
    public List<NoteResponseDto> getNotesByFilter(NoteFilterDto noteFilterDto) {
        LOGGER.debug("search note by title {}", noteFilterDto.getTitle());
        return noteRepo.findByFilter(
                        noteFilterDto.getTitle(),
                        getActiveUser().getId())
                .stream()
                .map(noteMapper::toDto)
                .collect(Collectors.toList());
    }
}