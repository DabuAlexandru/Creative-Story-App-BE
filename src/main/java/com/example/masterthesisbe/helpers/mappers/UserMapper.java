package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.auth.UserDto;
import com.example.masterthesisbe.model.User;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class UserMapper {
    public UserDto convertToResponseDto(User user) {
        if(isNull(user)) {
            return null;
        }

        return new UserDto(
            user.getId(),
            user.getPenName(),
            user.getEmail(),
            user.getRole()
        );
    }
}
