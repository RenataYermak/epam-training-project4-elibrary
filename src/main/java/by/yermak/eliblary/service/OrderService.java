package by.yermak.eliblary.service;

import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.order.Type;
import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.service.exception.ServiceException;

import java.util.List;

/**
 * Describes the behavior of {@link Order} entity.
 */
public interface OrderService {

//    /**
//     * Find user {@link BookOrder} instance by <tt>id</tt>
//     *
//     * @param id {@link BookOrder}'s id
//     * @return {@link BookOrder} instance
//     * @throws ServiceException if {@link BookOrder} with <tt>login</tt> do not present into
//     *                          data source or if an error occurs while searching {@link BookOrder}
//     *                          into the data source
//     */
//    BookOrder findOrder(Long id) throws ServiceException;
//
//    /**
//     * Find all orders list {@link BookOrder}
//     *
//     * @return all users list {@link BookOrder}
//     * @throws ServiceException if {@link BookOrder} in empty
//     *                          occurs after searching {@link BookOrder} into the data source
//     */
//    List<BookOrder> findAll() throws ServiceException;

    /**
     * Creat {@link Order} with filled fields
     *
     * @param bookId  is filled user instance
     * @param userId  is filled user instance
     * @param type {@link Type} is filled user instance
     * @return user {@link Order}
     * @throws ServiceException if an error occurs while writing new {@link Order} into
     *                          data source
     */
    Long orderBook(Long bookId, Long userId, Type type) throws ServiceException;
//
//    /**
//     * Update {@link User} with filled fields
//     *
//     * @param user {@link User} is filled user instance
//     * @return
//     * @throws ServiceException if <tt>user</tt>'s fields not accords to specify pattern
//     *                          {@link Validator}
//     *                          or if user with <tt>email</tt> has already exist
//     *                          or if an error occurs while writing new {@link User} into
//     *                          data source
//     */
//    User update(User user) throws ServiceException;
//
//    /**
//     * Delete {@link User} with filled fields
//     *
//     * @param id {@link User}'s id
//     * @throws ServiceException if {@link User} with <tt>id</tt> do not present into
//     *                          data source or if an error occurs while searching {@link User}
//     *                          into the data source
//     */
//    void delete(Long id) throws ServiceException;

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
