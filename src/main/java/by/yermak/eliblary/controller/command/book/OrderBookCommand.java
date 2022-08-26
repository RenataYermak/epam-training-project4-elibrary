package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParameter;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.builder.BookBuilder;
import by.yermak.eliblary.entity.builder.OrderBuilder;
import by.yermak.eliblary.entity.builder.UserBuilder;
import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.entity.order.Type;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.OrderService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import by.yermak.eliblary.service.impl.OrderServiceImpl;
import by.yermak.eliblary.util.locale.LanguageMessage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.yermak.eliblary.util.locale.MessagesKey.*;

public class OrderBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final OrderService orderService;
    private final BookService bookService;
    private final LanguageMessage message = LanguageMessage.getInstance();

    public OrderBookCommand() {
        this.orderService = new OrderServiceImpl();
        this.bookService = new BookServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        var currentLocale = request.getSession().getAttribute(RequestAttribute.LOCALE_NAME).toString();
        if (isAuthorized(session)) {
            try {
                if (!orderService.isOrderExist(parseLongParameter(request.getParameter(RequestParameter.BOOK_ID)), parseLongParameter(request.getParameter(RequestParameter.USER_ID)))) {
                    Long orderId = orderService.orderBook(constructBookOrder(request));
                    List<Book> books = bookService.findAll();
                    Order order = orderService.find(orderId);
                    String bookTitle = order.getBook().getTitle();
                    request.setAttribute(RequestAttribute.ORDER_ID, orderId);
                    request.setAttribute(RequestAttribute.BOOKS, books);
                    request.setAttribute(RequestAttribute.BOOK_TITLE, bookTitle);
                    request.setAttribute(RequestAttribute.SUCCESS_MESSAGE_BOOK_ORDER, message.getText(currentLocale, SUCCESS_BOOK_ORDER));
                } else {
                    request.setAttribute(RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, message.getText(currentLocale, BOOK_ALREADY_BOOKED));
                }
                return new Router(PagePath.BOOKS_TABLE_URL, Router.RouterType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during reserve book: ", e);
                request.setAttribute(RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, message.getText(currentLocale, BOOK_NOT_ORDER));
                return new Router(PagePath.ERROR_PAGE_500, Router.RouterType.FORWARD);
            }
        }
        request.setAttribute(RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, message.getText(currentLocale, BOOK_NOT_ORDER));
        return new Router(PagePath.BOOKS_TABLE_URL, Router.RouterType.FORWARD);
    }

    private Order constructBookOrder(HttpServletRequest request) {
        return new OrderBuilder()
                .setType(Type.valueOf(request.getParameter(RequestParameter.TYPE).toUpperCase()))
                .setUser(
                        new UserBuilder()
                                .setId(parseLongParameter(request.getParameter(RequestParameter.USER_ID)))
                                .build())
                .setBook(
                        new BookBuilder()
                                .setId(parseLongParameter(request.getParameter(RequestParameter.BOOK_ID)))
                                .setTitle(request.getParameter(RequestParameter.BOOK_TITLE))
                                .build()
                )
                .build();
    }
}
