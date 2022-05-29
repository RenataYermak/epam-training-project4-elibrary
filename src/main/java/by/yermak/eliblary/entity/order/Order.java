package by.yermak.eliblary.entity.order;

import java.sql.Timestamp;
import java.util.Objects;

public class Order {
    private Long id;
    private Long bookId;
    private Long userId;
    private Issue issue;
    private Status status;
    private String userFirstName;
    private String userSecondName;
    private String bookTitle;
    private Timestamp orderedDate;
    private Timestamp reservedDate;
    private Timestamp returnedDate;
    private Timestamp rejectedDate;

    public Order() {
    }

    public Order(Long bookId, Long userId, Issue issue) {
        this.bookId = bookId;
        this.userId = userId;
        this.issue = issue;
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

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
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
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(bookId, order.bookId) &&
                Objects.equals(userId, order.userId) &&
                issue == order.issue && status == order.status &&
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
        return Objects.hash(id, bookId, userId, issue, status, userFirstName,
                userSecondName, bookTitle, orderedDate, reservedDate, returnedDate, rejectedDate);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", userId=" + userId +
                ", issue=" + issue +
                ", status=" + status +
                ", userFirstName='" + userFirstName + '\'' +
                ", userSecondName='" + userSecondName + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", orderedDate=" + orderedDate +
                ", reservedDate=" + reservedDate +
                ", reservedDate=" + returnedDate +
                ", rejectedDate=" + rejectedDate +
                '}';
    }
}
