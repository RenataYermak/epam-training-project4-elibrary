package by.yermak.yermak.eliblary.controller.command.book;

import by.yermak.yermak.eliblary.controller.PagePath;
import by.yermak.yermak.eliblary.controller.RequestAttribute;
import by.yermak.yermak.eliblary.controller.RequestParam;
import by.yermak.yermak.eliblary.controller.ResponseContext;
import by.yermak.yermak.eliblary.controller.command.Command;
import by.yermak.yermak.eliblary.model.order.Order;
import by.yermak.yermak.eliblary.model.order.Status;
import by.yermak.yermak.eliblary.service.BookService;
import by.yermak.yermak.eliblary.service.exception.ServiceException;
import by.yermak.yermak.eliblary.service.impl.BookServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindOrdersByStatus implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookService bookService;

    public FindOrdersByStatus() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAdmin(session) && isAuthorized(session)) {
            try {
                Status orderStatus = Status.valueOf(
                        request.getParameter(RequestParam.ORDER_STATUS).toUpperCase());
                List<Order> orders = bookService.findOrdersByOrderStatus(orderStatus);
                if (orderStatus.equals(Status.ORDERED)) {
                    request.setAttribute(RequestAttribute.ORDERS_PAGE_TITLE, "All Ordered Books");
                } else if(orderStatus.equals(Status.RESERVED)) {
                    request.setAttribute(RequestAttribute.ORDERS_PAGE_TITLE, "All Reserved Books");
                }
                request.setAttribute(RequestAttribute.ORDER_STATUS, orderStatus.getValue());
                request.setAttribute(RequestAttribute.ORDERS, orders);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during find books by orderStatus: ", e);
            }
        }
        return new ResponseContext(PagePath.ORDERS, ResponseContext.ResponseContextType.FORWARD);
    }
}
