package by.yermak.yermak.eliblary.model.statistic;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class BookStatistic implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long bookId;
    private Long userId;
    private Read read;
    private Rating rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Read getRead() {
        return read;
    }

    public void setRead(Read read) {
        this.read = read;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookStatistic that = (BookStatistic) o;
        return id.equals(that.id) && bookId.equals(that.bookId) &&
                userId.equals(that.userId) && read == that.read &&
                rating == that.rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, userId, read, rating);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass());
        builder.append(" id = ").append(id);
        builder.append(", bookId = ").append(bookId);
        builder.append(", userId = ").append(userId);
        builder.append(", read = ").append(read);
        builder.append(", rating = ").append(rating);
        return builder.toString();
    }
}
