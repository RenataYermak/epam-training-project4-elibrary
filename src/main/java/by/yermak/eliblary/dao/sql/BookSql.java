package by.yermak.eliblary.dao.sql;

public class BookSql {
    public static final String SELECT_ALL_BOOKS = """
            SELECT b.book_id,
                   b.title,
                   b.author,
                   bc.category_name,
                   b.publish_year,
                   b.description,
                   b.number,
                   b.picture
            FROM books b
            JOIN book_categories bc ON bc.category_id = b.category_id""";
    public static final String SELECT_BOOK_BY_ID = """
            SELECT b.book_id,
                   b.title,
                   b.author,
                   bc.category_name,
                   b.publish_year,
                   b.description,
                   b.number,
                   b.picture
            FROM books b
            JOIN book_categories bc ON b.category_id = bc.category_id
            WHERE b.book_id=?""";
    public static final String INSERT_BOOK = """
            INSERT INTO books (title, author, category_id, publish_year, description, number, picture)
            VALUES(?, ?, (SELECT bc.category_id FROM book_categories bc WHERE bc.category_name = ?), ?, ?, ?, ?)""";
    public static final String UPDATE_BOOK = """
            UPDATE books b
            SET b.title=?,
                b.author=?,
                b.category_id=(SELECT bc.category_id FROM book_categories bc WHERE bc.category_name = ?),
                b.publish_year=?,
                b.description=?,
                b.number=?,
                b.picture=?
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
                   b.number,
                   b.picture
            FROM books b
            JOIN book_categories bc ON b.category_id = bc.category_id
            WHERE b.title LIKE CONCAT('%',?,'%') OR b.author LIKE CONCAT('%',?,'%')""";
    public static final int ELEMENTS_ON_PAGE = 12;
    public static final String FIND_PAGE_QUERY_BOOKS = """
            SELECT b.book_id,
                   b.title,
                   b.author,
                   bc.category_name,
                   b.publish_year,
                   b.description,
                   b.number,
                   b.picture
            FROM books b
            JOIN book_categories bc ON b.category_id = bc.category_id
            LIMIT ?, ?""";

    private BookSql() {
    }
}
