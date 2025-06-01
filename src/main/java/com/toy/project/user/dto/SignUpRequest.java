package com.toy.project.user.dto;

import com.toy.project.base.DtoInterface;
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
