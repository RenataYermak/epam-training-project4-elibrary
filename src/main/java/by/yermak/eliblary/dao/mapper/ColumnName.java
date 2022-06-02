package by.yermak.eliblary.dao.mapper;

public class ColumnName {
    /**
     * user
     */
    public static final String USER_ID = "u.user_id";
    public static final String LOGIN = "u.login";
    public static final String PASSWORD = "u.password";
    public static final String FIRSTNAME = "u.firstname";
    public static final String SECONDNAME = "u.secondname";
    public static final String EMAIL = "u.email";
    public static final String ROLE = "ur.role_name";
    public static final String USER_STATUS = "us.status_name";
    public static final String ACTIVATION_DATE = "u.activation_date";
    public static final String DEACTIVATION_DATE = "u.deactivation_date";

    /**
     * book
     */
    public static final String BOOK_ID = "b.book_id";
    public static final String TITLE = "b.title";
    public static final String AUTHOR = "b.author";
    public static final String CATEGORY = "bc.category_name";
    public static final String PUBLISH_YEAR = "b.publish_year";
    public static final String DESCRIPTION = "b.description";
    public static final String NUMBER = "b.number";

    /**
     * bookOrder
     */
    public static final String ORDER_ID = "o.order_id";
    public static final String STATUS = "os.order_status_name";
    public static final String TYPE = "ot.order_type_name";
    public static final String ORDERED_DATE = "o.ordered_date";
    public static final String RESERVED_DATE = "o.reserved_date";
    public static final String RETURNED_DATE = "o.returned_date";
    public static final String REJECTED_DATE = "o.rejected_date";
    public static final String USER_FIRSTNAME = "o.firstname";
    public static final String USER_SECONDNAME = "o.secondname";
    public static final String BOOK_TITLE = "o.title";

    private ColumnName() {
    }
}
