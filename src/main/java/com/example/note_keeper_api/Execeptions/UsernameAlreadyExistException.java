package com.example.note_keeper_api.Execeptions;

public class UsernameAlreadyExistException  extends Exception{

    public UsernameAlreadyExistException(String message){
        super(message);
    }
}
