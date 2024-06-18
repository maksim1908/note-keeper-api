package com.example.note_keeper_api.Entitie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDateTime timeOfCreation;
    private LocalDateTime timeOfLastModification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = true)
    @JsonIgnore
    private User user;



    public Note(){

    }
    public Note(String title, String content) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setTimeOfLastModification(LocalDateTime timeOfLastModification) {
        this.timeOfLastModification = timeOfLastModification;
    }


}
