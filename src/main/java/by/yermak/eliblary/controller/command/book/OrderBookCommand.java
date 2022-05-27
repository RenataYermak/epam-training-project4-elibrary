package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParam;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.order.Issue;
import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.service.OrderService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

public class OrderBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final OrderService orderService;

    public OrderBookCommand() {
        this.orderService = new OrderServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAdmin(session)) {
            try {
                Long userId = parseLongParameter(request.getParameter(RequestParam.USER_ID));
                Long bookId = parseLongParameter(request.getParameter(RequestParam.BOOK_ID));
                String status = request.getParameter(RequestParam.ORDER_STATUS);
                String issue = request.getParameter(RequestParam.ISSUE);
                Order order = new Order();
                order.setUserId(userId);
                order.setBookId(bookId);
                order.setStatus(Status.valueOf(status));
                order.setIssue(Issue.valueOf(issue));
                order = orderService.create(order);
                if (order.getId() != null) {
                    LOGGER.log(Level.INFO, "user was registered successfully");
                    List<Order> allOrders = orderService.findAll();
                    request.setAttribute(RequestAttribute.USERS, allOrders);
                }
//                List<Book> books = orderService.findAllBooks();
//                request.setAttribute(RequestAttribute.ORDER_ID, orderId);
//                // request.setAttribute(RequestAttribute.BOOKS, books);
//                request.setAttribute(RequestAttribute.BOOK_TITLE, bookTitle);
                request.setAttribute(RequestAttribute.SUCCESS_MESSAGE_BOOK_UPDATE, "Book was ordered  successfully");
                return new Router(PagePath.BOOKS_TABLE, Router.RouterType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during reserve book: ", e);
            }
        }
        request.setAttribute(RequestAttribute.SUCCESS_MESSAGE_BOOK_UPDATE, "Book didn't ordered ");
        return new Router(PagePath.BOOKS_TABLE, Router.RouterType.FORWARD);
    }
}
