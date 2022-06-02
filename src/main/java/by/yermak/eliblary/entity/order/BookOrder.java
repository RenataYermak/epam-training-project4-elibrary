package by.yermak.eliblary.entity.order;

import java.sql.Timestamp;
import java.util.Objects;

public class BookOrder {
    private Long id;
    private Long bookId;
    private Long userId;
    private Status status;
    private Type type;
    private Timestamp orderedDate;
    private Timestamp reservedDate;
    private Timestamp returnedDate;
    private Timestamp rejectedDate;

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

    public Timestamp getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(Timestamp orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Timestamp getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(Timestamp reservedDate) {
        this.reservedDate = reservedDate;
    }

    public Timestamp getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Timestamp returnedDate) {
        this.returnedDate = returnedDate;
    }

    public Timestamp getRejectedDate() {
        return rejectedDate;
    }

    public void setRejectedDate(Timestamp rejectedDate) {
        this.rejectedDate = rejectedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookOrder bookOrder = (BookOrder) o;
        return id.equals(bookOrder.id) && bookId.equals(bookOrder.bookId) &&
                userId.equals(bookOrder.userId) && status == bookOrder.status &&
                type == bookOrder.type && orderedDate.equals(bookOrder.orderedDate) &&
                Objects.equals(reservedDate, bookOrder.reservedDate) &&
                Objects.equals(returnedDate, bookOrder.returnedDate) &&
                Objects.equals(rejectedDate, bookOrder.rejectedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, userId, status, type,
                orderedDate, reservedDate, returnedDate, rejectedDate);
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
        builder.append(" orderedDate = ").append(orderedDate);
        builder.append(" reservedDate = ").append(reservedDate);
        builder.append(" reservedDate = ").append(reservedDate);
        builder.append(" rejectedDate = ").append(rejectedDate);
        return builder.toString();
    }
}
