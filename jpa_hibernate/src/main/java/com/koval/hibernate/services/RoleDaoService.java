package com.koval.hibernate.services;

import com.koval.hibernate.dao.RoleDao;
import com.koval.hibernate.entities.RoleEntity;

import java.util.List;

public class RoleDaoService {

    private final RoleDao roleDao;

    public RoleDaoService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void create(RoleEntity roleEntity) {
        roleDao.create(roleEntity);
    }

    public void update(RoleEntity roleEntity) {
        roleDao.update(roleEntity);
    }

    public void remove(RoleEntity roleEntity) {
        roleDao.remove(roleEntity);
    }

    public List<RoleEntity> findAll() {
        return roleDao.findAll();
    }

    public RoleEntity getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }
}