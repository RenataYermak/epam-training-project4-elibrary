package com.epam.yermak.project.dao.config;

import com.epam.yermak.project.dao.exception.DataSourceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool extends DataSource {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ConnectionPool INSTANCE = new ConnectionPool();

    private final BlockingQueue<Connection> free;
    private final BlockingQueue<Connection> busy;

    private ConnectionPool() {
        LOGGER.log(Level.INFO, "create connection pool");
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTY_FILE_NAME);
        int numOfConnections = Integer.parseInt(resourceBundle.getString(DB_PROPERTY_CONNECTIONS_NUMB));
        free = new LinkedBlockingQueue<>(numOfConnections);
        busy = new LinkedBlockingQueue<>();
        try {
            for (int i = 0; i < numOfConnections; i++) {
                try {
                    free.put(openConnection());
                } catch (SQLException | DataSourceException e) {
                    LOGGER.log(Level.ERROR, "exception in open connection: ", e);
                }
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "interrupted exception: ", e);
        }
    }

    public static ConnectionPool getConnectionPool() {
        return INSTANCE;
    }

    public Connection getConnection() {
        LOGGER.log(Level.INFO, "get connection from pool");
        Connection connection = null;
        try {
            connection = free.take();
            busy.add(connection);
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "exception in get connection from pool: ", e);
        }
        return connection;
    }

    public void returnConnection(Connection connection) {
        LOGGER.log(Level.INFO, "return connection to pool");
        try {
            if (busy.remove(connection)) {
                free.put(connection);
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "exception in return connection to pool: ", e);
        }
    }
}

