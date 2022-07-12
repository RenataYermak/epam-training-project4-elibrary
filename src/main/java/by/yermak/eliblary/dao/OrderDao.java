package by.yermak.eliblary.dao;

import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.dao.exception.DaoException;

import java.util.List;

/**
 * Interface describes the opportunity that data source provide to store and
 * restore {@link Order} entity
 */
public interface OrderDao extends EntityDao<Order, Long> {
    boolean isOrderExist(Long bookId, Long userId) throws DaoException;

    /**
     * Creates a new {@link Order}
     *
     * @param order the {@link Order}
     * @throws DaoException if there is any problem during access
     */
    Long orderBook(Order order) throws DaoException;

    /**
     * Finds and returns the {@link Order} list by orderStatus and collect them in the list
     * If no such BookOrder contains into data source returns emptyList
     *
     * @param orderStatus is the {@link Order}'s status, {@link Order} field
     * @return {@link Order} list
     * @throws DaoException if there is any problem during access
     */
    List<Order> findOrdersByOrderStatus(Status orderStatus) throws DaoException;

    /**
     * Finds and returns the {@link Order} list by userId and  orderStatus and collect them in the list
     * If no such BookOrder contains into data source returns emptyList
     *
     * @param userId is the {@link User}'s id, {@link Order} field
     * @return {@link Order} list
     * @throws DaoException if there is any problem during access
     */
    List<Order> findOrdersByUserIdAndOrderStatus(Long userId, Status orderStatus) throws DaoException;

    /**
     * Updates the {@link Order}'s status in a data source to reserved.
     *
     * @param orderId the {@link Order}
     * @throws DaoException if there is any problem during access
     */
    void reserveBook(Long orderId) throws DaoException;

    /**
     * Updates the {@link Order}'s status in a data source to returned.
     *
     * @param orderId the {@link Order}
     * @throws DaoException if there is any problem during access
     */
    void returnBook(Long orderId) throws DaoException;

    /**
     * Updates the {@link Order}'s status in a data source to reserved.
     *
     * @param orderId the {@link Order}
     * @throws DaoException if there is any problem during access
     */
    void rejectOrder(Long orderId) throws DaoException;

    /**
     * Finds users in page
     *
     * @param page        is the count pages
     * @param orderStatus is the {@link Order}'s status
     * @return {@link Order}  list of books in page
     * @throws DaoException if there is any problem during access
     */
    List<Order> findAlL(int page, Status orderStatus) throws DaoException;

    /**
     * Finds users in page
     *
     * @param page        is the count pages
     * @param userId is the {@link User}'s id
     * @param orderStatus is the {@link Order}'s status
     * @return {@link Order}  list of books in page
     * @throws DaoException if there is any problem during access
     */
    List<Order> findAlL(int page, Long userId, Status orderStatus) throws DaoException;
}
