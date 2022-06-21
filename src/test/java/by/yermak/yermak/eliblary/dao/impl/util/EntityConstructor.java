package by.yermak.yermak.eliblary.dao.impl.util;

import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.book.Category;
import by.yermak.eliblary.entity.user.Role;
import by.yermak.eliblary.entity.user.User;

import java.util.Optional;

public final class EntityConstructor {
    public static User constructTestUser() {
        User user = new User();
        user.setLogin("test" + Math.random() * 10);
        user.setPassword("Test1997");
        user.setFirstName("test");
        user.setSecondName("test");
        user.setEmail("test@mail.ru");
        user.setRole(Role.ADMIN);
        return user;
    }

    public static Book constructTestBook() {
        Book book = new Book();
        book.setTitle("Test Title TT");
        book.setAuthor("Test Author TA");
        book.setCategory(Category.SCI_FI);
        book.setDescription("Test Description");
        book.setPublishYear(1999);
        book.setNumber(1);
        return book;
    }

    private EntityConstructor() {
    }
}