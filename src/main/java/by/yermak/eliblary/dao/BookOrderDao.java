package by.yermak.eliblary.dao;

import by.yermak.eliblary.model.order.BookOrder;
import by.yermak.eliblary.model.order.Order;
import by.yermak.eliblary.model.order.Status;
import by.yermak.eliblary.dao.exception.DaoException;

import java.util.List;

public interface BookOrderDao extends  EntityDao<BookOrder> {
    List<Order> findOrdersByOrderStatus(Status orderStatus) throws DaoException;

    List<Order> findOrdersByUserIdAndOrderStatus(Long userId, Status orderStatus) throws DaoException;

    Long orderBook(Order order) throws DaoException;

    void reserveBook(Long orderId) throws DaoException;

    void returnBook(Long orderId) throws DaoException;

    void rejectOrder(Long orderId) throws DaoException;
}
