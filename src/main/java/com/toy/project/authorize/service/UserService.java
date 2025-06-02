package com.toy.project.authorize.service;

import com.toy.project.authorize.dto.SignInRequest;
import com.toy.project.authorize.dto.SignUpRequest;
import com.toy.project.authorize.dto.UserDataResponse;
import com.toy.project.authorize.entity.User;
import com.toy.project.authorize.exception.DuplicateIdException;
import com.toy.project.authorize.exception.DuplicateNameException;
import com.toy.project.authorize.repository.UserRepository;
import com.toy.project.authorize.util.JwtUtil;
import com.toy.project.authorize.util.UserMapperContainer;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapperContainer userMapperContainer;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository,
                       UserMapperContainer userMapperContainer,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userMapperContainer = userMapperContainer;
        this.jwtUtil = jwtUtil;
    }

    public List<UserDataResponse> getUserList(){
        var userDataResponseMapper = userMapperContainer.getUserDataResponseMapper();

        return userRepository.findAll()
                             .stream()
                             .map(userDataResponseMapper::toDto)
                             .toList();
    }

    public User getUserFromUserId(String userId){
        return userRepository.findByUserId(userId).orElse(null);
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

    public String signIn(SignInRequest signInRequest) throws Exception {
        var user = userRepository.findByUserIdAndUserPw(signInRequest.getUserId(), signInRequest.getUserPw())
                .orElseThrow(() -> new Exception(""));

        return jwtUtil.generateToken(user);
    }

    public void signOut(HttpSession session){
        session.invalidate();
    }
}
