package by.yermak.yermak.eliblary.dao.impl.util;

import by.yermak.yermak.eliblary.model.book.Book;
import by.yermak.yermak.eliblary.model.book.Category;
import by.yermak.yermak.eliblary.model.user.Role;
import by.yermak.yermak.eliblary.model.user.User;

public final class EntityConstructor {
    public static User constructTestUser() {
        User user = new User();
        user.setLogin("test" + Math.random() * 10);
        user.setPassword("test");
        user.setFirstName("test");
        user.setSecondName("test");
        user.setRole(Role.ADMIN);
        return user;
    }

    public static Book constructTestBook() {
        Book book = new Book();
        book.setTitle("Test Title TT");
        book.setAuthor("Test Author TA");
        book.setCategory(Category.SCI_FI);
        book.setDescription("Test Description");
        book.setPublishYear(Integer.valueOf(("1865-11-26")));
        book.setNumber(1);
        return book;
    }

    private EntityConstructor() {}
}
