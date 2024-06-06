package com.example.note_keeper_api.DTO;

public class UserDTO {
    private String username;

    public UserDTO(){

    }

    public UserDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
