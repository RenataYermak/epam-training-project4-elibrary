package by.yermak.eliblary.dao.sql;

public class AuthorSql {
    public static final String SELECT_ALL_AUTHORS = """
            SELECT ba.author_id,
                   ba.author_name
            FROM book_authors ba""";
    public static final String SELECT_AUTHOR_BY_ID = """
            SELECT ba.author_id,
                   ba.author_name
            FROM book_authors ba
            WHERE ba.author_id=?""";
    public static final String INSERT_AUTHOR = """
            INSERT INTO book_authors
            (author_name)
            VALUES (?)""";
    public static final String SQL_IS_AUTHOR_EXIST = """
            SELECT author_id
            FROM book_authors ba
            WHERE ba.author_name= ?
            LIMIT 1""";

    private AuthorSql() {
    }
}
