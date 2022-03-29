package com.koval.hibernate.services;

import com.koval.hibernate.dao.UserDao;
import com.koval.hibernate.entities.UserEntity;

import java.util.List;

public class UserDaoService {
    private final UserDao userDao;

    public UserDaoService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean isUserExists(String login, String password) {
        return userDao.isUserExists(login, password);
    }

    public void create(UserEntity userEntity) {
        userDao.create(userEntity);
    }

    public void update(UserEntity userEntity) {
        userDao.update(userEntity);
    }

    public void remove(UserEntity userEntity) {
        userDao.remove(userEntity);
    }

    public UserEntity findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    public UserEntity findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @SuppressWarnings("unchecked")
    public List<UserEntity> findAll() {
        return userDao.findAll();
    }
}