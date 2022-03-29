package com.koval.spring.dao;

import com.koval.spring.entity.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleDao extends Dao<RoleEntity> {

    List<RoleEntity> findAllRoles();

    RoleEntity getRoleByName(String name);
}