package by.yermak.eliblary.dao.mapper;

public class ColumnName {
    /**
     * user
     */
    public static final String USER_ID = "user_id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String FIRSTNAME = "firstname";
    public static final String SECONDNAME = "secondname";
    public static final String EMAIL = "email";
    public static final String ROLE = "role";
    public static final String USER_STATUS = "status";
    public static final String ACTIVATION_DATE = "activation_date";
    public static final String DEACTIVATION_DATE = "deactivation_date";

    /**
     * book
     */
    public static final String BOOK_ID = "book_id";
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String CATEGORY = "category";
    public static final String PUBLISH_YEAR = "publish_year";
    public static final String DESCRIPTION = "description";
    // public static final String OVERALL_RATING = "overall_rating";
    public static final String NUMBER = "number";

    /**
     * bookOrder
     */
    public static final String ORDER_ID = "order_id";
    public static final String STATUS = "status";
    public static final String ISSUE = "issue";
    public static final String ORDERED_DATE = "ordered_date";
    public static final String RESERVED_DATE = "reserved_date";
    public static final String RETURNED_DATE = "returned_date";
    public static final String REJECTED_DATE = "rejected_date";
    public static final String USER_FIRSTNAME = "firstname";
    public static final String USER_SECONDNAME = "secondname";
    public static final String BOOK_TITLE = "title";

    private ColumnName() {
    }
}
