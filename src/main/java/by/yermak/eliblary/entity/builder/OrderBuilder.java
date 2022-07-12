package by.yermak.eliblary.entity.builder;

import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.entity.order.Type;
import by.yermak.eliblary.entity.user.User;

import java.time.LocalDateTime;

public class OrderBuilder {
    private Long id;
    private Book book;
    private User user;
    private Status status;
    private Type type;
    private LocalDateTime orderedDate;
    private LocalDateTime reservedDate;
    private LocalDateTime returnedDate;
    private LocalDateTime rejectedDate;

    public Order build() {
        return new Order(this);
    }

    public Long getId() {
        return id;
    }

    public OrderBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public OrderBuilder setBook(Book book) {
        this.book = book;
        return this;
    }

    public User getUser() {
        return user;
    }

    public OrderBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public OrderBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Type getType() {
        return type;
    }

    public OrderBuilder setType(Type type) {
        this.type = type;
        return this;
    }

    public LocalDateTime getOrderedDate() {
        return orderedDate;
    }

    public OrderBuilder setOrderedDate(LocalDateTime orderedDate) {
        this.orderedDate = orderedDate;
        return this;
    }

    public LocalDateTime getReservedDate() {
        return reservedDate;
    }

    public OrderBuilder setReservedDate(LocalDateTime reservedDate) {
        this.reservedDate = reservedDate;
        return this;
    }

    public LocalDateTime getReturnedDate() {
        return returnedDate;
    }

    public OrderBuilder setReturnedDate(LocalDateTime returnedDate) {
        this.returnedDate = returnedDate;
        return this;
    }

    public LocalDateTime getRejectedDate() {
        return rejectedDate;
    }

    public OrderBuilder setRejectedDate(LocalDateTime rejectedDate) {
        this.rejectedDate = rejectedDate;
        return this;
    }
}
