package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.OrderService;
import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParameter;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import by.yermak.eliblary.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindOrdersByUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final OrderService orderService;
    private final BookService bookService;

    public FindOrdersByUserCommand() {
        this.orderService = new OrderServiceImpl();
        this.bookService = new BookServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session)) {
            try {
                Long userId = parseLongParameter(request.getParameter(RequestParameter.USER_ID));
                Status orderStatus = Status.valueOf(
                        request.getParameter(RequestParameter.ORDER_STATUS).toUpperCase());
                List<Order> orders = orderService.findOrdersByUserIdAndStatus(userId, orderStatus);
                if (orderStatus.equals(Status.ORDERED)) {
                    request.setAttribute(RequestAttribute.ORDERS_PAGE_TITLE, "My Ordered Books");
                } else if (orderStatus.equals(Status.RESERVED)) {
                    request.setAttribute(RequestAttribute.ORDERS_PAGE_TITLE, "My Reserved Books");
                }
                List<Book> books = bookService.findAllBooks();
                request.setAttribute(RequestAttribute.BOOKS, books);
                // request.setAttribute(RequestAttribute.BOOK_TITLE, bookTitle);

                request.setAttribute(RequestAttribute.ORDER_STATUS, orderStatus.getValue());
                request.setAttribute(RequestAttribute.ORDERS, orders);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during find books by userId and orderStatus: ", e);
            }
        }
        return new Router(PagePath.ORDERS, Router.RouterType.FORWARD);
    }
}
