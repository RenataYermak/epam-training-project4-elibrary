package com.epam.project.controller;

public final class RequestParam {
    /**
     * Command params
     */
    public static final String COMMAND = "command";
    public static final String PAGE_PATH = "path";

    /**
     * Book params
     */
    public static final String BOOK_ID = "bookId";
    public static final String BOOK_AUTHOR = "bookAuthor";
    public static final String BOOK_TITLE = "bookTitle";
    public static final String BOOK_PUBLISH_YEAR = "publishYear";
    public static final String BOOK_CATEGORY = "bookCategory";
    public static final String BOOK_DESCRIPTION = "bookDescription";
    public static final String BOOK_NUMBER = "number";
    public static final String BOOK_OVERALL_RATING = "overallRating";


    /**
     * User params
     */
    public static final String USER_ID = "userId";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_FIRSTNAME = "firstName";
    public static final String USER_SECONDNAME = "secondName";
    public static final String USER_ROLE = "userRole";
    public static final String USER_REPEAT_PASSWORD = "repeatPassword";

    /**
     * Order book
     */
    public static final String ORDER_ID = "orderId";
    public static final String ORDER_STATUS = "orderStatus";
    public static final String ISSUE = "issue";

    /**
     * Other
     */
    public static final String SEARCH_QUERY = "searchQuery";

    private RequestParam() {}
}
