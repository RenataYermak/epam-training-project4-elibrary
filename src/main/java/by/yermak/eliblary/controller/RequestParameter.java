package by.yermak.eliblary.controller;

public final class RequestParameter {

    /**
     * Book params
     */
    public static final String BOOK_ID = "bookId";
    public static final String BOOK_AUTHOR = "author";
    public static final String BOOK_TITLE = "title";
    public static final String BOOK_PUBLISH_YEAR = "publishYear";
    public static final String BOOK_CATEGORY = "category";
    public static final String BOOK_NUMBER = "number";
    public static final String BOOK_PICTURE = "picture";
    public static final String BOOK_DESCRIPTION = "description";
    /**
     * User params
     */
    public static final String USER_ID = "userId";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_FIRSTNAME = "firstName";
    public static final String USER_SECONDNAME = "secondName";
    public static final String USER_EMAIL = "email";
    public static final String USER_ROLE = "userRole";

    /**
     * Order book
     */
    public static final String ORDER_ID = "orderId";
    public static final String ORDER_STATUS = "orderStatus";
    public static final String TYPE = "type";

    /**
     * Author
     */
    public static final String AUTHOR_NAME = "authorName";

    /**
     * Other
     */
    public static final String COMMAND = "command";
    public static final String SEARCH_QUERY = "searchQuery";

    private RequestParameter() {
    }
}
