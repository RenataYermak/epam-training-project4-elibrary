package by.yermak.eliblary.service;

import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.service.exception.ServiceException;

import java.util.List;

/**
 * Describes the behavior of {@link Order} entity.
 */
public interface OrderService extends EntityService<Order, Long> {

    /**
     * Creat {@link Order} with filled fields
     *
     * @return order {@link Order}
     * @throws ServiceException if an error occurs while writing new {@link Order} into
     *                          data source
     */
    Long orderBook(Order order) throws ServiceException;


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
     * Find orders in page  {@link Order}
     *
     * @param page        count pages
     * @param orderStatus {@link Order}'s status
     * @return all  orders list {@link Order} instance
     * @throws ServiceException if  {@link Order}'s list don"t into
     *                          data source or if an error occurs while searching {@link Order}
     *                          into the data source
     */
    List<Order> findAll(int page, Status orderStatus) throws ServiceException;

    /**
     * Send message about reject {@link User}'s  {@link Order}  on  {@link User}'s email.
     *
     * @param firstName     the {@link User}'s firstname
     * @param secondName    the {@link User}'s secondname
     * @param bookName      the {@link Book}'s title
     * @param email         the {@link User}'s email
     * @param currentLocale the current locale, chosen by current user
     */
    void sendEmailRejectedOrder(String firstName, String secondName, String bookName, String email, String currentLocale);

    /**
     * Checks whether an {@link User}'s email is already exist
     *
     * @param userId a {@link User}'s id
     * @param bookId a {@link Book}'s id
     * @return whether an {@link Order}'s is already exist
     */
    boolean isOrderExist(Long bookId, Long userId) throws ServiceException;

    /**
     * Find orders in page  {@link Order}
     *
     * @param page        count pages
     * @param orderStatus {@link Order}'s status
     * @param userId      {@link Order}'s user id
     * @return all  orders list {@link Order} instance
     * @throws ServiceException if  {@link Order}'s list don"t into
     *                          data source or if an error occurs while searching {@link Order}
     *                          into the data source
     */
    List<Order> findAll(int page, Long userId, Status orderStatus) throws ServiceException;
}
