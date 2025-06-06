package com.toy.project.authorize.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class JwtResponse {
    private String refreshToken;
    private String accessToken;
}
