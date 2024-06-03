package com.example.note_keeper_api.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDateTime timeOfCreation;
    private LocalDateTime timeOfLastModification;

    public NoteEntity(){

    }
    public NoteEntity(String title, String content) {
        this.title = title;
        this.content = content;
        this.timeOfCreation = LocalDateTime.now();
        this.timeOfLastModification = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(LocalDateTime timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public LocalDateTime getTimeOfLastModification() {
        return timeOfLastModification;
    }

    public void setTimeOfLastModification(LocalDateTime timeOfLastModification) {
        this.timeOfLastModification = timeOfLastModification;
    }
}
