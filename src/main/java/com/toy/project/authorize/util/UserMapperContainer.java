package com.toy.project.authorize.util;

import com.toy.project.interfaces.DtoToEntityMapperInterface;
import com.toy.project.interfaces.EntityToDtoMapperInterface;
import com.toy.project.authorize.dto.SignUpRequest;
import com.toy.project.authorize.dto.UserDataResponse;
import com.toy.project.authorize.entity.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Getter
@Component
public class UserMapperContainer {
    private final DtoToEntityMapperInterface<User, SignUpRequest> userCreateMapper;
    private final EntityToDtoMapperInterface<User, UserDataResponse> userDataResponseMapper;

    public UserMapperContainer(
            @Qualifier("userCreateMapper") DtoToEntityMapperInterface<User, SignUpRequest> userCreateMapper,
            @Qualifier("userDataResponseMapper") EntityToDtoMapperInterface<User, UserDataResponse> userDataResponseMapper) {
        this.userCreateMapper = userCreateMapper;
        this.userDataResponseMapper = userDataResponseMapper;
    }
}
