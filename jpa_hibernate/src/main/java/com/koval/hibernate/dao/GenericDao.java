package com.koval.hibernate.dao;

import com.koval.hibernate.util.HibernateSessionFactoryUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

public class GenericDao {

    static final Logger logger = LogManager.getLogger(HibernateUserDao.class);

    public void genericOperation(Consumer<Session> consumer) {

        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            consumer.accept(session);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public <T> T genericRead(Function<Session, T> function) {

        Transaction transaction = null;
        T dataFromDatabase;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            dataFromDatabase = function.apply(session);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return dataFromDatabase;
    }
}