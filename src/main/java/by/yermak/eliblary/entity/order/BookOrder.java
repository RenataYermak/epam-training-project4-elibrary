package by.yermak.eliblary.entity.order;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class BookOrder implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long bookId;
    private Long userId;
    private Status status;
    private Issue issue;
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

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
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
                issue == bookOrder.issue && orderedDate.equals(bookOrder.orderedDate) &&
                Objects.equals(reservedDate, bookOrder.reservedDate) &&
                Objects.equals(returnedDate, bookOrder.returnedDate) &&
                Objects.equals(rejectedDate, bookOrder.rejectedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, userId, status, issue,
                orderedDate, reservedDate, returnedDate, rejectedDate);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass());
        builder.append(" id = ").append(id);
        builder.append(" bookId = ").append(bookId);
        builder.append(" userId = ").append(userId);
        builder.append(" issue = ").append(issue);
        builder.append(" status = ").append(status);
        builder.append(" orderedDate = ").append(orderedDate);
        builder.append(" reservedDate = ").append(reservedDate);
        builder.append(" reservedDate = ").append(reservedDate);
        builder.append(" rejectedDate = ").append(rejectedDate);
        return builder.toString();
    }
}
