package by.yermak.eliblary.service;

import by.yermak.eliblary.model.book.Book;
import by.yermak.eliblary.model.order.Issue;
import by.yermak.eliblary.model.order.Order;
import by.yermak.eliblary.model.order.Status;
import by.yermak.eliblary.model.user.User;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.validator.Validator;

import java.util.List;

public interface BookService {
    /**
     * Find book {@link Book} instance by <tt>id</tt>
     *
     * @param id {@link Book}'s id
     * @return {@link Book instance
     * @throws ServiceException if {@link Book} with <tt>login</tt> do not present into
     *                          data source or if an error occurs while searching {@link Book}
     *                          into the data source
     */
    Book findBook(Long id) throws ServiceException;

    /**
     * Find all books list {@link Book}
     *
     * @return all books list {@link Book}
     * @throws ServiceException if {@link Book} in empty occurs after searching {@link Book}
     *                          into the data source
     */
    List<Book> findAllBooks() throws ServiceException;

    /**
     * Find  book in list {@link Book}
     *
     * @return book {@link Book}
     * @throws ServiceException if {@link Book} in empty occurs after searching {@link Book}
     *                          into the data source
     */
    List<Book> findBooksByQuery(String searchQuery) throws ServiceException;

    /**
     * Creat {@link Book} with filled fields
     *
     * @param book {@link Book} is filled book instance
     * @return book {@link Book}
     * @throws ServiceException if an error occurs while writing new {@link Book} into
     *                          data source
     */
    Book create(Book book) throws ServiceException;

    /**
     * Update {@link Book} with filled fields
     *
     * @param book {@link Book} is filled user instance
     * @return book {@link Book}
     * @throws ServiceException if <tt>book</tt>'s fields not accords to specify pattern
     *                          {@link Validator} or if an error occurs while writing new
     *                          {@link User} into data source
     */
    Book update(Book book) throws ServiceException;

    /**
     * Delete {@link Book} with filled fields
     *
     * @param id {@link Book}'s id
     * @throws ServiceException if {@link Book} with <tt>id</tt> do not present into
     *                          data source or if an error occurs while searching {@link Book}
     *                          into the data source
     */
    void delete(Long id) throws ServiceException;

    /**
     * Find  orders list {@link Order} by status
     *
     * @return all orders list by status {@link Order}
     * @throws ServiceException if {@link Order} in empty occurs after searching {@link Order} by
     *                          status into the data source
     */
    List<Order> findOrdersByOrderStatus(Status orderStatus) throws ServiceException;

    /**
     * Find  orders list {@link Order} by {@link User} id and {@link Order} status
     *
     * @return orders list {@link Order}
     * @throws ServiceException if {@link Order} in empty occurs after searching {@link Order} by
     *                          status into the data source or if  {@link User} with <tt>id</tt>
     *                          do not present into data source
     */
    List<Order> findOrdersByUserIdAndStatus(Long userId, Status orderStatus) throws ServiceException;

    Long orderBook(Long bookId, Long userId, Issue issue) throws ServiceException;

    /**
     * Reserved {@link Book}
     *
     * @param orderId {@link Order}'s id
     * @throws ServiceException if {@link Book} with <tt>id</tt> do not present into
     *                          data source or if an error occurs while searching {@link Book}
     *                          into the data source
     */
    void reserveBook(Long orderId) throws ServiceException;

    /**
     * Returned {@link Book}
     *
     * @param orderId {@link Order}'s id
     * @throws ServiceException if {@link Book} with <tt>id</tt> do not present into
     *                          data source or if an error occurs while searching {@link Book}
     *                          into the data source
     */
    void returnBook(Long orderId) throws ServiceException;

    /**
     * Rejected {@link Book}
     *
     * @param orderId {@link Order}'s id
     * @throws ServiceException if {@link Book} with <tt>id</tt> do not present into
     *                          data source or if an error occurs while searching {@link Book}
     *                          into the data source
     */
    void rejectedOrder(Long orderId) throws ServiceException;
}
