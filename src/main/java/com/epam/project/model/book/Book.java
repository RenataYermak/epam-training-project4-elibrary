package com.epam.project.model.book;

import java.util.Objects;

public class Book {
    private Long id;
    private String title;
    private String author;
    private Category category;
    private Integer publishYear;
    private String description;
    private Double overallRating;
    private Integer number;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(Double overallRating) {
        this.overallRating = overallRating;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id) && title.equals(book.title) &&
                author.equals(book.author) && category == book.category &&
                publishYear.equals(book.publishYear) && Objects.equals(description, book.description)
                && Objects.equals(overallRating, book.overallRating) && number.equals(book.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, category,publishYear, description, overallRating, number);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category=" + category +
                ", publishYear=" + publishYear +
                ", description='" + description + '\'' +
                ", overallRating=" + overallRating +
                ", number=" + number +
                '}';
    }
}
