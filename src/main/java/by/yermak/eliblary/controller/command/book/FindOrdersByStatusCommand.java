package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParam;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.service.OrderService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindOrdersByStatusCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final OrderService orderService;

    public FindOrdersByStatusCommand() {
        this.orderService = new OrderServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAdmin(session) && isAuthorized(session)) {
            try {
                Status orderStatus = Status.valueOf(
                        request.getParameter(RequestParam.ORDER_STATUS).toUpperCase());
                List<Order> orders = orderService.findOrdersByOrderStatus(orderStatus);
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
        return new Router(PagePath.ORDERS, Router.RouterType.FORWARD);
    }
}
