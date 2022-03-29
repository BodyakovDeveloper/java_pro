package com.koval.servlets_jsp.dao;

import com.koval.servlets_jsp.entities.User;

import java.util.List;

public interface UserDao extends Dao<User> {
    List<User> findAll();
    User findByLogin(String login);
    boolean isUserExists(String email, String password);
}