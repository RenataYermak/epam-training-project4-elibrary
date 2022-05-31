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
//    /**
//     * user
//     */
//    public static final String SELECT_USER_BY_ID = """
//            SELECT user_id,
//                   login,
//                   password,
//                   firstname,
//                   secondname,
//                   email,
//                   role,
//                   status,
//                   activation_date,
//                   deactivation_date
//            FROM users
//            WHERE user_id=?""";
//    public static final String SELECT_USER_BY_LOGIN = """
//            SELECT user_id,
//                   login,
//                   password,
//                   firstname,
//                   secondname,
//                   email,
//                   role,
//                   status,
//                   activation_date,
//                   deactivation_date
//            FROM users
//            WHERE login=?""";
//    public static final String SELECT_USER_BY_LOGIN_AND_PASS = """
//            SELECT user_id,
//                   login,
//                   password,
//                   firstname,
//                   secondname,
//                   email,
//                   role,
//                   status,
//                   activation_date,
//                   deactivation_date
//            FROM users
//            WHERE login=? and password=? AND `status`='ACTIVATED'""";
//    public static final String SELECT_ALL_USERS = """
//            SELECT user_id,
//                   login,
//                   password,
//                   firstname,
//                   secondname,
//                   email,
//                   role,
//                   status,
//                   activation_date,
//                   deactivation_date
//            FROM users""";
//    public static final String SELECT_ALL_ACTIVATED_USERS = """
//            SELECT user_id,
//                   login,
//                   password,
//                   firstname,
//                   secondname,
//                   email,
//                   role,
//                   status,
//                   activation_date,
//                   deactivation_date
//            FROM users
//            WHERE `status`='ACTIVATED'""";
//    public static final String SELECT_ALL_DEACTIVATED_USERS = """
//            SELECT user_id,
//                   login,
//                   password,
//                   firstname,
//                   secondname,
//                   email,
//                   role,
//                   status,
//                   activation_date,
//                   deactivation_date
//            FROM users
//            WHERE `status`='DEACTIVATED'""";
//    public static final String INSERT_USER = """
//            INSERT INTO users(login, password, firstname, secondname, email, role)
//            VALUES(?, ?, ?, ?, ?,?)""";
//    public static final String UPDATE_USER = """
//            UPDATE users
//            SET login=?,
//                password=?,
//                firstname=?,
//                secondname=?,
//                email=?,
//                role=?
//            WHERE user_id=?""";
//    public static final String DEACTIVATE_USER = """
//            UPDATE users
//            SET `status`='DEACTIVATED',
//                deactivation_date=?
//            WHERE user_id=?""";
//    public static final String DELETE_USER = """
//            DELETE
//            FROM users
//            WHERE user_id=?""";
//    public static final String USER_SEARCH = """
//            SELECT user_id,
//                   login,
//                   password,
//                   firstname,
//                   secondname,
//                   email,
//                   role,
//                   status,
//                   activation_date,
//                   deactivation_date
//            FROM users
//            WHERE login LIKE CONCAT('%',?,'%')
//                  OR firstname LIKE CONCAT('%',?,'%')
//                  OR secondname LIKE CONCAT('%',?,'%')""";
//    public static final String UPDATE_PASSWORD = """
//            UPDATE users
//            SET password=?
//            WHERE user_id=?""";
//    public static final String SQL_IS_EMAIL_EXIST = """
//            SELECT user_id
//            FROM users
//            WHERE email = ?
//            LIMIT 1""";
//    public static final String FIND_PAGE_QUERY_USERS = """
//            SELECT user_id,
//                   login,
//                   password,
//                   firstname,
//                   secondname,
//                   email,
//                   role,
//                   status,
//                   activation_date,
//                   deactivation_date
//            FROM users
//            LIMIT ?, ?""";

    /**
     * bookOrder
     */
    public static final String SELECT_BOOKS_BY_ORDER_STATUS = """
            SELECT order_id,
                   book_id,
                   user_id,
                   status,
                   issue,
                   ordered_date,
                   reserved_date,
                   returned_date,
                   rejected_date
            FROM book_orders bo
            INNER JOIN books b ON bo.book_id=b.book_id WHERE status=?""";
    public static final String ORDER_BOOK = """
            INSERT INTO book_orders (book_id, user_id, status, issue)
            VALUES (?, ?, 'ORDERED', ?)""";
    public static final String RESERVE_BOOK = """
            UPDATE book_orders
            SET status='RESERVED',
                reserved_date=?
            WHERE order_id=?""";
    public static final String RETURN_BOOK = """
            UPDATE book_orders
            SET status='RETURNED',
                returned_date=?
            WHERE order_id=?""";
    public static final String REJECT_ORDER = """
            UPDATE book_orders
            SET status='REJECTED',
                rejected_date=?
            WHERE order_id=?""";
    public static final String SELECT_ORDERED_BOOKS_BY_USER_ID_AND_STATUS = """
            SELECT book_id,
                   title,
                   author,
                   category,
                   publish_year,
                   description,
                   number
            FROM books  b
            INNER JOIN book_orders bo ON b.book_id = bo.book_id" +
            INNER JOIN users u ON bo.user_id = u.user_id" +
            WHERE u.user_id=? AND bo.status=?""";
    public static final String SET_BOOKS_NUMBER_TO_ONE_LESS = """
            UPDATE books
            SET number=number-1
            WHERE book_id=(SELECT book_id
                           FROM book_orders
                           WHERE order_id=?)""";
    public static final String SET_BOOKS_NUMBER_TO_ONE_MORE = """
            UPDATE books
            SET number=number+1
            WHERE book_id=(SELECT book_id
                           FROM book_order
                           WHERE order_id=?)""";
    public static final String SELECT_ORDER_BY_ID = """
            SELECT order_id,
                   book_id,
                   user_id,
                   status,
                   issue,
                   ordered_date,
                   reserved_date,
                   returned_date,
                   rejected_date
            FROM book_orders
            WHERE order_id=?""";
    public static final String DELETE_ORDER = """
            DELETE
            FROM book_orders
            WHERE order_id=?""";

    /**
     * book
     */
//    public static final String SELECT_ALL_BOOKS = """
//            SELECT book_id,
//                   title,
//                   author,
//                   category,
//                   publish_year,
//                   description,
//                   number
//            FROM books""";
//    public static final String SELECT_BOOK_BY_ID = """
//            SELECT book_id,
//                   title,
//                   author,
//                   category,
//                   publish_year,
//                   description,
//                   number
//            FROM books
//            WHERE book_id=?""";
//    public static final String INSERT_BOOK = """
//            INSERT INTO books (title, author, category, publish_year, description, number)
//            VALUES(?, ?, ?, ?, ?, ?)""";
//    public static final String UPDATE_BOOK = """
//            UPDATE books
//            SET title=?,
//                author=?,
//                category=?,
//                publish_year=?,
//                description=?,
//                number=?
//            WHERE book_id=?""";
//    public static final String DELETE_BOOK = """
//            DELETE
//            FROM books
//            WHERE book_id=?""";
//    public static final String BOOK_SEARCH = """
//            SELECT book_id,
//                   title,
//                   author,
//                   category,
//                   publish_year,
//                   description,
//                   number
//            FROM books
//            WHERE title LIKE CONCAT('%',?,'%') OR author LIKE CONCAT('%',?,'%')""";
//    public static final int ELEMENTS_ON_PAGE = 7;
//    public static final String FIND_PAGE_QUERY_BOOKS = """
//            SELECT book_id,
//                   title,
//                   author,
//                   category,
//                   publish_year,
//                   description,
//                   number
//            FROM books
//            LIMIT ?, ?""";
    private QuerySQL() {
    }
}
