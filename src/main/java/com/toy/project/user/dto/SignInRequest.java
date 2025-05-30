package com.toy.project.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    private String userId;
    private String userPw;
}
