package by.yermak.yermak.eliblary.validator;

import by.yermak.eliblary.validator.BookValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookValidatorTest {
    private BookValidator validator;

    @BeforeEach
    void init() {
        validator = BookValidator.getInstance();
    }

    @Test
    void testCorrectTitleShouldBeValid() {
        String title = "Title";
        boolean isTitleValid = validator.isTitleValid(title);
        assertTrue(isTitleValid);
    }

    @Test
    void testNullTitleShouldBeInvalid() {
        boolean isTitleValid = validator.isTitleValid(null);
        assertFalse(isTitleValid);
    }

    @Test
    void testEmptyTitleShouldBeInvalid() {
        String title = "";
        boolean isTitleValid = validator.isTitleValid(title);
        assertFalse(isTitleValid);
    }

    @Test
    void testTitleShouldBeInvalid() {
        String title = "title17#*${83!@24";
        boolean isTitleValid = validator.isTitleValid(title);
        assertFalse(isTitleValid);
    }

    @Test
    void testShortTitleShouldBeInvalid() {
        String title = "T";
        boolean isTitleValid = validator.isTitleValid(title);
        assertFalse(isTitleValid);
    }

    @Test
    void testLongTitleWithSpacesShouldBeInvalid() {
        String title = "very long title that contains spaces";
        boolean isTitleValid = validator.isTitleValid(title);
        assertFalse(isTitleValid);
    }

    @Test
    void testCorrectAuthorShouldBeValid() {
        String author = "Author";
        boolean isAuthorValid = validator.isAuthorValid(author);
        assertTrue(isAuthorValid);
    }

    @Test
    void testNullAuthorShouldBeInvalid() {
        boolean isAuthorValid = validator.isAuthorValid(null);
        assertFalse(isAuthorValid);
    }

    @Test
    void testEmptyAuthorShouldBeInvalid() {
        String author = "";
        boolean isAuthorValid = validator.isAuthorValid(author);
        assertFalse(isAuthorValid);
    }

    @Test
    void testAuthorShouldBeInvalid() {
        String author = "author17#*${83!@24";
        boolean isAuthorValid = validator.isAuthorValid(author);
        assertFalse(isAuthorValid);
    }

    @Test
    void testShortAuthorShouldBeInvalid() {
        String author = "A";
        boolean isAuthorValid = validator.isAuthorValid(author);
        assertFalse(isAuthorValid);
    }

    @Test
    void testLongAuthorWithSpacesShouldBeInvalid() {
        String author = "very long author  that contains spaces";
        boolean isAuthorValid = validator.isAuthorValid(author);
        assertFalse(isAuthorValid);
    }

    ////////////////////////////////
    @Test
    void testCorrectMinNumberShouldBeValid() {
        int number = 0;
        boolean isNumberValid = validator.isNumberValid(number);
        assertTrue(isNumberValid);
    }

    @Test
    void testCorrectMaxNumberShouldBeValid() {
        int number = 100;
        boolean isNumberValid = validator.isNumberValid(number);
        assertTrue(isNumberValid);
    }

    @Test
    void testNegativeNumberShouldBeInvalid() {
        int number = -1;
        boolean isNumberValid = validator.isNumberValid(number);
        assertFalse(isNumberValid);
    }

    @Test
    void testBigNumberShouldBeInvalid() {
        int number = 101;
        boolean isNumberValid = validator.isNumberValid(number);
        assertFalse(isNumberValid);
    }

    @Test
    void testCorrectMinPublishYearShouldBeValid() {
        int publishYear = 1500;
        boolean isPublishYearValid = validator.isPublishYearValid(publishYear);
        assertTrue(isPublishYearValid);
    }

    @Test
    void testCorrectMaxPublishYearShouldBeValid() {
        int publishYear = LocalDate.now().getYear();
        boolean isPublishYearValid = validator.isPublishYearValid(publishYear);
        assertTrue(isPublishYearValid);
    }

    @Test
    void testNegativePublishYearShouldBeInvalid() {
        int publishYear = -1000;
        boolean isPublishYearValid = validator.isPublishYearValid(publishYear);
        assertFalse(isPublishYearValid);
    }

    @Test
    void testLessPublishYearShouldBeInvalid() {
        int publishYear = 1499;
        boolean isPublishYearValid = validator.isPublishYearValid(publishYear);
        assertFalse(isPublishYearValid);
    }

    @Test
    void testExtraPublishYearShouldBeInvalid() {
        int publishYear = LocalDate.now().getYear() + 1;
        boolean isPublishYearValid = validator.isPublishYearValid(publishYear);
        assertFalse(isPublishYearValid);
    }
}
