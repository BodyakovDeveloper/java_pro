package com.koval.spring.service;

import com.koval.spring.dao.RoleDao;
import com.koval.spring.entity.RoleEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleDaoService {

    private final RoleDao roleDao;

    public RoleDaoService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    public List<RoleEntity> findAllRoles() {
        return roleDao.findAllRoles();
    }

    @Transactional(readOnly = true)
    public RoleEntity getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }
}