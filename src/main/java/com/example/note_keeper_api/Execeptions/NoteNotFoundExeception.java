package com.example.note_keeper_api.Execeptions;

public class NoteNotFoundExeception extends Exception {
    public NoteNotFoundExeception(String message) {
        super(message);
    }
}
