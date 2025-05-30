package com.toy.project.user.service;

import com.toy.project.user.dto.SignInRequest;
import com.toy.project.user.dto.SignUpRequest;
import com.toy.project.user.dto.SignUpResponse;
import com.toy.project.user.exception.DuplicateIdException;
import com.toy.project.user.exception.DuplicateNameException;
import com.toy.project.user.repository.UserRepository;
import com.toy.project.user.util.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        var newUser = userMapper.toEntity(signUpRequest, SignUpRequest.class);

        if(userRepository.existsByUserId(newUser.getUserId())){
            throw new DuplicateIdException("중복된 아이디");
        }

        if(userRepository.existsByUserName(newUser.getUserName())){
            throw new DuplicateNameException("중복된 이름");
        }

        return userMapper.toDto(userRepository.save(newUser), SignUpResponse.class);
    }

    public SignUpResponse signIn(SignInRequest signInRequest) {
        var request = userMapper.toEntity(signInRequest, SignInRequest.class);

        return new SignUpResponse("dummy_user_id", "dummy_user_name");
    }
}
