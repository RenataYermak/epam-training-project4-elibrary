package com.epam.yermak.project.dao;

import com.epam.yermak.project.dao.exception.DaoException;
import com.epam.yermak.project.model.order.BookOrder;
import com.epam.yermak.project.model.order.Order;
import com.epam.yermak.project.model.order.Status;

import java.util.List;

public interface BookOrderDao extends EntityDao<BookOrder, Long> {
    List<Order> findOrdersByOrderStatus(Status orderStatus) throws DaoException;
    List<Order> findOrdersByUserIdAndOrderStatus(Long userId, Status orderStatus) throws DaoException;
    Long orderBook(Order order) throws DaoException;
    void reserveBook(Long orderId) throws DaoException;
    void returnBook(Long orderId) throws DaoException;
    void rejectOrder(Long orderId) throws DaoException;
}
