package com.toy.project.user.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class UserMapperContainer {
    private final UserCreateMapper userCreateMapper;
    private final UserDataResponseMapper userDataResponseMapper;

    public UserMapperContainer(){
        this.userCreateMapper = new UserCreateMapper();
        this.userDataResponseMapper = new UserDataResponseMapper();
    }
}
