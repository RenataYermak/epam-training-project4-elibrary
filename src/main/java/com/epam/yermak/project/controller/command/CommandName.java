package com.epam.yermak.project.controller.command;

public enum CommandName {
    /**
     * Book commands
     */
    ADD_BOOK,
    EDIT_BOOK,
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
