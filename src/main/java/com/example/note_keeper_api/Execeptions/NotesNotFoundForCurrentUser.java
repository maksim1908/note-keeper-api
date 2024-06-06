package com.example.note_keeper_api.Execeptions;

public class NotesNotFoundForCurrentUser extends Exception{
    public NotesNotFoundForCurrentUser(String message){
        super(message);
    }
}
