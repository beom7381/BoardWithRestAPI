package com.toy.project.authorize.mapper;

import com.toy.project.global.interfaces.EntityToDtoMapperInterface;
import com.toy.project.authorize.dto.UserDataResponse;
import com.toy.project.authorize.entity.User;
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
