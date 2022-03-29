package com.koval.spring.dao;

import com.koval.spring.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao extends Dao<UserEntity> {

    List<UserEntity> findAllUsers();

    UserEntity findByLogin(String login);

    UserEntity findByEmail(String email);

    boolean isUserExists(String login, String email);
}