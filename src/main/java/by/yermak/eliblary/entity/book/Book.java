package by.yermak.eliblary.entity.book;

import by.yermak.eliblary.entity.builder.BookBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private Author author;
    private Category category;
    private int publishYear;
    private String description;
    private int number;
    private String picture;

    public Book() {
    }

    public Book(BookBuilder builder) {
        this.id = builder.getId();
        this.title = builder.getTitle();
        this.author = builder.getAuthor();
        this.category = builder.getCategory();
        this.publishYear = builder.getPublishYear();
        this.publishYear = builder.getPublishYear();
        this.description = builder.getDescription();
        this.number = builder.getNumber();
        this.picture = builder.getPicture();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String pictures) {
        this.picture = pictures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return publishYear == book.publishYear && number == book.number && Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && category == book.category && Objects.equals(description, book.description) && Objects.equals(picture, book.picture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, category, publishYear, description, number, picture);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass());
        builder.append(" id = ").append(id);
        builder.append(" title = ").append(title);
        builder.append(" author = ").append(author);
        builder.append(" category = ").append(category);
        builder.append(" publishYear = ").append(publishYear);
        builder.append(" description = ").append(description);
        builder.append(" number = ").append(number);
        builder.append(" picture = ").append(picture);
        return builder.toString();
    }
}
