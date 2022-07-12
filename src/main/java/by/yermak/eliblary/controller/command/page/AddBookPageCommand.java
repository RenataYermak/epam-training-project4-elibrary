package by.yermak.eliblary.controller.command.page;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.book.Author;
import by.yermak.eliblary.service.AuthorService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.AuthorServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class AddBookPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AuthorService authorService;

    public AddBookPageCommand() {
        this.authorService = new AuthorServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        if (isAuthorized(session) && isAdmin(session)) {
            Router currentRouter = new Router();
            currentRouter.setPagePath(PagePath.ADD_BOOK);
            currentRouter.setRouteType(Router.RouterType.FORWARD);
            try {
                List<Author> authors = new ArrayList<>(authorService.findAll());
                request.setAttribute(RequestAttribute.AUTHORS, authors);
                return currentRouter;
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during book creation: ", e);
            }
        }
        return new Router(PagePath.ADD_BOOK, Router.RouterType.FORWARD);
    }
}