package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParameter;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.service.BookOrderService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookOrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ReturnBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookOrderService orderService;

    public ReturnBookCommand() {
        this.orderService = new BookOrderServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session) && isAdmin(session)) {
            try {
                Long orderId = parseLongParameter(request.getParameter(RequestParameter.ORDER_ID));
                orderService.returnBook(orderId);
                List<Order> orders = orderService.findOrdersByOrderStatus(Status.RESERVED);
                request.setAttribute(RequestAttribute.ORDERS_PAGE_TITLE, "All Reserved Books");
                request.setAttribute(RequestAttribute.ORDER_STATUS, Status.RESERVED.getValue());
                request.setAttribute(RequestAttribute.ORDERS, orders);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during return book: ", e);
            }
        }
        return new Router(PagePath.ORDERS, Router.RouterType.FORWARD);

    }
}
