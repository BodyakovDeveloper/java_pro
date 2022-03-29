package com.koval.jdbc.dao;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.koval.jdbc.entity.Role;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.github.database.rider.core.DBUnitRule.instance;

@RunWith(JUnit4.class)
public class JdbcRoleDaoTest {
    static final Logger logger = LogManager.getLogger(JdbcRoleDaoTest.class);
    private final RoleDao jdbcRoleDao = new JdbcRoleDao();

    @Rule
    public DBUnitRule dbUnitRule;

    {
        try {
            dbUnitRule = instance(TestUtil.getDataSource().getConnection());
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    @DataSet(value = "datasets/actual_dataset_role.yml", executeScriptsBefore = "create_role_table.sql", cleanAfter = true)
    @ExpectedDataSet("create_role_dataset.yml")
    public void testCreate() {
        jdbcRoleDao.create(new Role(4L, "<DDDD>"));
    }

    @Test
    @DataSet(value = "datasets/actual_dataset_role.yml", executeScriptsBefore = "create_role_table.sql", cleanAfter = true)
    @ExpectedDataSet("datasets/update_role_dataset.yml")
    public void testUpdate() {
        jdbcRoleDao.update(new Role(3L, "User123"));
    }

    @Test
    @DataSet(value = {"datasets/actual_dataset_role.yml"}, executeScriptsBefore = "create_role_table.sql", cleanAfter = true)
    @ExpectedDataSet("datasets/delete_role_dataset.yml")
    public void testDelete() {
        jdbcRoleDao.remove(new Role(3L, "123"));
    }

    @Test
    @DataSet(value = "datasets/actual_dataset_role.yml", executeScriptsBefore = "create_role_table.sql", cleanAfter = true)
    public void testFindAll() {
        List<Role> roleList = jdbcRoleDao.findAll();
        Assert.assertFalse(roleList.isEmpty());
        Assert.assertEquals(3, roleList.size());
    }

    @Test
    @DataSet(value = "datasets/actual_dataset_role.yml", executeScriptsBefore = "create_role_table.sql", cleanAfter = true)
    public void testFindById() {
        Role role = jdbcRoleDao.findById(1L);
        Assert.assertEquals("Admin1", role.getName());
    }

    @Test
    @DataSet(value = "datasets/actual_dataset_role.yml", executeScriptsBefore = "create_role_table.sql", cleanAfter = true)
    public void testFindByName() {
        Assert.assertEquals(Optional.of(1L), Optional.ofNullable(jdbcRoleDao.findByName("Admin1").getId()));
        Assert.assertEquals("Admin1", jdbcRoleDao.findByName("Admin1").getName());
    }
}