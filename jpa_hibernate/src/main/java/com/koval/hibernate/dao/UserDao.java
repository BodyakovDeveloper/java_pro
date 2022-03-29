package com.koval.hibernate.dao;

import com.koval.hibernate.entities.UserEntity;

import java.util.List;

public interface UserDao extends Dao<UserEntity> {

    List<UserEntity> findAll();

    UserEntity findByLogin(String login);

    UserEntity findByEmail(String email);

    boolean isUserExists(String login, String password);
}