package com.toy.project.user.util;

import com.toy.project.base.DtoToEntityMapperInterface;
import com.toy.project.user.dto.SignUpRequest;
import com.toy.project.user.entity.User;

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
