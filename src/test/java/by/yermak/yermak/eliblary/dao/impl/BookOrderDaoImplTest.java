package by.yermak.yermak.eliblary.dao.impl;

import by.yermak.eliblary.dao.OrderDao;
import by.yermak.eliblary.dao.impl.BookDaoImpl;
import by.yermak.eliblary.dao.impl.OrderDaoImpl;
import by.yermak.eliblary.dao.impl.UserDaoImpl;
import by.yermak.eliblary.dao.BookDao;
import by.yermak.eliblary.dao.UserDao;
import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.entity.order.Type;
import by.yermak.eliblary.entity.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

import static by.yermak.yermak.eliblary.dao.impl.util.EntityConstructor.constructTestBook;
import static by.yermak.yermak.eliblary.dao.impl.util.EntityConstructor.constructTestUser;

class BookOrderDaoImplTest {

    private final UserDao userDao = new UserDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();
    private final OrderDao orderDao = new OrderDaoImpl();

    @Test
    @DisplayName("Should find books by orderStatus")
    void findBooksByOrderStatus() throws DaoException {
       Optional<User> user = userDao.create(constructTestUser());
        Optional<Book> book1 = bookDao.create(constructTestBook());
        Optional<Book> book2 = bookDao.create(constructTestBook());

        Order order1 = new Order();
        order1.setBook(book1.get());
        order1.setUser(user.get());
        order1.setType(Type.READING_ROOM);

        Order order2 = new Order();
        order2.setBook(book2.get());
        order2.setUser(user.get());
        order2.setType(Type.READING_ROOM);

        Long orderId1 = orderDao.orderBook(order1);
        Long orderId2 = orderDao.orderBook(order2);
        List<Order> ordersOrdered = orderDao.findOrdersByOrderStatus(Status.ORDERED);
        assertThat(ordersOrdered.size(), is(18));

        orderDao.delete(orderId1);
        orderDao.delete(orderId2);
        bookDao.delete(book1.get().getId());
        bookDao.delete(book2.get().getId());
        userDao.delete(user.get().getId());
    }

    @Test
    @DisplayName("Should find books by userId and orderStatus")
    void findBooksByUserIdAndOrderStatus() throws DaoException {
        Optional<User> user = userDao.create(constructTestUser());
        Optional<Book> book1 = bookDao.create(constructTestBook());
        Optional<Book> book2 = bookDao.create(constructTestBook());

        Order order1 = new Order();
        order1.setBook(book1.get());
        order1.setUser(user.get());
        order1.setType(Type.READING_ROOM);

        Order order2 = new Order();
        order2.setBook(book2.get());
        order2.setUser(user.get());
        order2.setType(Type.READING_ROOM);

        Long orderId1 = orderDao.orderBook(order1);
        Long orderId2 = orderDao.orderBook(order2);

        orderDao.reserveBook(orderId1);
        book1.get().setNumber(book1.get().getNumber() - 1);

        List<Order> ordersOrdered = orderDao.findOrdersByUserIdAndOrderStatus(user.get().getId(), Status.ORDERED);
        List<Order> ordersReserved = orderDao.findOrdersByUserIdAndOrderStatus(user.get().getId(), Status.RESERVED);

        assertThat(ordersOrdered.size(), is(1));
        assertThat(ordersReserved.size(), is(1));

        orderDao.delete(orderId1);
        orderDao.delete(orderId2);
        bookDao.delete(book1.get().getId());
        bookDao.delete(book2.get().getId());
        userDao.delete(user.get().getId());
    }

    @Test
    @DisplayName("Should order book by order")
    void orderBook() throws DaoException {
        Optional<User> user = userDao.create(constructTestUser());
        Optional<Book> book = bookDao.create(constructTestBook());

        Order order = new Order();
        order.setBook(book.get());
        order.setUser(user.get());
        order.setType(Type.READING_ROOM);

        Integer numberOfBooks = book.get().getNumber();
        Long orderId = orderDao.orderBook(order);
        assertThat(orderId, notNullValue());

        Optional<Order> bookOrder = orderDao.find(orderId);
        assertThat(bookOrder.get().getUser().getId(), is(user.get().getId()));
        assertThat(bookOrder.get().getBook().getId(), is(book.get().getId()));
        assertThat(bookOrder.get().getStatus(), is(Status.ORDERED));
        assertThat(bookOrder.get().getOrderedDate(), notNullValue());
        assertThat(bookOrder.get().getReservedDate(), nullValue());
        assertThat(bookOrder.get().getReturnedDate(), nullValue());
        assertThat(bookDao.find(book.get().getId()).get().getNumber(), is(numberOfBooks - 1));

        orderDao.delete(orderId);
        bookDao.delete(book.get().getId());
        userDao.delete(user.get().getId());
    }

    @Test
    @DisplayName("Should reserve book")
    void reserveBook() throws DaoException {
        Optional<User> user = userDao.create(constructTestUser());
        Optional<Book> book1 = bookDao.create(constructTestBook());

        Order order1 = new Order();
        order1.setBook(book1.get());
        order1.setUser(user.get());
        order1.setType(Type.READING_ROOM);

        Long orderId1 = orderDao.orderBook(order1);
        orderDao.reserveBook(orderId1);
        List<Order> reservedBooks = orderDao.findOrdersByUserIdAndOrderStatus(user.get().getId(), Status.RESERVED);

        assertThat(reservedBooks.size(), is(1));
        assertThat(reservedBooks.get(0).getBook().getId(), is(book1.get().getId()));

        Optional<Order> bookOrder = orderDao.find(orderId1);
        assertThat(bookOrder.get().getReservedDate(), notNullValue());

        orderDao.delete(orderId1);
        bookDao.delete(book1.get().getId());
        userDao.delete(user.get().getId());
    }

    @Test
    @DisplayName("Should return book")
    void returnBook() throws DaoException {
        Optional<User> user = userDao.create(constructTestUser());
        Optional<Book> book = bookDao.create(constructTestBook());

        Order order1 = new Order();
        order1.setBook(book.get());
        order1.setUser(user.get());
        order1.setType(Type.READING_ROOM);

        Integer numberOfBooks = bookDao.find(book.get().getId()).get().getNumber();
        Long orderId1 = orderDao.orderBook(order1);
        orderDao.reserveBook(orderId1);
        orderDao.returnBook(orderId1);
        List<Order> returnedBooks = orderDao.findOrdersByUserIdAndOrderStatus(user.get().getId(), Status.RETURNED);

        assertThat(returnedBooks.size(), is(1));
        assertThat(returnedBooks.get(0).getBook().getId(), is(book.get().getId()));
        assertThat(bookDao.find(book.get().getId()).get().getNumber(), is(numberOfBooks));

        Optional<Order> bookOrder = orderDao.find(orderId1);
        assertThat(bookOrder.get().getReturnedDate(), notNullValue());

        orderDao.delete(orderId1);
        bookDao.delete(book.get().getId());
        userDao.delete(user.get().getId());
    }

    @Test
    @DisplayName("Should reject order")
    void rejectOrder() throws DaoException {
        Optional<User> user = userDao.create(constructTestUser());
        Optional<Book> book = bookDao.create(constructTestBook());

        Order order = new Order();
        order.setBook(book.get());
        order.setUser(user.get());
        order.setType(Type.READING_ROOM);

        Integer numberOfBooks = book.get().getNumber();
        Long orderId = orderDao.orderBook(order);
        assertThat(orderId, notNullValue());

        orderDao.rejectOrder(orderId);
        Optional<Order> rejectedOrder = orderDao.find(orderId);
        assertThat(rejectedOrder.get().getUser().getId(), is(user.get().getId()));
        assertThat(rejectedOrder.get().getBook().getId(), is(book.get().getId()));
        assertThat(rejectedOrder.get().getStatus(), is(Status.REJECTED));
        assertThat(rejectedOrder.get().getOrderedDate(), notNullValue());
        assertThat(rejectedOrder.get().getReservedDate(), nullValue());
        assertThat(rejectedOrder.get().getReturnedDate(), nullValue());
        assertThat(rejectedOrder.get().getRejectedDate(), notNullValue());
        assertThat(bookDao.find(book.get().getId()).get().getNumber(), is(numberOfBooks));

        orderDao.delete(orderId);
        bookDao.delete(book.get().getId());
        userDao.delete(user.get().getId());
    }
}