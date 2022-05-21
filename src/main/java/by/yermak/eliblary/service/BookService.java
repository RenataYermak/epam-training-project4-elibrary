package by.yermak.eliblary.service;

import by.yermak.eliblary.model.book.Book;
import by.yermak.eliblary.model.order.Issue;
import by.yermak.eliblary.model.order.Order;
import by.yermak.eliblary.model.order.Status;
import by.yermak.eliblary.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

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
