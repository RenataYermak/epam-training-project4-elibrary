package by.yermak.yermak.eliblary.controller.command.book;

import by.yermak.yermak.eliblary.controller.PagePath;
import by.yermak.yermak.eliblary.controller.RequestParam;
import by.yermak.yermak.eliblary.controller.ResponseContext;
import by.yermak.yermak.eliblary.controller.command.Command;
import by.yermak.yermak.eliblary.service.BookService;
import by.yermak.yermak.eliblary.service.exception.ServiceException;
import by.yermak.yermak.eliblary.service.impl.BookServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DeleteBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookService bookService;

    public DeleteBookCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session) && isAdmin(session)) {
            try {
                Long id = Long.parseLong(request.getParameter(RequestParam.BOOK_ID));
                bookService.delete(id);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during deleting user: ", e);
            }
        }
        return new ResponseContext(PagePath.BOOKS, ResponseContext.ResponseContextType.FORWARD);
    }
}
