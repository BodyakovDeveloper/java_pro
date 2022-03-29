package com.koval.hibernate.dao;

import com.koval.hibernate.entities.UserEntity;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateUserDao implements UserDao {

    private final GenericDao genericDao;

    public HibernateUserDao() {
        this.genericDao = new GenericDao();
    }

    @Override
    public void create(UserEntity userEntity) {
        genericDao.genericOperation(session -> session.save(userEntity));
    }

    @Override
    public void update(UserEntity userEntity) {
        genericDao.genericOperation(session -> session.update(userEntity));
    }

    @Override
    public void remove(UserEntity userEntity) {
        genericDao.genericOperation(session -> session.delete(userEntity));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserEntity> findAll() {
        return genericDao.genericRead(session -> session.createQuery("From UserEntity").list());
    }

    @Override
    public UserEntity findByLogin(String login) {
        return genericDao.genericRead(session -> {
            Query query = session.createQuery("from UserEntity where login=:login");
            query.setParameter("login", login);
            return (UserEntity) query.uniqueResult();
        });
    }

    @Override
    public UserEntity findByEmail(String email) {
        return genericDao.genericRead(session -> {
            Query query = session.createQuery("from UserEntity where email=:email");
            query.setParameter("email", email);
            return (UserEntity) query.uniqueResult();
        });
    }

    @Override
    public boolean isUserExists(String login, String password) {

        return genericDao.genericRead(session -> {
            Query query = session.createQuery("from UserEntity where login = :login and password = :password");
            query.setParameter("login", login);
            query.setParameter("password", password);

            return query.uniqueResult() != null;
        });
    }
}