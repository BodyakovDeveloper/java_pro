package com.koval.jdbc.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestUtil {
    private static final String rootPath = Thread.currentThread().getContextClassLoader()
            .getResource("database.properties").getPath();
    private static final Logger logger = LogManager.getLogger(JdbcUserDao.class);

    static BasicDataSource dataSource;

    public static BasicDataSource getDataSource() {
        if (dataSource == null) {
            setUpDataBaseSource();
        }
        return dataSource;
    }

    private static void setUpDataBaseSource() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(rootPath));

            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
            dataSource.setMinIdle(Integer.parseInt(properties.getProperty("minIdle")));
            dataSource.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}