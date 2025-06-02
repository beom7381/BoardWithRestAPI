package com.toy.project.authorize.dto;

import com.toy.project.interfaces.DtoInterface;
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
