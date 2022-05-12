package com.epam.project.dao.impl.util;

import com.epam.project.model.book.Book;
import com.epam.project.model.book.Category;
import com.epam.project.model.user.Role;
import com.epam.project.model.user.User;

import java.sql.Date;

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
