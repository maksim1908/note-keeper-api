package com.example.note_keeper_api.DTO;


import com.example.note_keeper_api.Entities.NoteEntity;

public class NoteDTO {
    private String title;
    private String content;

    public static NoteEntity toNoteEntity(NoteDTO noteDTO){
        return new NoteEntity(noteDTO.getTitle(), noteDTO.getContent());
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
