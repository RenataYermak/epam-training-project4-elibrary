package com.epam.yermak.project.dao.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

class ConnectionFactory {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Properties properties = new Properties();
    private static final String DB_PROPERTIES_PATH = "config/db-mysql-elibrary.properties";
    private static final String DRIVER_PROPERTY = "db.driver";
    private static final String URL_PROPERTY = "db.url";
    private static final String USER_PROPERTY = "db.user";
    private static final String PASSWORD_PROPERTY = "db.password";
    private static final String DB_DRIVER;
    private static final String DB_USER;
    private static final String DB_PASSWORD;
    private static final String DB_URL;


    static {
        try (InputStream inputStream = ConnectionFactory.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_PATH)) {
            properties.load(inputStream);
            DB_URL = properties.getProperty(URL_PROPERTY);
            DB_USER = properties.getProperty(USER_PROPERTY);
            DB_PASSWORD = properties.getProperty(PASSWORD_PROPERTY);
            DB_DRIVER = properties.getProperty(DRIVER_PROPERTY);
            Class.forName(DB_DRIVER);
        } catch (IOException e) {
            LOGGER.error("failed to read database properties", e);
            throw new RuntimeException("failed to read database properties", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("driver was not found");
            throw new RuntimeException("driver was not found");
        }
    }

    private ConnectionFactory() {
    }

    //    static ProxyConnection createConnection() throws SQLException {
//        return new ProxyConnection(DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD));
//    }
    static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}