package com.toy.project.authorize.repository;

import com.toy.project.authorize.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    boolean existsByUserId(String userId);
    boolean existsByNickName(String nickName);
    Optional<User> findByUserIdAndUserPw(String userId, String userPw);
    Optional<User> findByUserId(String userId);
}