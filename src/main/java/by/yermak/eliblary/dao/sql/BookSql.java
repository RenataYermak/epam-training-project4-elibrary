package by.yermak.eliblary.dao.sql;

public class BookSql {
    public static final String SELECT_ALL_BOOKS = """
            SELECT b.book_id,
                   b.title,
                   ba.author_id,
                   ba.author_name,
                   bc.category_name,
                   b.publish_year,
                   b.description,
                   b.number,
                   b.picture
            FROM books b
            JOIN book_categories bc ON bc.category_id = b.category_id
            JOIN book_authors ba ON ba.author_id = b.author_id""";
    public static final String SELECT_BOOK_BY_ID = """
            SELECT b.book_id,
                   b.title,
                   ba.author_id,
                   ba.author_name,
                   bc.category_name,
                   b.publish_year,
                   b.description,
                   b.number,
                   b.picture
            FROM books b
            JOIN book_categories bc ON b.category_id = bc.category_id
            JOIN book_authors ba ON ba.author_id = b.author_id
            WHERE b.book_id=?""";
    public static final String INSERT_BOOK = """
            INSERT INTO books (title, author_id, category_id, publish_year, description, number, picture)
            VALUES(?, ?, (SELECT bc.category_id FROM book_categories bc WHERE bc.category_name = ?), ?, ?, ?, ?)""";
    public static final String UPDATE_PICTURE = """
            UPDATE books b
            SET  b.picture=?
            WHERE b.book_id=?""";
    public static final String UPDATE_BOOK_DATA = """
            UPDATE books b
            SET b.title=?,
                b.author_id=?,
                b.category_id=(SELECT bc.category_id FROM book_categories bc WHERE bc.category_name = ?),
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
                   ba.author_id,
                   ba.author_name,
                   bc.category_name,
                   b.publish_year,
                   b.description,
                   b.number,
                   b.picture
            FROM books b
            JOIN book_categories bc ON b.category_id = bc.category_id
            JOIN book_authors ba ON ba.author_id = b.author_id
            WHERE b.title LIKE CONCAT('%',?,'%') OR b.author LIKE CONCAT('%',?,'%')""";
    public static final int ELEMENTS_ON_PAGE = 12;
    public static final String FIND_PAGE_QUERY_BOOKS = """
            SELECT b.book_id,
                   b.title,
                   ba.author_id,
                   ba.author_name,
                   bc.category_name,
                   b.publish_year,
                   b.description,
                   b.number,
                   b.picture
            FROM books b
            JOIN book_categories bc ON b.category_id = bc.category_id
            JOIN book_authors ba ON ba.author_id = b.author_id
            LIMIT ?, ?""";

    private BookSql() {
    }
}
