package by.yermak.eliblary.controller.command.page;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParameter;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BookViewPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookService bookService;

    public BookViewPageCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        Router currentRouter = new Router();
        if (isAuthorized(session)) {
            try {
                Long id = parseLongParameter(request.getParameter(RequestParameter.BOOK_ID));
                var book = bookService.findBook(id);
                request.setAttribute(RequestAttribute.BOOK, book);
                currentRouter.setPagePath(PagePath.BOOK_VIEW);
                currentRouter.setRouteType(Router.RouterType.FORWARD);
                return currentRouter;
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during find book by id: ", e);
              //  return new Router(PagePath.ERROR_PAGE_500, Router.RouterType.FORWARD);
            }
        }
        return new Router(PagePath.BOOK_VIEW, Router.RouterType.FORWARD);
    }
}