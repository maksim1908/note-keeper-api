package com.example.note_keeper_api.Entities;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("singleton")
public class NoteEntity {
    private int id;
    private String title;
    private String content;

    public NoteEntity(){
    }

    public NoteEntity(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @PostConstruct
    void init(){
        System.out.println("created bean");
    }
    @PreDestroy
    void destroy(){
        System.out.println("bean destriyed");
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
