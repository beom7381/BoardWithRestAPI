package com.toy.project.authorize.dto;

import com.toy.project.global.interfaces.DtoInterface;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest implements DtoInterface {
    private String userId;
    private String userPw;
    private boolean createRefreshToken;
}
