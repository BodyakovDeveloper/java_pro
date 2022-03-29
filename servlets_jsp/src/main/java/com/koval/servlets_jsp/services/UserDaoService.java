package com.koval.servlets_jsp.services;

import com.koval.servlets_jsp.dao.UserDao;
import com.koval.servlets_jsp.entities.User;

public class UserDaoService {
    private final UserDao userDao;

    public UserDaoService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean isUserExists(String login, String password) {
        return userDao.isUserExists(login, password);
    }

    public void create(User user) {
        userDao.create(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void remove(User user) {
        userDao.remove(user);
    }

    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }
}