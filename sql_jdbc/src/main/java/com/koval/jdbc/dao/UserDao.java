package com.koval.jdbc.dao;

import com.koval.jdbc.entity.User;

import java.util.List;

public interface UserDao extends Dao<User> {
    List<User> findAll();
    User findByLogin(String login);
    User findByEmail(String login);
}