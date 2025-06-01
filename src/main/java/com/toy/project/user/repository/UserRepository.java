package com.toy.project.user.repository;

import com.toy.project.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    boolean existsByUserId(String userId);
    boolean existsByUserName(String userName);
    User findByUserIdAndUserPw(String userId, String userPw);
    User findByUserId(String userId);
}