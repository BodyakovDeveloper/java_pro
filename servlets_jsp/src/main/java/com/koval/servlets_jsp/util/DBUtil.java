package com.koval.servlets_jsp.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBUtil {
    static final Logger logger = LogManager.getLogger(DBUtil.class);

    private static BasicDataSource dataSource;

    static {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream("database.properties")) {

            Properties properties = new Properties();
            properties.load(inputStream);

            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
            dataSource.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));
            dataSource.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));

            logger.info("DataSource was successfully initialized: {}", dataSource);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}