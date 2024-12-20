package org.example.NoteKeeperApi.Dto.User;

import lombok.Data;

@Data
public class UserResponseDto {
    private Integer id;
    private String name;
    private String username;
    private String email;
}
