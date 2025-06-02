package com.toy.project.authorize.entity;

import com.toy.project.interfaces.EntityInterface;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "USERS")
public class User implements EntityInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "user_pw", nullable = false)
    private String userPw;

    @Column(name = "user_name", nullable = false)
    private String userName;
}
