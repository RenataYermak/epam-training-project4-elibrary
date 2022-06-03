package by.yermak.eliblary.entity.order;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long bookId;
    private Long userId;
    private Type type;
    private Status status;
    private String userFirstName;
    private String userSecondName;
    private String bookTitle;
    private LocalDateTime orderedDate;
    private LocalDateTime reservedDate;
    private LocalDateTime returnedDate;
    private LocalDateTime rejectedDate;

    public Order() {
    }

    public Order(Long bookId, Long userId, Type type) {
        this.bookId = bookId;
        this.userId = userId;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserSecondName() {
        return userSecondName;
    }

    public void setUserSecondName(String userSecondName) {
        this.userSecondName = userSecondName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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
        return Objects.equals(id, order.id) &&
                Objects.equals(bookId, order.bookId) &&
                Objects.equals(userId, order.userId) &&
                type == order.type && status == order.status &&
                Objects.equals(userFirstName, order.userFirstName) &&
                Objects.equals(userSecondName, order.userSecondName) &&
                Objects.equals(bookTitle, order.bookTitle) &&
                Objects.equals(orderedDate, order.orderedDate) &&
                Objects.equals(reservedDate, order.reservedDate) &&
                Objects.equals(rejectedDate, order.rejectedDate) &&
                Objects.equals(returnedDate, order.returnedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, userId, type, status, userFirstName,
                userSecondName, bookTitle, orderedDate, reservedDate, returnedDate, rejectedDate);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass());
        builder.append(" id = ").append(id);
        builder.append(" bookId = ").append(bookId);
        builder.append(" userId = ").append(userId);
        builder.append(" type = ").append(type);
        builder.append(" status = ").append(status);
        builder.append("userFirstName= ").append(userFirstName);
        builder.append("userSecondName= ").append(userSecondName);
        builder.append("bookTitle= ").append(bookTitle);
        builder.append("orderedDate = ").append(orderedDate);
        builder.append(" reservedDate = ").append(reservedDate);
        builder.append(" reservedDate = ").append(reservedDate);
        builder.append(" rejectedDate = ").append(rejectedDate);
        return builder.toString();
    }
}
//    private Long id;
//    private Long bookId;
//    private Long userId;
//    private Status status;
//    private Type type;
//    private Timestamp orderedDate;
//    private Timestamp reservedDate;
//    private Timestamp returnedDate;
//    private Timestamp rejectedDate;
//
//    public Order() {
//    }
//
//    public Order(Long bookId, Long userId, Type type) {
//        this.bookId = bookId;
//        this.userId = userId;
//        this.type = type;
//    }
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(Long bookId) {
//        this.bookId = bookId;
//    }
//
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public Status getStatus() {
//        return status;
//    }
//
//    public void setStatus(Status status) {
//        this.status = status;
//    }
//
//    public Type getType() {
//        return type;
//    }
//
//    public void setType(Type type) {
//        this.type = type;
//    }
//
//    public Timestamp getOrderedDate() {
//        return orderedDate;
//    }
//
//    public void setOrderedDate(Timestamp orderedDate) {
//        this.orderedDate = orderedDate;
//    }
//
//    public Timestamp getReservedDate() {
//        return reservedDate;
//    }
//
//    public void setReservedDate(Timestamp reservedDate) {
//        this.reservedDate = reservedDate;
//    }
//
//    public Timestamp getReturnedDate() {
//        return returnedDate;
//    }
//
//    public void setReturnedDate(Timestamp returnedDate) {
//        this.returnedDate = returnedDate;
//    }
//
//    public Timestamp getRejectedDate() {
//        return rejectedDate;
//    }
//
//    public void setRejectedDate(Timestamp rejectedDate) {
//        this.rejectedDate = rejectedDate;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Order bookOrder = (Order) o;
//        return id.equals(bookOrder.id) && bookId.equals(bookOrder.bookId) &&
//                userId.equals(bookOrder.userId) && status == bookOrder.status &&
//                type == bookOrder.type && orderedDate.equals(bookOrder.orderedDate) &&
//                Objects.equals(reservedDate, bookOrder.reservedDate) &&
//                Objects.equals(returnedDate, bookOrder.returnedDate) &&
//                Objects.equals(rejectedDate, bookOrder.rejectedDate);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, bookId, userId, status, type,
//                orderedDate, reservedDate, returnedDate, rejectedDate);
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        builder.append(this.getClass());
//        builder.append(" id = ").append(id);
//        builder.append(" bookId = ").append(bookId);
//        builder.append(" userId = ").append(userId);
//        builder.append(" type = ").append(type);
//        builder.append(" status = ").append(status);
//        builder.append(" orderedDate = ").append(orderedDate);
//        builder.append(" reservedDate = ").append(reservedDate);
//        builder.append(" reservedDate = ").append(reservedDate);
//        builder.append(" rejectedDate = ").append(rejectedDate);
//        return builder.toString();
//    }
//}
