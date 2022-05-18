package com.epam.yermak.project.dao.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.yermak.project.dao.config.exception.ConnectionPoolException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final AtomicBoolean isPoolCreated = new AtomicBoolean(false);
    private static final Lock locker = new ReentrantLock(true);
    private static final int POOL_SIZE = 5;
    private static ConnectionPool instance;
    private final BlockingDeque<Connection> freeConnections;
    private final BlockingDeque<Connection> usedConnections;


    private ConnectionPool() {
        freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        usedConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
               Connection connection = ConnectionFactory.createConnection();
                freeConnections.add( connection);
            } catch (SQLException e) {
                logger.error("exception in open connection: ", e);
            }
        }
        if (freeConnections.isEmpty()) {
            logger.fatal("the connection pool is empty, no connections created");
            throw new RuntimeException("the connection pool is empty, no connections were created");
        }
    }

    /**
     * Get instance of the connection pool.
     *
     * @return the connection pool instance
     */
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

    /**
     * Takes a free connection from the ConnectionPool
     *
     * @return a connection
     */
    public Connection getConnection() {
       Connection connection = null;
        try {
            connection = freeConnections.take();
            usedConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error("fexception in get connection from pool:", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * Releases a connection
     *
     * @param connection used connection
     * @throws ConnectionPoolException if receives the wrong instance of the connection
     */
    public void releaseConnection(Connection connection) throws ConnectionPoolException {

//        if (!(connection instanceof ProxyConnection)) {
//            logger.error("wrong instance");
//            throw new ConnectionPoolException("wrong instance");
//        }
            usedConnections.remove(connection);
            freeConnections.offer(connection);

    }

    /**
     * Destroys the connection pool
     */
    public void destroy() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.take().close();;
            } catch (SQLException e) {
                logger.error("failed to destroy pool", e);
            } catch (InterruptedException e) {
                logger.error("failed to destroy pool", e);
                Thread.currentThread().interrupt();
            }
        }
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                DriverManager.deregisterDriver(drivers.nextElement());
            }
        } catch (SQLException exception) {
            logger.error("Drivers were not de-registered");
        }
    }
}
