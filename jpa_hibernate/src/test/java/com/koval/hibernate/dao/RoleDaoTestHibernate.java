package com.koval.hibernate.dao;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.koval.hibernate.entities.RoleEntity;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

public class RoleDaoTestHibernate {

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance();

    private final RoleDao hibernateRoleDao = new HibernateRoleDao();

    @Test
    @DataSet(value = "role_datasets/actual_dataset_role.yml", executeScriptsBefore = "prepare_to_role_dao_test.sql")
    @ExpectedDataSet("role_datasets/create_role_dataset.yml")
    public void testCreate() {
        hibernateRoleDao.create(new RoleEntity("Test"));
    }

    @Test
    @DataSet(value = {"role_datasets/actual_dataset_role.yml"}, executeScriptsBefore = "prepare_to_role_dao_test.sql")
    @ExpectedDataSet("role_datasets/update_role_dataset.yml")
    public void testUpdate() {
        hibernateRoleDao.update(new RoleEntity(3, "User123"));
    }

    @Test
    @DataSet(value = {"role_datasets/actual_dataset_role.yml"}, executeScriptsBefore = "prepare_to_role_dao_test.sql")
    @ExpectedDataSet("role_datasets/delete_role_dataset.yml")
    public void testDelete() {
        hibernateRoleDao.remove(new RoleEntity(3, "User"));
    }

    @Test
    @DataSet(value = "role_datasets/actual_dataset_role.yml", executeScriptsBefore = "prepare_to_role_dao_test.sql")
    public void testFindAll() {
        List<RoleEntity> roles = hibernateRoleDao.findAll();
        System.out.println(roles);
        Assert.assertEquals(2, roles.size());
    }

    @Test
    @DataSet(value = "role_datasets/actual_dataset_role.yml", executeScriptsBefore = "prepare_to_role_dao_test.sql")
    public void testGetRoleByName() {
        System.out.println(hibernateRoleDao.getRoleByName("User").getName());
        Assert.assertEquals("User", hibernateRoleDao.getRoleByName("User").getName());
    }
}