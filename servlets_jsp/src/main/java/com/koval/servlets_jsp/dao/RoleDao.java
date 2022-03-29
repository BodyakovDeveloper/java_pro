package com.koval.servlets_jsp.dao;

import com.koval.servlets_jsp.entities.Role;

import java.util.List;

public interface RoleDao extends Dao<Role>{

    @Override
    void create(Role entity);

    @Override
    void update(Role entity);

    @Override
    void remove(Role entity);

    @Override
    List<Role> findAll();
}
