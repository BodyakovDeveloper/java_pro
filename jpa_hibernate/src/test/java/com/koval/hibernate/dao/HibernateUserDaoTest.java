package com.koval.hibernate.dao;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.koval.hibernate.entities.RoleEntity;
import com.koval.hibernate.entities.UserEntity;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

@DataSet(value = "user_datasets/actual_dataset_user.yml")
public class HibernateUserDaoTest {

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance();

    private final UserDao hibernateUserDao = new HibernateUserDao();
    private final RoleDao hibernateRoleDao = new HibernateRoleDao();

    @Test
    @DataSet(value = "user_datasets/actual_dataset_user.yml", executeScriptsBefore = "prepare_to_user_dao_test.sql")
    @ExpectedDataSet("user_datasets/create_user_dataset.yml")
    public void testCreate() {

        RoleEntity roleEntity = hibernateRoleDao.getRoleByName("ADMIN");

        UserEntity userEntity = new UserEntity("User1", "123451", "email3@gmail.com", "FirstFirstName",
                "LastName", Date.valueOf("2015-01-01"), roleEntity);

        hibernateUserDao.create(userEntity);
    }

    @Test
    @DataSet(value = "user_datasets/actual_dataset_user.yml", executeScriptsBefore = "prepare_to_user_dao_test.sql")
    @ExpectedDataSet("user_datasets/update_user_dataset.yml")
    public void testUpdate() {
        UserEntity expectedUser = new UserEntity("User", "123451", "email3@gmail.com", "FirstName", "LastName", Date.valueOf("2015-01-01"), new RoleEntity(2, "USER"));
        expectedUser.setId(3);
        hibernateUserDao.update(expectedUser);
    }

    @Test
    @DataSet(value = "user_datasets/actual_dataset_user.yml", executeScriptsBefore = "prepare_to_user_dao_test.sql")
    @ExpectedDataSet("user_datasets/delete_user_dataset.yml")
    public void testDelete() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(3);
        hibernateUserDao.remove(userEntity);
    }

    @Test
    @DataSet(value = "user_datasets/actual_dataset_user.yml", executeScriptsBefore = "prepare_to_user_dao_test.sql")
    public void testFindAll() {
        List<UserEntity> roleList = hibernateUserDao.findAll();
        Assert.assertEquals(2, roleList.size());
    }

    @Test
    @DataSet(value = "user_datasets/actual_dataset_user.yml", executeScriptsBefore = "prepare_to_user_dao_test.sql")
    public void testGetUserByLogin() {
        UserEntity expectedUser = new UserEntity("User", "12345", "email3@gmail.com", "FirstName3", "LastName3", Date.valueOf("2015-01-01"), new RoleEntity(2, "USER"));
        expectedUser.setId(3);
        Assert.assertEquals(expectedUser, hibernateUserDao.findByLogin("User"));
    }

    @Test
    @DataSet(value = "user_datasets/actual_dataset_user.yml", executeScriptsBefore = "prepare_to_user_dao_test.sql")
    public void testIsUserExists() {

        Assert.assertTrue(hibernateUserDao.isUserExists("User", "12345"));
        Assert.assertFalse(hibernateUserDao.isUserExists("NotUser", "NotPassword"));
    }
}