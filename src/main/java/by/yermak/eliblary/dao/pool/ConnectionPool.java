package by.yermak.eliblary.dao.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Lock locker = new ReentrantLock(true);
    private static final AtomicBoolean isPoolCreated = new AtomicBoolean(false);
    private static final int POOL_SIZE = 6;
    private static ConnectionPool instance;

    private final BlockingQueue<ProxyConnection> freeConnection = new LinkedBlockingQueue<>(POOL_SIZE);
    private final BlockingQueue<ProxyConnection> usedConnection = new LinkedBlockingQueue<>(POOL_SIZE);

    private ConnectionPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            ProxyConnection connection;
            try {
                connection = ConnectionFactory.createConnection();
                freeConnection.put(connection);
            } catch (SQLException | InterruptedException e) {
                LOGGER.error("the connection pool is empty, no connections created", e);
                Thread.currentThread().interrupt();
                throw new ExceptionInInitializerError("Error while initialising connection pool");

            }
        }
    }

    public static ConnectionPool getInstance() {
        if (!isPoolCreated.get()) {
            locker.lock();
            try {
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

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection proxyConnection) {
            try {
                usedConnection.take();
                freeConnection.put(proxyConnection);
            } catch (InterruptedException e) {
                LOGGER.error("failed to release a connection", e);
                Thread.currentThread().interrupt();
            }
        }
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
        } catch (SQLException e) {
            LOGGER.error("Drivers were not de-registered", e);
        }
    }
}
