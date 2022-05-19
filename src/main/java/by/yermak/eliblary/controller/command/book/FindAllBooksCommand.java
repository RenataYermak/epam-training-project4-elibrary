package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.ResponseContext;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.model.book.Book;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindAllBooksCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookService bookService;

    public FindAllBooksCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session)) {
            try {
                List<Book> books = bookService.findAllBooks();
                request.setAttribute(RequestAttribute.BOOKS, books);
                return new ResponseContext(PagePath.BOOKS, ResponseContext.ResponseContextType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during find all books: ", e);
            }
        }

        return new ResponseContext(PagePath.SIGN_IN, ResponseContext.ResponseContextType.FORWARD);
    }
}
