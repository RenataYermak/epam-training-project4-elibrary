//package com.epam.project.dao.pool;
//
//import com.epam.project.dao.config.exception.ConnectionPoolException;
//import org.apache.logging.log4j.Level;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.sql.Connection;
//import java.sql.Driver;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.Enumeration;
//import java.util.MissingResourceException;
//import java.util.Properties;
//import java.util.ResourceBundle;
//import java.util.concurrent.BlockingDeque;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingDeque;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.atomic.AtomicBoolean;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
//public class ConnectionPool {
//    private static final Logger LOGGER = LogManager.getLogger();
//     private static final int DEFAULT_POOL_SIZE = 4;
//    // private static final Properties properties = new Properties();
//    private static final String DB_PROPERTY = "db-mysql-elibrary";
//    private static final String DB_PROPERTY_DRIVER = "driver";
//    private static final String DB_PROPERTY_URL = "url";
//    private static final String DB_PROPERTY_USER = "user";
//    private static final String DB_PROPERTY_PASSWORD = "password";
//    private static final String DB_PROPERTY_POOL_SIZE = "pool_size";
//    private static final String DB_DRIVER;
//    private static final String DB_URL;
//    private static final String DB_USER;
//    private static final String DB_PASSWORD;
//    //private static final String DB_POOL_SIZE;
//
//    private static ConnectionPool instance;
//    private static final AtomicBoolean isPoolCreated = new AtomicBoolean(false);
//    private static final Lock locker = new ReentrantLock();
//    private final BlockingQueue<ProxyConnection> freeConnections;
//    private final BlockingQueue<ProxyConnection> busyConnections;
//
//    static {
//        try {
//            ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTY);
//            DB_URL = resourceBundle.getString(DB_PROPERTY_URL);
//            DB_USER = resourceBundle.getString(DB_PROPERTY_USER);
//            DB_PASSWORD = resourceBundle.getString(DB_PROPERTY_PASSWORD);
//            DB_DRIVER = resourceBundle.getString(DB_PROPERTY_DRIVER);
//            Class.forName(DB_DRIVER);
//        } catch (MissingResourceException e) {
//            LOGGER.fatal("Property file not found or has incorrect data " + DB_PROPERTY, e);
//            throw new RuntimeException("Property file not found or has incorrect data" + DB_PROPERTY, e);
//        } catch (ClassNotFoundException e) {
//            LOGGER.fatal("Driver have not been registered" + DB_PROPERTY, e);
//            throw new ExceptionInInitializerError();
//        }
//    }
//
//    private ConnectionPool() {
//       // ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTY);
//      //  int numOfConnections = Integer.parseInt(resourceBundle.getString(DB_PROPERTY_POOL_SIZE));
//        freeConnections = new LinkedBlockingQueue<>(DEFAULT_POOL_SIZE);
//        busyConnections = new LinkedBlockingQueue<>();
//        for (int i = 0; i <DEFAULT_POOL_SIZE; i++) {
//            try {
//                Connection connection = createConnection();
//                ProxyConnection proxyConnection = new ProxyConnection(connection);
//                freeConnections.add(proxyConnection);
//            } catch (SQLException e) {
//                LOGGER.error("Exception in open connection: ", e);
//            }
//        }
//        if (freeConnections.isEmpty()) {
//            LOGGER.fatal("Connection pool is empty.");
//            throw new RuntimeException("Connection pool is empty.");
//        }
//        LOGGER.info("Connection pool was created.");
//    }
//
//    public static ConnectionPool getInstance() {
//        if (!isPoolCreated.get()) {
//            locker.lock();
//            try {
//                if (isPoolCreated.compareAndSet(false, true)) {
//                    instance = new ConnectionPool();
//                }
//            } finally {
//                locker.unlock();
//            }
//        }
//        return instance;
//    }
//
//    public Connection getConnection() {
//        ProxyConnection proxyConnection = null;
//        try {
//            proxyConnection = freeConnections.take();
//            busyConnections.put(proxyConnection);
//        } catch (InterruptedException e) {
//            LOGGER.error("Try to get connection from pool was failed. ", e);
//            Thread.currentThread().interrupt();
//        }
//        return proxyConnection;
//    }
//
//    public void releaseConnection(Connection connection) throws ConnectionPoolException {
//        if (connection == null) {
//            LOGGER.error("connection is null");
//        }
//        if (!(connection instanceof ProxyConnection)) {
//            LOGGER.error("wrong instance");
//            throw new ConnectionPoolException("wrong instance");
//        }
//        try {
//            busyConnections.remove(connection);
//            freeConnections.put((ProxyConnection) connection);
//        } catch (InterruptedException e) {
//            LOGGER.error("failed to release connection", e);
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    public void destroyPool() {
//       // ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTY);
//       // int numOfConnections = Integer.parseInt(resourceBundle.getString(DB_PROPERTY_POOL_SIZE));
//        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
//            try {
//                freeConnections.take().reallyClose();
//            } catch (SQLException e) {
//                LOGGER.error("Try to destroy connection pool was failed", e);
//            } catch (InterruptedException e) {
//                LOGGER.error("Try to destroy connection pool was failed ", e);
//                Thread.currentThread().interrupt();
//            }
//        }
//        deregisterDriver();
//    }
//
//    private Connection createConnection() throws SQLException {
//        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//    }
//
//    private void deregisterDriver() {
//        Enumeration<Driver> drivers = DriverManager.getDrivers();
//        while (drivers.hasMoreElements()) {
//            Driver driver = drivers.nextElement();
//            try {
//                DriverManager.deregisterDriver(driver);
//            } catch (SQLException e) {
//                LOGGER.warn(String.format("Error to deregister driver %s", driver), e);
//            }
//        }
//    }
//}
////        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
////            try {
////                DriverManager.deregisterDriver(driver);
////            } catch (SQLException e) {
////                LOGGER.error("Failed to deregister driver", e);
////            }
////        });
//

