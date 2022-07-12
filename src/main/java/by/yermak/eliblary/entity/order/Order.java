package by.yermak.eliblary.entity.order;

import by.yermak.eliblary.entity.builder.OrderBuilder;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.user.User;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private Book book;
    private User user;
    private Status status;
    private Type type;
    private LocalDateTime orderedDate;
    private LocalDateTime reservedDate;
    private LocalDateTime returnedDate;
    private LocalDateTime rejectedDate;

    public Order() {
    }

    public Order(OrderBuilder orderBuilder) {
        this.id = orderBuilder.getId();
        this.book = orderBuilder.getBook();
        this.user = orderBuilder.getUser();
        this.status = orderBuilder.getStatus();
        this.type = orderBuilder.getType();
        this.orderedDate = orderBuilder.getOrderedDate();
        this.reservedDate = orderBuilder.getReservedDate();
        this.returnedDate = orderBuilder.getReturnedDate();
        this.rejectedDate = orderBuilder.getRejectedDate();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDateTime getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(LocalDateTime orderedDate) {
        this.orderedDate = orderedDate;
    }

    public LocalDateTime getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(LocalDateTime reservedDate) {
        this.reservedDate = reservedDate;
    }

    public LocalDateTime getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDateTime returnedDate) {
        this.returnedDate = returnedDate;
    }

    public LocalDateTime getRejectedDate() {
        return rejectedDate;
    }

    public void setRejectedDate(LocalDateTime rejectedDate) {
        this.rejectedDate = rejectedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(book, order.book) &&
               Objects.equals(user, order.user) && status == order.status &&
               type == order.type && Objects.equals(orderedDate, order.orderedDate) &&
               Objects.equals(reservedDate, order.reservedDate) &&
               Objects.equals(returnedDate, order.returnedDate) &&
               Objects.equals(rejectedDate, order.rejectedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, user, status, type,
                orderedDate, reservedDate, returnedDate, rejectedDate);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass());
        builder.append(" id = ").append(id);
        builder.append(" bookId = ").append(book);
        builder.append(" userId = ").append(user);
        builder.append(" type = ").append(type);
        builder.append(" status = ").append(status);
        builder.append(" orderedDate = ").append(orderedDate);
        builder.append(" reservedDate = ").append(reservedDate);
        builder.append(" reservedDate = ").append(reservedDate);
        builder.append(" rejectedDate = ").append(rejectedDate);
        return builder.toString();
    }

}
