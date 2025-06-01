package com.toy.project.user.service;

import com.toy.project.user.dto.SignInRequest;
import com.toy.project.user.dto.SignUpRequest;
import com.toy.project.user.dto.UserDataResponse;
import com.toy.project.user.exception.DuplicateIdException;
import com.toy.project.user.exception.DuplicateNameException;
import com.toy.project.user.repository.UserRepository;
import com.toy.project.user.util.UserMapperContainer;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapperContainer userMapperContainer;

    public UserService(UserRepository userRepository,
                       UserMapperContainer userMapperContainer) {
        this.userRepository = userRepository;
        this.userMapperContainer = userMapperContainer;
    }

    public List<UserDataResponse> getUserList(){
        var userDataResponseMapper = userMapperContainer.getUserDataResponseMapper();

        return userRepository.findAll()
                             .stream()
                             .map(userDataResponseMapper::toDto)
                             .toList();
    }

    public UserDataResponse signUp(SignUpRequest signUpRequest) {
        var userCreateMapper = userMapperContainer.getUserCreateMapper();
        var userDataResponseMapper = userMapperContainer.getUserDataResponseMapper();
        var newUser = userCreateMapper.toEntity(signUpRequest);

        if(userRepository.existsByUserId(newUser.getUserId())){
            throw new DuplicateIdException("중복된 아이디");
        }

        if(userRepository.existsByUserName(newUser.getUserName())){
            throw new DuplicateNameException("중복된 이름");
        }

        return userDataResponseMapper.toDto(userRepository.save(newUser));
    }

    public UserDataResponse signIn(SignInRequest signInRequest) {
        var userDataResponseMapper = userMapperContainer.getUserDataResponseMapper();
        var user = userRepository.findByUserIdAndUserPw(signInRequest.getUserId(), signInRequest.getUserPw());

        return userDataResponseMapper.toDto(user);
    }

    public void signOut(HttpSession session){
        session.invalidate();
    }
}
