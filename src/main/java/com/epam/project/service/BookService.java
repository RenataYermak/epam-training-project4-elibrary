package com.epam.project.service;

import com.epam.project.dao.exception.DaoException;
import com.epam.project.model.book.Book;
import com.epam.project.model.order.Issue;
import com.epam.project.model.order.Order;
import com.epam.project.model.order.Status;
import com.epam.project.service.exception.ServiceException;

import java.util.List;

public interface BookService {
    Book findBook(Long id) throws ServiceException;
    List<Book> findAllBooks() throws ServiceException;
    List<Book> findBooksByQuery(String searchQuery) throws ServiceException;
    Book create(Book book) throws ServiceException;
    Book update(Book book) throws ServiceException;
    void delete(Long id) throws ServiceException;
    List<Order> findOrdersByOrderStatus(Status orderStatus) throws ServiceException;
    List<Order> findOrdersByUserIdAndStatus(Long userId, Status orderStatus) throws ServiceException;
    Long orderBook(Long bookId, Long userId, Issue issue) throws ServiceException;
    void reserveBook(Long orderId) throws ServiceException;
    void returnBook(Long orderId) throws ServiceException;
    void rejectedOrder(Long orderId) throws ServiceException;
}