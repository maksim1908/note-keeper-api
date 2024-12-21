package org.example.NoteKeeperApi.Mapper;

import org.example.NoteKeeperApi.Dto.User.UserResponseDto;
import org.example.NoteKeeperApi.Entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    private ModelMapper modelMapper;

    public UserResponseDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return modelMapper.map(user, UserResponseDto.class);
    }
}
