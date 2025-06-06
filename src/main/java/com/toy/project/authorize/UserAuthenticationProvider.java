package com.toy.project.authorize;

import com.toy.project.authorize.service.UserService;
import com.toy.project.global.exception.SignInFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        var user = userService.getUserFromUserId(userName);

        if(!passwordEncoder.matches(password, user.getUserPw())){
            throw new SignInFailedException();
        }

        return new UsernamePasswordAuthenticationToken(user, password, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
