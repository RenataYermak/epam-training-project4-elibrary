package by.yermak.eliblary.dao.sql;

public class OrderSql {
    public static final String SELECT_BOOKS_BY_ORDER_STATUS = """
            SELECT o.order_id,
                   os.order_status_name,
                   ot.order_type_name,
                   o.ordered_date,
                   o.reserved_date,
                   o.returned_date,
                   o.rejected_date,
                   u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date,
                   b.book_id,
                   b.title,
                   ba.author_id,
                   ba.author_name,
                   bc.category_name,
                   b.publish_year,
                   b.description,
                   b.number,
                   b.picture
            FROM orders o
            JOIN books b ON b.book_id = o.book_id
            JOIN book_categories bc ON bc.category_id = b.category_id
            JOIN book_authors ba ON ba.author_id = b.author_id
            JOIN users u ON u.user_id = o.user_id
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            JOIN order_types ot ON ot.order_type_id = o.type_id
            JOIN order_statuses os ON os.order_status_id = o.status_id
            WHERE os.order_status_name = ?""";
    public static final String SELECT_ORDERED_BOOKS_BY_USER_ID_AND_STATUS = """
            SELECT o.order_id,
                   os.order_status_name,
                   ot.order_type_name,
                   o.ordered_date,
                   o.reserved_date,
                   o.returned_date,
                   o.rejected_date,
                   u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date,
                   b.book_id,
                   b.title,
                   ba.author_id,
                   ba.author_name,
                   bc.category_name,
                   b.publish_year,
                   b.description,
                   b.number,
                   b.picture
            FROM orders o
            JOIN books b ON b.book_id = o.book_id
            JOIN book_categories bc ON bc.category_id = b.category_id
            JOIN book_authors ba ON ba.author_id = b.author_id
            JOIN users u ON u.user_id = o.user_id
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            JOIN order_types ot ON ot.order_type_id = o.type_id
            JOIN order_statuses os ON os.order_status_id = o.status_id
            WHERE u.user_id = ? AND os.order_status_name = ?""";
    public static final String ORDER_BOOK = """
            INSERT INTO orders (book_id, user_id, status_id, type_id)
            VALUES((SELECT b.book_id FROM books b WHERE b.book_id= ?),
                  (SELECT u.user_id FROM users u WHERE u.user_id = ?),
                  (SELECT os.order_status_id FROM order_statuses os WHERE os.order_status_name='ORDERED'),
                  (SELECT ot.order_type_id FROM order_types ot WHERE ot.order_type_name = ?))""";
    public static final String RESERVE_BOOK = """
            UPDATE orders o
            SET o.status_id=(SELECT os.order_status_id FROM order_statuses os WHERE order_status_name = 'RESERVED'),
                o.reserved_date=?
            WHERE o.order_id =?""";
    public static final String RETURN_BOOK = """
            UPDATE orders o
            SET o.status_id=(SELECT os.order_status_id FROM order_statuses os WHERE order_status_name = 'RETURNED'),
                o.returned_date=?
            WHERE o.order_id =?""";
    public static final String REJECT_ORDER = """
            UPDATE orders o
            SET o.status_id=(SELECT os.order_status_id FROM order_statuses os WHERE order_status_name = 'REJECTED'),
                o.rejected_date=?
            WHERE o.order_id =?""";
    public static final String SET_BOOKS_NUMBER_TO_ONE_LESS = """
            UPDATE books b
            SET b.number = b.number - 1
            WHERE b.book_id =(SELECT o. book_id FROM orders o WHERE order_id =?)""";
    public static final String SET_BOOKS_NUMBER_TO_ONE_MORE = """
            UPDATE books b
            SET b.number = b.number + 1
            WHERE b.book_id =(SELECT o.book_id FROM orders o WHERE order_id =?)""";
    public static final String SELECT_ORDER_BY_ID = """
            SELECT o.order_id,
                   os.order_status_name,
                   ot.order_type_name,
                   o.ordered_date,
                   o.reserved_date,
                   o.returned_date,
                   o.rejected_date,
                   u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date,
                   b.book_id,
                   b.title,
                   ba.author_id,
                   ba.author_name,
                   bc.category_name,
                   b.publish_year,
                   b.description,
                   b.number,
                   b.picture
            FROM orders o
            JOIN books b ON b.book_id = o.book_id
            JOIN book_categories bc ON bc.category_id = b.category_id
            JOIN book_authors ba ON ba.author_id = b.author_id
            JOIN users u ON u.user_id = o.user_id
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            JOIN order_types ot ON ot.order_type_id = o.type_id
            JOIN order_statuses os ON os.order_status_id = o.status_id
            WHERE o.order_id =?""";
    public static final String DELETE_ORDER = """
            DELETE
            FROM orders o
            WHERE o.order_id =?""";
    public static final String SELECT_ALL_ORDERS = """
            SELECT b.title,
                   ba.author_name,
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
            JOIN book_authors ba ON ba.author_id = b.author_id
            JOIN order_statuses os on os.order_status_id = o.status_id
            JOIN order_types ot on ot.order_type_id = o.type_id""";
    public static final String FIND_PAGE_QUERY_ORDERS = """
            SELECT o.order_id,
                   os.order_status_name,
                   ot.order_type_name,
                   o.ordered_date,
                   o.reserved_date,
                   o.returned_date,
                   o.rejected_date,
                   u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date,
                   b.book_id,
                   b.title,
                   ba.author_id,
                   ba.author_name,
                   bc.category_name,
                   b.publish_year,
                   b.description,
                   b.number,
                   b.picture
            FROM orders o
            JOIN books b ON b.book_id = o.book_id
            JOIN book_categories bc ON bc.category_id = b.category_id
            JOIN users u ON u.user_id = o.user_id
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            JOIN order_types ot ON ot.order_type_id = o.type_id
            JOIN order_statuses os ON os.order_status_id = o.status_id
            JOIN book_authors ba ON ba.author_id = b.author_id
            WHERE os.order_status_name = ?
            LIMIT ?,?""";
    public static final String FIND_PAGE_QUERY_ORDERS_BY_USER = """
            SELECT o.order_id,
                   os.order_status_name,
                   ot.order_type_name,
                   o.ordered_date,
                   o.reserved_date,
                   o.returned_date,
                   o.rejected_date,
                   u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date,
                   b.book_id,
                   b.title,
                   ba.author_id,
                   ba.author_name,
                   bc.category_name,
                   b.publish_year,
                   b.description,
                   b.number,
                   b.picture
            FROM orders o
            JOIN books b ON b.book_id = o.book_id
            JOIN book_categories bc ON bc.category_id = b.category_id
            JOIN users u ON u.user_id = o.user_id
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            JOIN order_types ot ON ot.order_type_id = o.type_id
            JOIN order_statuses os ON os.order_status_id = o.status_id
            JOIN book_authors ba ON ba.author_id = b.author_id
            WHERE u.user_id = ? AND os.order_status_name = ?
            LIMIT ?,?""";
    public static final String SQL_IS_BOOK_ORDER_EXIST = """
            SELECT order_id
            FROM orders o
            WHERE o.book_id = ? AND o.user_id = ?
            LIMIT 1""";

    private OrderSql() {
    }
}
