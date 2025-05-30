package com.toy.project.user.util;

import com.toy.project.user.dto.SignInRequest;
import com.toy.project.user.dto.SignUpRequest;
import com.toy.project.user.dto.SignUpResponse;
import com.toy.project.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public <T> User toEntity(T dto, Class<T> dtoClass) {
        if (dto == null) {
            return null;
        }

        User result = null;

        if( dtoClass == SignUpRequest.class) {
            SignUpRequest signUpRequest = (SignUpRequest) dto;
            result = new User(
                    null,
                    signUpRequest.getUserId(),
                    signUpRequest.getUserPw(),
                    signUpRequest.getUserName());
        }
        else if (dtoClass == SignInRequest.class) {
            SignInRequest signInRequest = (SignInRequest) dto;
            result = new User(
                    null,
                    signInRequest.getUserId(),
                    signInRequest.getUserPw(),
                    null);
        }
        else{
            throw new IllegalArgumentException("Unsupported type");
        }

        return result;
    }

    public <T> T toDto(User user, Class<T> dtoClass) {
        if (user == null) {
            return null;
        }

        T result = null;

        if (dtoClass == SignUpResponse.class) {
            result = dtoClass.cast(
                    new SignUpResponse(
                            user.getUserId(),
                            user.getUserName()));
        }
        else{
            throw new IllegalArgumentException("Unsupported type");
        }

        return result;
    }
}
