package com.toy.project.authorize.dto;

import com.toy.project.interfaces.DtoInterface;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest implements DtoInterface {
    private String userId;
    private String userPw;
    private String userName;
}
