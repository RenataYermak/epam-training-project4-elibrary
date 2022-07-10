package by.yermak.eliblary.controller;

public final class PagePath {
    /**
     * Book pages path
     */
    public static final String EDIT_BOOK = "/jsp/edit_book.jsp";
    public static final String BOOKS_TABLE = "/jsp/books_table.jsp";
    public static final String ADD_BOOK = "/jsp/add_book.jsp";
    public static final String ADD_AUTHOR = "/jsp/add_author.jsp";
    public static final String BOOK_VIEW = "/jsp/book_view.jsp";
    /**
     * User pages path
     */
    public static final String USER_PROFILE = "/jsp/user_profile.jsp";
    public static final String USERS = "/jsp/users_table.jsp";
    public static final String DEACTIVATED_USERS = "/jsp/deactivated_users.jsp";
    /**
     * Other pages path
     */
    public static final String INDEX = "/index.jsp";
    public static final String SIGN_IN = "/jsp/sign_in.jsp";
    public static final String REGISTRATION = "/jsp/registration.jsp";
    public static final String ORDERS = "/jsp/orders.jsp";
    public static final String ABOUT_LIBRARY = "/jsp/about_library.jsp";
    public static final String ERROR_PAGE_404 = "/jsp/error/error404.jsp";
    public static final String ERROR_PAGE_500 = "/jsp/error/error500.jsp";
    /**
     * url path
     */
    public static final String BOOKS_TABLE_URL = "/controller?command=find_books";
    public static final String DEACTIVATED_USERS_URL ="/controller?command=find_deactivated_users";

    private PagePath() {
    }
}