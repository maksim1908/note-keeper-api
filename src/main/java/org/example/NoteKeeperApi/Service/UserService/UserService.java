package org.example.NoteKeeperApi.Service.UserService;

import org.example.NoteKeeperApi.Dto.User.ChangePasswordRequestDto;
import org.example.NoteKeeperApi.Dto.User.UpdateUserRequestDto;
import org.example.NoteKeeperApi.Dto.User.UserResponseDto;

public interface UserService {
    UserResponseDto getUserById(Long id);

    void changeUserPassword(ChangePasswordRequestDto changePasswordRequestDto);

    UserResponseDto updateUserInfo(UpdateUserRequestDto userResponseDto);
}
