package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParameter;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.book.Author;
import by.yermak.eliblary.service.AuthorService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.AuthorServiceImpl;
import by.yermak.eliblary.util.locale.LanguageMessage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.yermak.eliblary.util.locale.MessagesKey.*;

public class AddAuthorCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    LanguageMessage message = LanguageMessage.getInstance();

    private final AuthorService authorService;

    public AddAuthorCommand() {
        this.authorService = new AuthorServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        var currentLocale = request.getSession().getAttribute(RequestAttribute.LOCALE_NAME).toString();
        if (isAuthorized(session) && isAdmin(session)) {
            var authorName = request.getParameter(RequestParameter.AUTHOR_NAME);
            var author = new Author();
            author.setName(authorName);
            try {
                if (!authorService.isAuthorExist(authorName)) {
                    author.setName(authorName);
                }else {
                    request.setAttribute(
                            RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, message.getText(currentLocale, LOGIN_ALREADY_EXISTS));
                    return new Router(PagePath.ADD_AUTHOR, Router.RouterType.FORWARD);
                }
                authorService.create(author);
                request.setAttribute(
                        RequestAttribute.SUCCESS_MESSAGE_AUTHOR_CREATE, message.getText(currentLocale, SUCCESS_AUTHOR_ADD));
                return new Router(PagePath.ADD_AUTHOR, Router.RouterType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during book creation: ", e);
                //   return new Router(PagePath.ERROR_PAGE_500, Router.RouterType.FORWARD);
            }
        }
        request.setAttribute(
                RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, message.getText(currentLocale, AUTHOR_NOT_ADD));
        return new Router(PagePath.ADD_AUTHOR, Router.RouterType.FORWARD);
    }
}

