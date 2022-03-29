package com.koval.hibernate.dao;

import com.koval.hibernate.entities.RoleEntity;

import java.util.List;

public interface RoleDao extends Dao<RoleEntity> {

    @Override
    void create(RoleEntity entity);

    @Override
    void update(RoleEntity entity);

    @Override
    void remove(RoleEntity entity);

    @Override
    List<RoleEntity> findAll();

    RoleEntity getRoleByName(String name);
}