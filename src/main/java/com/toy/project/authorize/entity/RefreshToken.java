package com.toy.project.authorize.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RefreshToken {
    @Id
    private String userId;

    @Column(nullable = false)
    private String refreshToken;
}
