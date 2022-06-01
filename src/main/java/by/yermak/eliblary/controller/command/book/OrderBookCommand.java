package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParameter;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.order.Type;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.OrderService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import by.yermak.eliblary.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class OrderBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final OrderService orderService;
    private final BookService bookService;

    public OrderBookCommand() {
        this.orderService = new OrderServiceImpl();
        this.bookService = new BookServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session)) {
            try {
                Long userId = parseLongParameter(request.getParameter(RequestParameter.USER_ID));
                Long bookId = parseLongParameter(request.getParameter(RequestParameter.BOOK_ID));
                String bookTitle = request.getParameter(RequestParameter.BOOK_TITLE);
                String type = request.getParameter(RequestParameter.TYPE);
                Long orderId = orderService.orderBook(bookId, userId, Type.valueOf(type.toUpperCase()));

                List<Book> books = bookService.findAllBooks();
                request.setAttribute(RequestAttribute.ORDER_ID, orderId);
                request.setAttribute(RequestAttribute.BOOKS, books);
                request.setAttribute(RequestAttribute.BOOK_TITLE, bookTitle);
                request.setAttribute(RequestAttribute.SUCCESS_MESSAGE_BOOK_UPDATE, "Book was ordered  successfully");
                return new Router(PagePath.BOOKS_TABLE_URL, Router.RouterType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during reserve book: ", e);
            }
        }
        request.setAttribute(RequestAttribute.SUCCESS_MESSAGE_BOOK_UPDATE, "Book didn't ordered ");
        return new Router(PagePath.BOOKS_TABLE, Router.RouterType.FORWARD);
    }
}
