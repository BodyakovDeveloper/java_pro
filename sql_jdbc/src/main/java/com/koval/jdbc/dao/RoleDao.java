package com.koval.jdbc.dao;

import com.koval.jdbc.entity.Role;

import java.util.List;

public interface RoleDao extends Dao<Role>{

    Role findByName(String name);

    @Override
    void create(Role entity);

    @Override
    void update(Role entity);

    @Override
    void remove(Role entity);

    @Override
    List<Role> findAll();

    @Override
    Role findById(Long id);
}
