package by.yermak.eliblary.validator;

import by.yermak.eliblary.entity.book.Book;

import java.time.LocalDate;


public final class BookValidator extends AbstractValidator {
    private static final String BOOK_AUTHOR_REGEX = "^[\\p{L}\\d-\\s']{2,25}$";
    private static final String BOOK_DESCRIPTION_REGEX = "^[\\p{L}\\d\\p{S}\\p{So}\\p{P}\\s\\f\\n\\r\\t\\v]{10,3000}$";
    private static final String SEARCH_REGEX = "^[\\p{L}\\d-\\s]{1,25}$";

    private static BookValidator instance;

    private BookValidator() {
    }

    public static BookValidator getInstance() {
        if (instance == null) {
            instance = new BookValidator();
        }
        return instance;
    }

    public boolean isBookValid(Book book) {
        return  isTitleValid(book.getTitle()) && isNumberValid(book.getNumber())
                && isPublishYearValid(book.getPublishYear());
    }

    public boolean isTitleValid(String title) {
        return title != null && isFieldValid(BOOK_AUTHOR_REGEX, title);
    }

    public boolean isAuthorValid(String author) {
        return author != null && isFieldValid(BOOK_AUTHOR_REGEX, author);
    }

    public boolean isSearchValid(String searchQuery) {
        return searchQuery != null && isFieldValid(SEARCH_REGEX, searchQuery);
    }

    public boolean isDescriptionValid(String description) {
        return description != null && isFieldValid(BOOK_DESCRIPTION_REGEX, description);
    }

    public boolean isPublishYearValid(int publishYear) {
        int currentYear = LocalDate.now().getYear();
        return publishYear >= 1500 && publishYear <= currentYear;
    }

    public boolean isNumberValid(int number) {
        return number >= 0 && number <= 100;
    }
}
