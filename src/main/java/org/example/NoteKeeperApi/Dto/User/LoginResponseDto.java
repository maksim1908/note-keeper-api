package org.example.NoteKeeperApi.Dto.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String username;
    private String jwtToken;
    private String refreshToken;
}
