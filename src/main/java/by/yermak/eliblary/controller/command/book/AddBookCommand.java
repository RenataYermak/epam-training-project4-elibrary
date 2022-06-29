package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParameter;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;

import by.yermak.eliblary.util.locale.LanguageMessage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static by.yermak.eliblary.util.locale.MessagesKey.BOOK_NOT_ADD;
import static by.yermak.eliblary.util.locale.MessagesKey.SUCCESS_BOOK_ADD;

public class AddBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    LanguageMessage message = LanguageMessage.getInstance();

    private final BookService bookService;

    public AddBookCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        var currentLocale = request.getSession().getAttribute(RequestAttribute.LOCALE_NAME).toString();
        if (isAuthorized(session) && isAdmin(session)) {
            Map<String, String> bookDataMap = new HashMap<>();
            bookDataMap.put(RequestParameter.BOOK_TITLE, request.getParameter(RequestParameter.BOOK_TITLE));
            bookDataMap.put(RequestParameter.BOOK_AUTHOR, request.getParameter(RequestParameter.BOOK_AUTHOR));
            bookDataMap.put(RequestParameter.BOOK_CATEGORY, request.getParameter(RequestParameter.BOOK_CATEGORY));
            bookDataMap.put(RequestParameter.BOOK_PUBLISH_YEAR, request.getParameter(RequestParameter.BOOK_PUBLISH_YEAR));
            bookDataMap.put(RequestParameter.BOOK_NUMBER, request.getParameter(RequestParameter.BOOK_NUMBER));
            bookDataMap.put(RequestParameter.BOOK_DESCRIPTION, request.getParameter(RequestParameter.BOOK_DESCRIPTION));
          bookDataMap.put(RequestParameter.BOOK_PICTURE, request.getParameter(RequestParameter.BOOK_PICTURE));
            byte[] bytesPicture = new byte[0];

            try (
                    InputStream stream = request.getPart(RequestParameter.BOOK_PICTURE).getInputStream()) {
                bytesPicture = stream.readAllBytes();
            } catch (ServletException | IOException e) {
                LOGGER.log(Level.ERROR, "error while addNewProductCommand is trying to get photo. {}", e.getMessage());
            }
            try {
                if (bookService.create(bookDataMap, bytesPicture)) {
                    request.setAttribute(
                            RequestAttribute.SUCCESS_MESSAGE_BOOK_CREATE, message.getText(currentLocale, SUCCESS_BOOK_ADD));
                    return new Router(PagePath.ADD_BOOK, Router.RouterType.FORWARD);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during book creation: ", e);
                //   return new Router(PagePath.ERROR_PAGE_500, Router.RouterType.FORWARD);

            }
        }
        request.setAttribute(
                RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, message.getText(currentLocale, BOOK_NOT_ADD));
        return new Router(PagePath.ADD_BOOK, Router.RouterType.FORWARD);
    }
}
