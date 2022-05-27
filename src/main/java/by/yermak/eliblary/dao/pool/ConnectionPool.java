package by.yermak.eliblary.dao.pool;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Properties properties = new Properties();

    private static final String DB_PROPERTIES_PATH = "config/db-mysql-elibrary.properties";
    private static final String DB_PROPERTIES_PREFIX = "db.";
    private static final String DB_URL_PROPERTY = "url";
    private static final String DB_USER_PROPERTY = "user";
    private static final String DB_PASSWORD_PROPERTY = "password";
    private static final String DB_DRIVER_PROPERTY = "driver";
    private static final String DEFAULT_DRIVER_PROPERTY = "com.mysql.cj.jdbc.Driver";
    private static final String POOL_PROPERTIES_PREFIX = "pool.";
    private static final String POOL_SIZE_PROPERTY = "size";
    private static final String DB_URL;
    private static final int DEFAULT_POOL_SIZE = 6;
    private static final int POOL_SIZE;

    private static final Lock locker = new ReentrantLock(true);
    private static final AtomicBoolean isPoolCreated = new AtomicBoolean(false);
    private static ConnectionPool instance;

    private final BlockingQueue<ProxyConnection> freeConnection = new LinkedBlockingQueue<>(POOL_SIZE);
    private final BlockingQueue<ProxyConnection> usedConnection = new LinkedBlockingQueue<>(POOL_SIZE);


    static {

        String driverProperty;

        try (var inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_PATH)) {

            var fileProperties = new Properties();
            fileProperties.load(inputStream);

            DB_URL = fileProperties.getProperty(DB_PROPERTIES_PREFIX + DB_URL_PROPERTY);
            properties.put(DB_USER_PROPERTY, fileProperties.getProperty(DB_PROPERTIES_PREFIX + DB_USER_PROPERTY));
            properties.put(DB_PASSWORD_PROPERTY, fileProperties.getProperty(DB_PROPERTIES_PREFIX + DB_PASSWORD_PROPERTY));

            if ((driverProperty = fileProperties.getProperty(DB_PROPERTIES_PREFIX + DB_DRIVER_PROPERTY)) == null) {
                driverProperty = DEFAULT_DRIVER_PROPERTY;
            }
            Class.forName(driverProperty);

            String poolSizeParameter;
            var poolSize = DEFAULT_POOL_SIZE;
            if ((poolSizeParameter = fileProperties.getProperty(POOL_PROPERTIES_PREFIX + POOL_SIZE_PROPERTY)) != null) {
                try {
                    poolSize = Integer.parseInt(poolSizeParameter);
                } catch (NumberFormatException nfe) {
                    LOGGER.error("Invalid pool size parameter in properties file: " + poolSizeParameter);
                }
            }
            POOL_SIZE = poolSize;
        } catch (IOException e) {
            LOGGER.error("failed to read database properties", e);
            throw new ExceptionInInitializerError("failed to read database properties");
        } catch (ClassNotFoundException e) {
            LOGGER.error("driver was not found");
            throw new ExceptionInInitializerError("driver was not found");
        }
    }

    private ConnectionPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            ProxyConnection connection;
            try {
                connection = new ProxyConnection(DriverManager.getConnection(DB_URL, properties));
                freeConnection.put(connection);
            } catch (SQLException | InterruptedException e) {
                LOGGER.error("the connection pool is empty, no connections created");
                throw new ExceptionInInitializerError("Error while initialising connection pool");
            }
        }
    }

    public static ConnectionPool getInstance() {
        if (!isPoolCreated.get()) {
            try {
                locker.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    isPoolCreated.set(true);
                }
            } finally {
                locker.unlock();
            }
        }
        return instance;
    }

    public boolean releaseConnection(Connection connection) {
        boolean result = false;
        if (connection instanceof ProxyConnection) {
            try {
                usedConnection.take();
                freeConnection.put((ProxyConnection) connection);
                result = true;
            } catch (InterruptedException e) {
                LOGGER.error("failed to release a connection", e);
                Thread.currentThread().interrupt();
            }
        }
        return result;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnection.take();
            usedConnection.put(connection);
        } catch (InterruptedException e) {
            LOGGER.error("exception in get connection from pool:", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void destroy() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnection.take().reallyClose();
            } catch (SQLException e) {
                LOGGER.error("failed to destroy pool", e);
            } catch (InterruptedException e) {
                LOGGER.error("failed to destroy pool", e);
                Thread.currentThread().interrupt();
            }
        }
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                DriverManager.deregisterDriver(drivers.nextElement());
            }
        } catch (SQLException exception) {
            LOGGER.error("Drivers were not de-registered");
        }
    }
}
