package com.toy.project.user.repository;

import com.toy.project.user.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByUserId(String userId);
    boolean existsByUserName(String userName);
    User findByUserIdAndUserPw(String userId, String userPw);
}