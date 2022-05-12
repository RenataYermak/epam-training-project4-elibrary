package com.epam.project.dao.impl;

import com.epam.project.dao.BookDao;
import com.epam.project.dao.BookOrderDao;
import com.epam.project.dao.UserDao;
import com.epam.project.dao.exception.DaoException;
import com.epam.project.model.book.Book;
import com.epam.project.model.order.BookOrder;
import com.epam.project.model.order.Issue;
import com.epam.project.model.order.Order;
import com.epam.project.model.order.Status;
import com.epam.project.model.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

import static com.epam.project.dao.impl.util.EntityConstructor.constructTestBook;
import static com.epam.project.dao.impl.util.EntityConstructor.constructTestUser;

class BookOrderDaoImplTest {

    private final UserDao userDao = new UserDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();
    private final BookOrderDao bookOrderDao = new BookOrderDaoImpl();

    @Test
    @DisplayName("Should find books by orderStatus")
    void findBooksByOrderStatus() throws DaoException {
        User user = userDao.create(constructTestUser());
        Book book1 = bookDao.create(constructTestBook());
        Book book2 = bookDao.create(constructTestBook());

        Order order1 = new Order();
        order1.setUserId(user.getId());
        order1.setBookId(book1.getId());
        order1.setIssue(Issue.READING_ROOM);

        Order order2 = new Order();
        order2.setUserId(user.getId());
        order2.setBookId(book2.getId());
        order2.setIssue(Issue.READING_ROOM);

        Long orderId1 = bookOrderDao.orderBook(order1);
        Long orderId2 = bookOrderDao.orderBook(order2);
        List<Order> ordersOrdered = bookOrderDao.findOrdersByOrderStatus(Status.ORDERED);
        assertThat(ordersOrdered.size(), is(2));

        bookOrderDao.delete(orderId1);
        bookOrderDao.delete(orderId2);
        bookDao.delete(book1.getId());
        bookDao.delete(book2.getId());
        userDao.delete(user.getId());
    }

    @Test
    @DisplayName("Should find books by userId and orderStatus")
    void findBooksByUserIdAndOrderStatus() throws DaoException {
        User user = userDao.create(constructTestUser());
        Book book1 = bookDao.create(constructTestBook());
        Book book2 = bookDao.create(constructTestBook());

        Order order1 = new Order();
        order1.setUserId(user.getId());
        order1.setBookId(book1.getId());
        order1.setIssue(Issue.READING_ROOM);

        Order order2 = new Order();
        order2.setUserId(user.getId());
        order2.setBookId(book2.getId());
        order2.setIssue(Issue.READING_ROOM);

        Long orderId1 = bookOrderDao.orderBook(order1);
        Long orderId2 = bookOrderDao.orderBook(order2);

        bookOrderDao.reserveBook(orderId1);
        book1.setNumber(book1.getNumber() - 1);

        List<Order> ordersOrdered = bookOrderDao.findOrdersByUserIdAndOrderStatus(user.getId(), Status.ORDERED);
        List<Order> ordersReserved = bookOrderDao.findOrdersByUserIdAndOrderStatus(user.getId(), Status.RESERVED);

        assertThat(ordersOrdered.size(), is(1));
        assertThat(ordersReserved.size(), is(1));

        bookOrderDao.delete(orderId1);
        bookOrderDao.delete(orderId2);
        bookDao.delete(book1.getId());
        bookDao.delete(book2.getId());
        userDao.delete(user.getId());
    }

    @Test
    @DisplayName("Should order book by order")
    void orderBook() throws DaoException {
        User user = userDao.create(constructTestUser());
        Book book = bookDao.create(constructTestBook());

        Order order = new Order();
        order.setUserId(user.getId());
        order.setBookId(book.getId());
        order.setIssue(Issue.READING_ROOM);

        Integer numberOfBooks = book.getNumber();
        Long orderId = bookOrderDao.orderBook(order);
        assertThat(orderId, notNullValue());

        BookOrder bookOrder = bookOrderDao.find(orderId);
        assertThat(bookOrder.getUserId(), is(user.getId()));
        assertThat(bookOrder.getBookId(), is(book.getId()));
        assertThat(bookOrder.getStatus(), is(Status.ORDERED));
        assertThat(bookOrder.getOrderedDate(), notNullValue());
        assertThat(bookOrder.getReservedDate(), nullValue());
        assertThat(bookOrder.getReturnedDate(), nullValue());
        assertThat(bookDao.find(book.getId()).getNumber(), is(numberOfBooks - 1));

        bookOrderDao.delete(orderId);
        bookDao.delete(book.getId());
        userDao.delete(user.getId());
    }

    @Test
    @DisplayName("Should reserve book")
    void reserveBook() throws DaoException {
        User user = userDao.create(constructTestUser());
        Book book1 = bookDao.create(constructTestBook());

        Order order1 = new Order();
        order1.setUserId(user.getId());
        order1.setBookId(book1.getId());
        order1.setIssue(Issue.READING_ROOM);

        Long orderId1 = bookOrderDao.orderBook(order1);
        bookOrderDao.reserveBook(orderId1);
        List<Order> reservedBooks = bookOrderDao.findOrdersByUserIdAndOrderStatus(user.getId(), Status.RESERVED);

        assertThat(reservedBooks.size(), is(1));
        assertThat(reservedBooks.get(0).getBookId(), is(book1.getId()));

        BookOrder bookOrder = bookOrderDao.find(orderId1);
        assertThat(bookOrder.getReservedDate(), notNullValue());

        bookOrderDao.delete(orderId1);
        bookDao.delete(book1.getId());
        userDao.delete(user.getId());
    }

    @Test
    @DisplayName("Should return book")
    void returnBook() throws DaoException {
        User user = userDao.create(constructTestUser());
        Book book = bookDao.create(constructTestBook());

        Order order1 = new Order();
        order1.setUserId(user.getId());
        order1.setBookId(book.getId());
        order1.setIssue(Issue.READING_ROOM);

        Integer numberOfBooks = bookDao.find(book.getId()).getNumber();
        Long orderId1 = bookOrderDao.orderBook(order1);
        bookOrderDao.reserveBook(orderId1);
        bookOrderDao.returnBook(orderId1);
        List<Order> returnedBooks = bookOrderDao.findOrdersByUserIdAndOrderStatus(user.getId(), Status.RETURNED);

        assertThat(returnedBooks.size(), is(1));
        assertThat(returnedBooks.get(0).getBookId(), is(book.getId()));
        assertThat(bookDao.find(book.getId()).getNumber(), is(numberOfBooks));

        BookOrder bookOrder = bookOrderDao.find(orderId1);
        assertThat(bookOrder.getReturnedDate(), notNullValue());

        bookOrderDao.delete(orderId1);
        bookDao.delete(book.getId());
        userDao.delete(user.getId());
    }

    @Test
    @DisplayName("Should reject order")
    void rejectOrder() throws DaoException {
        User user = userDao.create(constructTestUser());
        Book book = bookDao.create(constructTestBook());

        Order order = new Order();
        order.setUserId(user.getId());
        order.setBookId(book.getId());
        order.setIssue(Issue.READING_ROOM);

        Integer numberOfBooks = book.getNumber();
        Long orderId = bookOrderDao.orderBook(order);
        assertThat(orderId, notNullValue());

        bookOrderDao.rejectOrder(orderId);
        BookOrder rejectedOrder = bookOrderDao.find(orderId);
        assertThat(rejectedOrder.getUserId(), is(user.getId()));
        assertThat(rejectedOrder.getBookId(), is(book.getId()));
        assertThat(rejectedOrder.getStatus(), is(Status.REJECTED));
        assertThat(rejectedOrder.getOrderedDate(), notNullValue());
        assertThat(rejectedOrder.getReservedDate(), nullValue());
        assertThat(rejectedOrder.getReturnedDate(), nullValue());
        assertThat(rejectedOrder.getRejectedDate(), notNullValue());
        assertThat(bookDao.find(book.getId()).getNumber(), is(numberOfBooks));

        bookOrderDao.delete(orderId);
        bookDao.delete(book.getId());
        userDao.delete(user.getId());
    }
}