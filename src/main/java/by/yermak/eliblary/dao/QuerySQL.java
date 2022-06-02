package by.yermak.eliblary.dao;

public final class QuerySQL {
    /**
     * user
     */
    public static final String SELECT_USER_BY_ID = """
            SELECT u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date
            FROM users u
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            WHERE u.user_id=?""";
    public static final String SELECT_USER_BY_LOGIN = """
            SELECT u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date
            FROM users u
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            WHERE u.login=?""";
    public static final String SELECT_USER_BY_LOGIN_AND_PASS = """
            SELECT u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date
            FROM users u
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            WHERE u.login=? and u.password=? AND us.status_name='ACTIVATED'""";
    public static final String SELECT_ALL_USERS = """
            SELECT u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date
            FROM users u
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id""";
    public static final String SELECT_ALL_ACTIVATED_USERS = """
            SELECT u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date
            FROM users u
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            WHERE us.status_name='ACTIVATED'""";
    public static final String SELECT_ALL_DEACTIVATED_USERS = """
            SELECT u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date
            FROM users u
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            WHERE us.status_name='DEACTIVATED'""";
    public static final String INSERT_USER = """
            INSERT INTO users
            (login, password, firstname, secondname, email, role_id)
            VALUES (?, ?, ?, ?, ?, (SELECT ur.role_id FROM user_roles ur WHERE role_name = ?))""";
    public static final String UPDATE_USER = """
            UPDATE users u
            SET u.login =?,
                u.password=?,
                u.firstname=?,
                u.secondname=?,
                u.email=?,
                u.role_id=(SELECT ur.role_id FROM user_roles ur WHERE role_name = ?)
            WHERE u.user_id =?""";
    public static final String DEACTIVATE_USER = """
            UPDATE users u
            SET u.status_id=(SELECT status_id FROM user_statuses us WHERE status_name='DEACTIVATED',
                u.deactivation_date=?
            WHERE u.user_id =?""";
    public static final String DELETE_USER = """
            DELETE
            FROM users u
            WHERE u.user_id =?""";
    public static final String USER_SEARCH = """
            SELECT u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date
            FROM users u
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            WHERE u.login LIKE CONCAT('%',?,'%') OR u.firstname LIKE CONCAT('%',?,'%')
                  OR u.secondname LIKE CONCAT('%',?,'%')""";
    public static final String UPDATE_PASSWORD = """
            UPDATE users u
            SET u.password =?
            WHERE u.user_id =?""";
    public static final String SQL_IS_EMAIL_EXIST = """
            SELECT user_id
            FROM users u
            WHERE u.email = ?
            LIMIT 1""";
    public static final String SQL_IS_LOGIN_EXIST = """
            SELECT user_id
            FROM users u
            WHERE u.login = ?
            LIMIT 1""";
    public static final String FIND_PAGE_QUERY_USERS = """
            SELECT u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date
            FROM users u
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            LIMIT ?,?""";

    /**
     * book
     */
    public static final String SELECT_ALL_BOOKS = """
            SELECT b.book_id,
                   b.title,
                   b.author,
                   bc.category_name,
                   b.publish_year,
                   b.description,
                   b.number
            FROM books b
            JOIN book_categories bc ON bc.category_id = b.category_id""";
    public static final String SELECT_BOOK_BY_ID = """
            SELECT b.book_id,
                   b.title,
                   b.author,
                   bc.category_name,
                   b.publish_year,
                   b.description,
                   b.number
            FROM books b
            JOIN book_categories bc ON b.category_id = bc.category_id
            WHERE b.book_id=?""";
    public static final String INSERT_BOOK = """
            INSERT INTO books (title, author, category_id, publish_year, description, number)
            VALUES(?, ?, (SELECT bc.category_id FROM book_categories bc WHERE category_name = ?), ?, ?, ?)""";
    public static final String UPDATE_BOOK = """
            UPDATE books b
            SET b.title=?,
                b.author=?,
                b.category_id=(SELECT bc.category_id FROM book_categories bc WHERE category_name = ?),
                b.publish_year=?,
                b.description=?,
                b.number=?
            WHERE b.book_id=?""";
    public static final String DELETE_BOOK = """
            DELETE
            FROM books b
            WHERE b.book_id=?""";
    public static final String BOOK_SEARCH = """
            SELECT b.book_id,
                   b.title,
                   b.author,
                   bc.category_name,
                   b.publish_year,
                   b.description,
                   b.number
            FROM books b
            JOIN book_categories bc ON b.category_id = bc.category_id
            WHERE b.title LIKE CONCAT('%',?,'%') OR b.author LIKE CONCAT('%',?,'%')""";
    public static final int ELEMENTS_ON_PAGE = 7;
    public static final String FIND_PAGE_QUERY_BOOKS = """
            SELECT b.book_id,
                   b.title,
                   b.author,
                   bc.category_name,
                   b.publish_year,
                   b.description,
                   b.number
            FROM books b
            JOIN book_categories bc ON b.category_id = bc.category_id
            LIMIT ?, ?""";

    /**
     * bookOrder
     */
    public static final String SELECT_BOOKS_BY_ORDER_STATUS = """
            SELECT b.title,
                   u.firstname,
                   u.secondname,
                   os.order_status_name,
                   ot.order_type_name,
                   o.ordered_date,
                   o.reserved_date,
                   o.returned_date,
                   o.rejected_date
            FROM orders o
            JOIN books b ON o.book_id = b.book_id
            JOIN users u ON o.user_id = u.user_id
            JOIN order_statuses os ON os.order_status_id = o.status_id
            JOIN order_types ot ON ot.order_type_id = o.type_id
            WHERE os.order_status_name = ?""";
    public static final String ORDER_BOOK = """
            INSERT INTO orders(book_id, user_id, status_id, type_id)
            VALUES(?, ?,(SELECT os.order_status_id FROM order_statuses os WHERE os.order_status_name='ORDERED'),
                        (SELECT ot.order_type_id FROM order_types ot WHERE ot.order_type_name = ?))""";
    public static final String RESERVE_BOOK = """
            UPDATE orders o
            SET o.status_id(SELECT os.order_status_id FROM order_statuses os WHERE order_status_name = 'RESERVED'),
                o.reserved_date=?
            WHERE o.order_id =?""";
    public static final String RETURN_BOOK = """
            UPDATE orders o
            SET o.status_id(SELECT os.order_status_id FROM order_statuses os WHERE order_status_name = 'RETURNED'),
                o.returned_date=?
            WHERE o.order_id =?""";
    public static final String REJECT_ORDER = """
            UPDATE orders o
            SET o.status_id(SELECT os.order_status_id FROM order_statuses os WHERE order_status_name = 'REJECTED'),
                o.rejected_date=?
            WHERE o.order_id =?""";
    public static final String SELECT_ORDERED_BOOKS_BY_USER_ID_AND_STATUS = """
            SELECT b.title,
                   b.author,
                   bc.category_name,
                   ot.order_type_name,
                   os.order_status_name
            FROM books b
            JOIN book_categories bc ON b.category_id = bc.category_id
            JOIN orders o ON b.book_id = o.book_id
            JOIN order_types ot ON o.type_id = ot.order_type_id
            JOIN users u ON o.user_id = u.user_id
            JOIN order_statuses os ON o.status_id = os.order_status_id
            Where u.user_id = ? AND os.order_status_name = ?""";
    public static final String SET_BOOKS_NUMBER_TO_ONE_LESS = """
            UPDATE books b
            SET number = number - 1
            WHERE b.book_id =(SELECT o. book_id FROM orders o WHERE order_id =?)""";
    public static final String SET_BOOKS_NUMBER_TO_ONE_MORE = """
            UPDATE books b
            SET number = number + 1
            WHERE b.book_id =(SELECT o.book_id FROM orders o WHERE order_id =?)""";
    public static final String SELECT_ORDER_BY_ID = """
            SELECT b.title,
                   u.firstname,
                   u.secondname,
                   os.order_status_name,
                   ot.order_type_name,
                   o.ordered_date,
                   o.reserved_date,
                   o.returned_date,
                   o.rejected_date
            FROM orders o
            JOIN books b ON o.book_id = b.book_id
            JOIN users u ON o.user_id = u.user_id
            JOIN order_statuses os on os.order_status_id = o.status_id
            JOIN order_types ot on ot.order_type_id = o.type_id
            WHERE order_id =?""";
    public static final String DELETE_ORDER = """
            DELETE
            FROM orders o
            WHERE o.order_id =?""";


//    public static final String SELECT_BOOKS_BY_ORDER_STATUS = """
//            SELECT b.title,
//                   u.firstname,
//                   u.secondname,
//                   os.order_status_name,
//                   ot.order_type_name,
//                   ordered_date,
//                   reserved_date,
//                   returned_date,
//                   rejected_date
//            FROM orders o
//            JOIN books b ON o.book_id = b.book_id
//            JOIN users u ON o.user_id = u.user_id
//            JOIN order_statuses os ON os.order_status_id = o.status_id
//            JOIN order_types ot ON ot.order_type_id = o.type_id
//            WHERE os.order_status_name = ?""";
//    public static final String ORDER_BOOK = """
//            INSERT INTO orders(book_id, user_id, status_id, type_id)
//            VALUES(?, ?,(SELECT os.order_status_id FROM order_statuses os WHERE os.order_status_name='ORDERED'),
//                        (SELECT ot.order_type_id FROM order_types ot WHERE ot.order_type_name = ?))""";
//    public static final String RESERVE_BOOK = """
//            UPDATE orders o
//            SET o.status_id(SELECT os.order_status_id FROM order_statuses os WHERE order_status_name = 'RESERVED'),
//                o.reserved_date=?
//            WHERE o.order_id =?""";
//    public static final String RETURN_BOOK = """
//            UPDATE orders o
//            SET o.status_id(SELECT os.order_status_id FROM order_statuses os WHERE order_status_name = 'RETURNED'),
//                o.returned_date=?
//            WHERE o.order_id =?""";
//    public static final String REJECT_ORDER = """
//            UPDATE orders o
//            SET o.status_id(SELECT os.order_status_id FROM order_statuses os WHERE order_status_name = 'REJECTED'),
//                o.rejected_date=?
//            WHERE o.order_id =?""";
//    public static final String SELECT_ORDERED_BOOKS_BY_USER_ID_AND_STATUS = """
//            SELECT b.title,
//                   b.author,
//                   bc.category_name,
//                   ot.order_type_name,
//                   os.order_status_name
//            FROM books b
//            JOIN book_categories bc ON b.category_id = bc.category_id
//            JOIN orders o ON b.book_id = o.book_id
//            JOIN order_types ot ON o.type_id = ot.order_type_id
//            JOIN users u ON o.user_id = u.user_id
//            JOIN order_statuses os ON o.status_id = os.order_status_id
//            Where u.user_id = ? AND os.order_status_name = ?""";
//    public static final String SET_BOOKS_NUMBER_TO_ONE_LESS = """
//            UPDATE books b
//            SET number = number - 1
//            WHERE b.book_id =(SELECT o. book_id FROM orders o WHERE order_id =?)""";
//    public static final String SET_BOOKS_NUMBER_TO_ONE_MORE = """
//            UPDATE books b
//            SET number = number + 1
//            WHERE b.book_id =(SELECT o.book_id FROM orders o WHERE order_id =?)""";
//    public static final String SELECT_ORDER_BY_ID = """
//            SELECT b.title,
//                   u.firstname,
//                   u.secondname,
//                   os.order_status_name,
//                   ot.order_type_name,
//                   ordered_date,
//                   reserved_date,
//                   returned_date,
//                   rejected_date
//            FROM orders o
//            JOIN books b ON o.book_id = b.book_id
//            JOIN users u ON o.user_id = u.user_id
//            JOIN order_statuses os on os.order_status_id = o.status_id
//            JOIN order_types ot on ot.order_type_id = o.type_id
//            WHERE order_id =?""";
//    public static final String DELETE_ORDER = """
//            DELETE
//            FROM orders o
//            WHERE o.order_id =?""";

    private QuerySQL() {
    }
}
