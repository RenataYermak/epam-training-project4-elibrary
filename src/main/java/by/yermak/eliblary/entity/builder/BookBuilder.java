package by.yermak.eliblary.entity.builder;

import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.book.Category;

public class BookBuilder {
    private Long id;
    private String title;
    private String author;
    private Category category;
    private int publishYear;
    private String description;
    private int number;

    public Book build() {
        return new Book(this);
    }

    public Long getId() {
        return id;
    }

    public BookBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public  BookBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public BookBuilder setCategory(Category category) {
        this.category = category;
        return this;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public BookBuilder setPublishYear(int publishYear) {
        this.publishYear = publishYear;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BookBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public BookBuilder setNumber(int number) {
        this.number = number;
        return this;
    }

}
