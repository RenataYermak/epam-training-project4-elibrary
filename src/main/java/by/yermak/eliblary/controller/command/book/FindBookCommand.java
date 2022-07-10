package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.entity.book.Author;
import by.yermak.eliblary.service.AuthorService;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.AuthorServiceImpl;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParameter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class FindBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookService bookService;
    private final AuthorService authorService;

    public FindBookCommand() {
        this.bookService = new BookServiceImpl();
        this.authorService = new AuthorServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session)) {
            try {
                Long id = parseLongParameter(request.getParameter(RequestParameter.BOOK_ID));
                var book = bookService.find(id);
                List<Author> authors = new ArrayList<>(authorService.findAll());
                request.setAttribute(RequestAttribute.AUTHORS, authors);
                request.setAttribute(RequestAttribute.BOOK, book);
                return new Router(PagePath.EDIT_BOOK, Router.RouterType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during find book by id: ", e);
                return new Router(PagePath.ERROR_PAGE_500, Router.RouterType.FORWARD);
            }
        }
        return new Router(PagePath.SIGN_IN, Router.RouterType.FORWARD);
    }
}
