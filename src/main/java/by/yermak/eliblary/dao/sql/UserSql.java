package by.yermak.eliblary.dao.sql;

public class UserSql {
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
            SET u.status_id=(SELECT user_status_id FROM user_statuses us WHERE us.status_name='DEACTIVATED'),
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

    private UserSql() {
    }
}
