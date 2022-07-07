package by.yermak.eliblary.controller.command;

public enum CommandName {
    /**
     * Book commands
     */
    ADD_BOOK,
    ADD_AUTHOR,
    EDIT_BOOK,
    EDIT_BOOK_PICTURE,
    FIND_BOOKS,
    FIND_BOOK,
    DELETE_BOOK,
    BOOK_SEARCH,
    FIND_ORDERS_BY_STATUS,
    FIND_ORDERS_BY_USER,
    ORDER_BOOK,
    RESERVE_BOOK,
    RETURN_BOOK,
    REJECT_ORDER,
    ABOUT_LIBRARY_PAGE,
    ADD_BOOK_PAGE,
    ADD_AUTHOR_PAGE,
    REGISTER_PAGE,
    BOOK_VIEW_PAGE,
    FIND_AUTHORS,

    /**
     * User commands
     */
    SIGN_IN,
    SIGN_OUT,
    REGISTRATION,
    EDIT_USER,
    FIND_USER,
    FIND_USERS,
    DELETE_USER,
    DEACTIVATE_USER,
    USER_SEARCH,
    CHANGE_LOCALE
}
