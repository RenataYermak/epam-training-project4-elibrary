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

import static by.yermak.eliblary.util.locale.MessagesKey.BOOK_NOT_UPDATE;
import static by.yermak.eliblary.util.locale.MessagesKey.SUCCESS_BOOK_UPDATE;

public class EditBookPictureCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    LanguageMessage message = LanguageMessage.getInstance();

    private final BookService bookService;

    public EditBookPictureCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        var currentLocale = request.getSession().getAttribute(RequestAttribute.LOCALE_NAME).toString();
        if (isAuthorized(session) && isAdmin(session)) {
            byte[] picture = new byte[0];
            try (
                    InputStream stream = request.getPart(RequestParameter.BOOK_PICTURE).getInputStream()) {
                picture = stream.readAllBytes();
            } catch (ServletException | IOException e) {
                LOGGER.log(Level.ERROR, "error while is trying to get photo. {}", e.getMessage());
            }
            try {
                var id = parseLongParameter(request.getParameter(RequestParameter.BOOK_ID));
                var book = bookService.find(id);
                bookService.updatePicture(id, picture);
                request.setAttribute(RequestAttribute.BOOK, book);
                request.setAttribute(
                        RequestAttribute.SUCCESS_MESSAGE_BOOK_UPDATE, message.getText(currentLocale, SUCCESS_BOOK_UPDATE));
                return new Router(PagePath.BOOK_VIEW, Router.RouterType.FORWARD);

            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during updating book: ", e);
                return new Router(PagePath.ERROR_PAGE_500, Router.RouterType.FORWARD);
            }
        }
        request.setAttribute(
                RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, message.getText(currentLocale, BOOK_NOT_UPDATE));
        return new Router(PagePath.BOOK_VIEW, Router.RouterType.FORWARD);
    }
}