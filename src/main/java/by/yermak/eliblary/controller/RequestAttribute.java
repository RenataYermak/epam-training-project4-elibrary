package by.yermak.eliblary.controller;

public final class RequestAttribute {
    /**
     * Book attributes
     */
    public static final String BOOKS = "books";
    public static final String BOOK = "book";
    public static final String BOOK_TITLE = "bookTitle";

    /**
     * User attributes
     */
    public static final String USER_NAME = "userName";
    public static final String USERS = "users";
    public static final String USER = "user";
    public static final String USER_SEARCH = "userSearch";

    /**
     * Order attributes
     */
    public static final String ORDER_ID = "orderId";
    public static final String ORDERS = "orders";
    public static final String ORDER_STATUS = "orderStatus";
    public static final String ORDERS_PAGE_TITLE = "ordersPageTitle";

    /**
     * Other
     */
    public static final String ERROR_MESSAGE_SIGN_IN = "errorMessageSignIn";
    public static final String WARNING_MESSAGE_BOOK_SEARCH = "warningMessageBookSearch";
    public static final String WARNING_MESSAGE_USER_SEARCH = "warningMessageUserSearch";
    public static final String SUCCESS_MESSAGE_USER_UPDATE = "successMessageUserUpdated";
    public static final String SUCCESS_MESSAGE_BOOK_UPDATE = "successMessageBookUpdated";
    public static final String WARNING_MESSAGE_PASS_MISMATCH = "warningMessagePassMismatch";
    public static final String SEARCH_QUERY = "searchQuery";
    public static final String LOCALE_NAME = "locale";
    public static final String DEFAULT_LANG = "en_EN";
    public static final String RUSSIAN_LANG = "ru_RU";
    public static final String CURRENT_PAGE = "current_page";


    private RequestAttribute() {
    }
}
