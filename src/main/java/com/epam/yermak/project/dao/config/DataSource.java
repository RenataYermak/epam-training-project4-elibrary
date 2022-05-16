package com.epam.yermak.project.dao.config;

import com.epam.yermak.project.dao.exception.DataSourceException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

abstract class DataSource {
    protected static final String DB_PROPERTY_FILE_NAME = "db-mysql-elibrary";
    protected static final String DB_PROPERTY_DRIVER_NAME = "db.driver";
    protected static final String DB_PROPERTY_URL = "db.url";
    protected static final String DB_PROPERTY_USER = "db.user";
    protected static final String DB_PROPERTY_PASSWORD = "db.password";
    protected static final String DB_PROPERTY_CONNECTIONS_NUMB = "db.connections_number";

    protected Connection openConnection() throws SQLException, DataSourceException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTY_FILE_NAME);
        String url = resourceBundle.getString(DB_PROPERTY_URL);
        String user = resourceBundle.getString(DB_PROPERTY_USER);
        String pass = resourceBundle.getString(DB_PROPERTY_PASSWORD);
        String driverName = resourceBundle.getString(DB_PROPERTY_DRIVER_NAME);

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            throw new DataSourceException();
        }
        return DriverManager.getConnection(url, user, pass);
    }
}

