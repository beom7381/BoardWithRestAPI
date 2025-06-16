package com.toy.project.authorize.service;

import com.toy.project.authorize.constant.CONSTATNS;
import com.toy.project.authorize.dto.JwtResponse;
import com.toy.project.authorize.dto.SignInRequest;
import com.toy.project.authorize.dto.SignUpRequest;
import com.toy.project.authorize.dto.UserDataResponse;
import com.toy.project.authorize.entity.User;
import com.toy.project.authorize.repository.UserRepository;
import com.toy.project.authorize.util.JwtProvider;
import com.toy.project.authorize.util.UserMapperContainer;
import com.toy.project.global.exception.DuplicateDataException;
import com.toy.project.global.exception.RefreshTokenExpiredException;
import com.toy.project.global.exception.SignInFailedException;
import com.toy.project.global.exception.UserNotFoundException;
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
    private final RedisService redisService;
    private final UserRepository userRepository;
    private final UserMapperContainer userMapperContainer;
    private final JwtProvider jwtProvider;

    public UserService(
            RedisService redisService,
            UserRepository userRepository,
            UserMapperContainer userMapperContainer,
            JwtProvider jwtProvider) {
        this.redisService = redisService;
        this.userRepository = userRepository;
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

        if (signInRequest.isCreateRefreshToken()) {
            refreshToken = jwtProvider.generateRefreshToken(user);
            response.addCookie(createRefreshTokenCookie(refreshToken));

            redisService.saveRefreshToken(user.getUserId(), refreshToken, CONSTATNS.REFRESH_TOKEN_EXPIRY_DAY);
        }

        return new JwtResponse(refreshToken, accessToken);
    }

    public void signOut(String accessToken, HttpServletResponse response) {
        var token = jwtProvider.resolveAccessToken(accessToken);
        var tokenData = jwtProvider.parseToken(token);
        var userId = jwtProvider.getUserId(tokenData);

        var user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        if (redisService.hasRefreshToken(userId)) {
            redisService.deleteRefreshToken(userId);
            response.addCookie(createRefreshTokenCookie(null));
            //리프레시토큰 쿠키 제거
        }

        redisService.blockAccessToken(accessToken, CONSTATNS.ACCESS_TOKEN_EXPIRY_DAY);
    }

    public JwtResponse refreshingAccessToken(String userId, HttpServletRequest request) {
        var user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        var userRefreshToken = redisService.getRefreshToken(userId);

        var requestRefreshToken = getRefreshTokenFromCookie(request).orElse(null);

        if (!userRefreshToken.equals(requestRefreshToken)) {
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
        cookie.setMaxAge(CONSTATNS.REFRESH_TOKEN_EXPIRY_DAY.intValue());

        return cookie;
    }

    private Optional<String> getRefreshTokenFromCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("Refresh-Token"))
                .map(Cookie::getValue)
                .findFirst();
    }
}
