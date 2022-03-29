package com.koval.jdbc.dao;

import com.koval.jdbc.entity.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.Properties;

public class JdbcUserDaoTest extends DataSourceBasedDBTestCase {
    private static final String DRIVER = "org.h2.Driver";
    private static final String CONNECTION_URL = "jdbc:h2:mem:mydb";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    private final JdbcUserDao userDao = new JdbcUserDao();

    private Connection dbConnection;
    TestUtil dbManager = new TestUtil();
    private IDatabaseTester databaseTester = new JdbcDatabaseTester(DRIVER, CONNECTION_URL, USERNAME, PASSWORD);

    public JdbcUserDaoTest() throws Exception {
    }

    @Override
    protected DataSource getDataSource() {
        BasicDataSource ds = dbManager.getDataSource();

        ds.setUrl(CONNECTION_URL);
        ds.setUsername(USERNAME);
        ds.setPassword(PASSWORD);
        return ds;
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        dbConnection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        createTables();
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder()
                .build(getClass().getClassLoader().getResourceAsStream("dbunit/actual_dataset_user.xml"));
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }

    private void createTables() throws SQLException, IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties");
        properties.load(fileInputStream);
        Connection conn = getDataSource().getConnection();
        Statement st = conn.createStatement();

        st.executeUpdate(properties.getProperty("SQL.CREATE_TABLE_USER"));
        conn.close();
    }

    @Before
    public void init() throws Exception {
        dbConnection = dbManager.getDataSource().getConnection();
        databaseTester.setDataSet(getDataSet());
        databaseTester.onSetup();
    }

    @Test
    public void testCreate() throws Exception {
        User testCreate = new User(4L, "User1", "123451", "email3@gmail.com1", "FirstName31",
                "LastName31",Date.valueOf("2015-01-01"), 3L);
        userDao.create(testCreate);

        IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("tuser");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream("dbunit/create_user_dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable("tuser");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testUpdate() throws Exception {
        User testCreate = new User(3L, "User1", "123451", "email3@gmail.com1", "FirstName31",
                "LastName31", new Date(new GregorianCalendar(2015, 0, 1).getTimeInMillis()), 2L);
        userDao.update(testCreate);
        IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("tuser");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream("dbunit/update_user_dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable("tuser");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testDelete() throws Exception {
        User testCreate = new User(3L, "User", "12345", "email3@gmail.com", "FirstName3",
                "LastName3", new Date(new GregorianCalendar(2015, 0, 1).getTimeInMillis()), 2L);
        userDao.remove(testCreate);
        IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("tuser");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("dbunit/delete_user_dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable("tuser");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testFindById() throws Exception {
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("tuser");
        Object expectedRole = expectedTable.getValue(0, "LOGIN");
        Assert.assertEquals(expectedRole, userDao.findById(1L).getLogin());
    }

    @Test
    public void testFindAll() throws Exception {
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("tuser");
        Assert.assertEquals(expectedTable.getRowCount(), userDao.findAll().size());
    }

    @Test
    public void testFindByLogin() throws Exception {
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("tuser");
        Object expectedRole = expectedTable.getValue(2, "ID");
        Assert.assertEquals(expectedRole, userDao.findByLogin("User").getId().toString());
    }

    @Test
    public void testFindByEmail() throws Exception {
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("tuser");
        Object expectedRole = expectedTable.getValue(0, "ID");
        Assert.assertEquals(expectedRole, userDao.findByEmail("email1@gmail.com").getId().toString());
    }
}
