package com.example.note_keeper_api.DTO;

import com.example.note_keeper_api.Entities.User;

public class UserDTO {
    private Long id;
    private String username;

    public UserDTO(){

    }

    public UserDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(),user.getUsername());
    }
}
