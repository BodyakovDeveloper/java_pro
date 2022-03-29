package com.koval.servlets_jsp.services;

import com.koval.servlets_jsp.dao.RoleDao;
import com.koval.servlets_jsp.entities.Role;

import java.util.List;

public class RoleDaoService {

    private final RoleDao roleDao;

    public RoleDaoService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<Role> findAll() {
        return roleDao.findAll();
    }
}