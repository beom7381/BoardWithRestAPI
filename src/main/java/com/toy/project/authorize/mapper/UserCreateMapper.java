package com.toy.project.authorize.mapper;

import com.toy.project.global.interfaces.DtoToEntityMapperInterface;
import com.toy.project.authorize.dto.SignUpRequest;
import com.toy.project.authorize.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserCreateMapper implements DtoToEntityMapperInterface<User, SignUpRequest> {
    @Override
    public User toEntity(SignUpRequest dto) {
        return new User(
                null,
                dto.getUserId(),
                dto.getUserPw(),
                dto.getUserName()
        );
    }
}
