package com.toy.project.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String userId;
    private String userPw;
    private String userName;
}
