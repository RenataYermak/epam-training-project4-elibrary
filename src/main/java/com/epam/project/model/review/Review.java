package com.epam.project.model.review;

import java.sql.Timestamp;
import java.util.Objects;

public class Review {
    private Long id;
    private Long bookId;
    private Long userId;
    private String review;
    private Timestamp reviewDate;

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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Timestamp getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Timestamp reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review1 = (Review) o;
        return id.equals(review1.id) &&
                bookId.equals(review1.bookId) &&
                userId.equals(review1.userId) &&
                Objects.equals(review, review1.review) &&
                Objects.equals(reviewDate, review1.reviewDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, userId, review, reviewDate);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", userId=" + userId +
                ", review='" + review + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }
}
