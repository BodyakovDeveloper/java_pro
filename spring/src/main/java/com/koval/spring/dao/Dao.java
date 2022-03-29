package com.koval.spring.dao;

public interface Dao <E> {

    void create(E entity);

    void update(E entity);

    void remove(E entity);
}
