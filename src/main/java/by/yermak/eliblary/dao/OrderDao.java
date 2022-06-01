package by.yermak.eliblary.dao;

import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.dao.exception.DaoException;

import java.util.List;

public interface OrderDao extends EntityDao<Order> {
    Long orderBook(Order order) throws DaoException;

    List<Order> findOrdersByOrderStatus(Status orderStatus) throws DaoException;

    List<Order> findOrdersByUserIdAndOrderStatus(Long userId, Status orderStatus) throws DaoException;

    void reserveBook(Long orderId) throws DaoException;

    void returnBook(Long orderId) throws DaoException;

    void rejectOrder(Long orderId) throws DaoException;
}
