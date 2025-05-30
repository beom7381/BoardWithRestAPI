package com.toy.project.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {
    private String userId;
    private String userName;
}
