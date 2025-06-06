package com.toy.project.authorize.service;

import com.toy.project.authorize.constant.CONSTATNS;
import com.toy.project.authorize.dto.JwtResponse;
import com.toy.project.authorize.dto.SignInRequest;
import com.toy.project.authorize.dto.SignUpRequest;
import com.toy.project.authorize.dto.UserDataResponse;
import com.toy.project.authorize.entity.RefreshToken;
import com.toy.project.authorize.entity.User;
import com.toy.project.authorize.repository.RefreshTokenRepository;
import com.toy.project.authorize.repository.UserRepository;
import com.toy.project.authorize.util.JwtProvider;
import com.toy.project.authorize.util.UserMapperContainer;
import com.toy.project.global.exception.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserMapperContainer userMapperContainer;
    private final JwtProvider jwtProvider;

    public UserService(UserRepository userRepository,
                       RefreshTokenRepository refreshTokenRepository,
                       UserMapperContainer userMapperContainer,
                       JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userMapperContainer = userMapperContainer;
        this.jwtProvider = jwtProvider;
    }

    public List<UserDataResponse> getUserList() {
        var userDataResponseMapper = userMapperContainer.getUserDataResponseMapper();

        return userRepository.findAll()
                .stream()
                .map(userDataResponseMapper::toDto)
                .toList();
    }

    public User getUserFromUserId(String userId) {
        return userRepository.findByUserId(userId).orElse(null);
    }

    public UserDataResponse signUp(SignUpRequest signUpRequest) {
        var userCreateMapper = userMapperContainer.getUserCreateMapper();
        var userDataResponseMapper = userMapperContainer.getUserDataResponseMapper();
        var newUser = userCreateMapper.toEntity(signUpRequest);

        if (userRepository.existsByUserId(newUser.getUserId())) {
            throw new DuplicateDataException();
        }

        if (userRepository.existsByNickName(newUser.getNickName())) {
            throw new DuplicateDataException();
        }

        return userDataResponseMapper.toDto(userRepository.save(newUser));
    }

    public JwtResponse signIn(SignInRequest signInRequest, HttpServletResponse response) {
        var user = userRepository.findByUserIdAndUserPw(signInRequest.getUserId(), signInRequest.getUserPw())
                .orElseThrow(SignInFailedException::new);

        String refreshToken = null;
        String accessToken = jwtProvider.generateAccessToken(user);

        if(signInRequest.isCreateRefreshToken()){
            refreshToken = jwtProvider.generateRefreshToken(user);
            response.addCookie(createRefreshTokenCookie(refreshToken));

            refreshTokenRepository.save(new RefreshToken(user.getUserId(), refreshToken));
        }

        return new JwtResponse(refreshToken, accessToken);
    }

    public void signOut(String userId) {
        var user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
        var token = refreshTokenRepository.findById(user.getUserId())
                .orElseThrow(RequestEntityNotFoundException::new);

        refreshTokenRepository.delete(token);
    }

    public JwtResponse refreshingAccessToken(String userId, HttpServletRequest request) {
        var user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        var userRefreshToken = refreshTokenRepository.findById(userId)
                .orElseThrow(RefreshTokenExpiredException::new);

        var requestToken = getRefreshTokenFromCookie(request).orElse(null);

        if (!userRefreshToken.getRefreshToken().equals(requestToken)) {
            throw new RefreshTokenExpiredException();
        }

        String newAccessToken = jwtProvider.generateAccessToken(user);

        return new JwtResponse(null, newAccessToken);
    }

    private Cookie createRefreshTokenCookie(String refreshToken) {
        Cookie cookie = new Cookie("Refresh-Token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(CONSTATNS.REFRESHTOKEN_EXPIRY_DAY.intValue());

        return cookie;
    }

    private Optional<String> getRefreshTokenFromCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("Refresh-Token"))
                .map(Cookie::getValue)
                .findFirst();
    }
}
