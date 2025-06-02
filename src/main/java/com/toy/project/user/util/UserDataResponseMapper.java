package com.toy.project.user.util;

import com.toy.project.base.EntityToDtoMapperInterface;
import com.toy.project.user.dto.UserDataResponse;
import com.toy.project.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDataResponseMapper implements EntityToDtoMapperInterface<User, UserDataResponse> {
    @Override
    public UserDataResponse toDto(User entity) {
        return new UserDataResponse(
                entity.getId(),
                entity.getUserId(),
                entity.getUserName()
        );
    }
}
