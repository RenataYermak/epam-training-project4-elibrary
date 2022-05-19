package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParam;
import by.yermak.eliblary.controller.ResponseContext;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.model.order.Order;
import by.yermak.eliblary.model.order.Status;
import by.yermak.eliblary.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindOrdersByUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookService bookService;

    public FindOrdersByUserCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session)) {
            try {
                Long userId = Long.parseLong(request.getParameter(RequestParam.USER_ID));
                Status orderStatus = Status.valueOf(
                        request.getParameter(RequestParam.ORDER_STATUS).toUpperCase());
                List<Order> orders = bookService.findOrdersByUserIdAndStatus(userId, orderStatus);
                if (orderStatus.equals(Status.ORDERED)) {
                    request.setAttribute(RequestAttribute.ORDERS_PAGE_TITLE, "My Ordered Books");
                } else if (orderStatus.equals(Status.RESERVED)) {
                    request.setAttribute(RequestAttribute.ORDERS_PAGE_TITLE, "My Reserved Books");
                }
                request.setAttribute(RequestAttribute.ORDER_STATUS, orderStatus.getValue());
                request.setAttribute(RequestAttribute.ORDERS, orders);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during find books by userId and orderStatus: ", e);
            }
        }
        return new ResponseContext(PagePath.ORDERS, ResponseContext.ResponseContextType.FORWARD);
    }
}
