package com.koval.jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBUtil {
    static final Logger logger = LogManager.getLogger(DBUtil.class);

    private static BasicDataSource dataSource;
    private static final String rootPath = Thread.currentThread().getContextClassLoader()
            .getResource("database.properties").getPath();

    static {
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

            logger.info("DataSource was successfully initialized: {}", dataSource);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}