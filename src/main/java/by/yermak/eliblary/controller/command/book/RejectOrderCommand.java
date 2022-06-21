package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.service.OrderService;
import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParameter;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RejectOrderCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final OrderService orderService;

    public RejectOrderCommand() {
        this.orderService = new OrderServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session) && isAdmin(session)) {
            try {
                Long orderId = parseLongParameter(request.getParameter(RequestParameter.ORDER_ID));
                orderService.rejectedOrder(orderId);
                List<Order> orders = orderService.findOrdersByOrderStatus(Status.ORDERED);
                request.setAttribute(RequestAttribute.ORDERS_PAGE_TITLE, "All Ordered Books");
                request.setAttribute(RequestAttribute.ORDER_STATUS, Status.ORDERED.getName());
                request.setAttribute(RequestAttribute.ORDERS, orders);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during reject order: ", e);
                return new Router(PagePath.ERROR_PAGE_500,Router.RouterType.FORWARD);
            }
        }
        return new Router(PagePath.ORDERS, Router.RouterType.FORWARD);
    }
}
