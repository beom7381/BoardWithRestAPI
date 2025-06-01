package com.toy.project.user.dto;

import com.toy.project.base.DtoInterface;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDataResponse implements DtoInterface {
    private Long id;
    private String userId;
    private String userName;
}
