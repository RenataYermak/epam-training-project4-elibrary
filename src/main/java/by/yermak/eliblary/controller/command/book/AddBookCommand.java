package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParameter;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.book.Author;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.book.Category;
import by.yermak.eliblary.service.AuthorService;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.AuthorServiceImpl;
import by.yermak.eliblary.service.impl.BookServiceImpl;

import by.yermak.eliblary.util.locale.LanguageMessage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.*;

import static by.yermak.eliblary.util.locale.MessagesKey.BOOK_NOT_ADD;
import static by.yermak.eliblary.util.locale.MessagesKey.SUCCESS_BOOK_ADD;

public class AddBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BookService bookService;
    private final AuthorService authorService;
    private final LanguageMessage message = LanguageMessage.getInstance();

    public AddBookCommand() {
        this.bookService = new BookServiceImpl();
        this.authorService = new AuthorServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        var currentLocale = request.getSession().getAttribute(RequestAttribute.LOCALE_NAME).toString();
        if (isAuthorized(session) && isAdmin(session)) {
            byte[] bytesPicture = new byte[0];
            try (
                    InputStream stream = request.getPart(RequestParameter.BOOK_PICTURE).getInputStream()) {
                bytesPicture = stream.readAllBytes();
            } catch (ServletException | IOException e) {
                LOGGER.log(Level.ERROR, "error while addNewProductCommand is trying to get photo. {}", e.getMessage());
            }
            var title = request.getParameter(RequestParameter.BOOK_TITLE);
            var author = parseLongParameter(request.getParameter(RequestParameter.BOOK_AUTHOR));
            var category = request.getParameter(RequestParameter.BOOK_CATEGORY);
            var publishYear = parseIntParameter(request.getParameter(RequestParameter.BOOK_PUBLISH_YEAR));
            var number = parseIntParameter(request.getParameter(RequestParameter.BOOK_NUMBER));
            var description = request.getParameter(RequestParameter.BOOK_DESCRIPTION);

            var book = new Book();
            book.setTitle(title);
            book.setAuthor(new Author(author));
            book.setCategory(Category.valueOf(category.toUpperCase()));
            book.setPublishYear(publishYear);
            book.setNumber(number);
            book.setDescription(description);

            try {
                if (bookService.create(book, bytesPicture)) {
                    request.setAttribute(
                            RequestAttribute.SUCCESS_MESSAGE_BOOK_CREATE, message.getText(currentLocale, SUCCESS_BOOK_ADD));
                    return new Router(PagePath.ADD_BOOK, Router.RouterType.FORWARD);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during book creation: ", e);
                return new Router(PagePath.ERROR_PAGE_500, Router.RouterType.FORWARD);
            }
        }
        request.setAttribute(
                RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, message.getText(currentLocale, BOOK_NOT_ADD));
        return new Router(PagePath.ADD_BOOK, Router.RouterType.FORWARD);
    }
}