package by.yermak.eliblary.controller;

public final class PagePath {
    /**
     * Book pages path
     */
    public static final String EDIT_BOOK = "/jsp/edit_book.jsp";
    public static final String BOOKS_TABLE = "/jsp/books_table.jsp";
    public static final String ADD_BOOK = "/jsp/add_book.jsp";
    /**
     * User pages path
     */
    public static final String USER_PROFILE = "/jsp/user_profile.jsp";
    public static final String USERS = "/jsp/users_table.jsp";
    /**
     * Other pages path
     */
    public static final String INDEX = "/index.jsp";
    public static final String SIGN_IN = "/jsp/sign_in.jsp";
    public static final String REGISTRATION = "/jsp/registration.jsp";
    public static final String ORDERS = "/jsp/orders.jsp";
    public static final String ABOUT_LIBRARY = "/jsp/about_library.jsp";
    /**
     * url path
     */
    public static final String BOOKS_TABLE_URL = "/controller?command=find_books";

    private PagePath() {
    }
}