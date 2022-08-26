package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParameter;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.service.OrderService;
import by.yermak.eliblary.service.UserService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.OrderServiceImpl;
import by.yermak.eliblary.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class FindOrdersByUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final OrderService orderService;
    public final UserService userService;

    public FindOrdersByUserCommand() {
        this.orderService = new OrderServiceImpl();
        this.userService = new UserServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        String page = request.getParameter(RequestAttribute.PAGE);
        int currentPage = page != null ? parseIntParameter(page) : RequestAttribute.DEFAULT_PAGE_NUMBER;
        if (isAuthorized(session)) {
            try {
                Long userId = parseLongParameter(request.getParameter(RequestParameter.USER_ID));
                Status orderStatus = Status.valueOf(
                        request.getParameter(RequestParameter.ORDER_STATUS).toUpperCase());
                List<Order> orderList = new ArrayList<>(orderService.findOrdersByUserIdAndStatus(userId, orderStatus));
                List<Order> orders = new ArrayList<>(orderService.findAll(currentPage, userId, orderStatus));
                int numberOfPages = (int) Math.ceil(orderList.size() * 1.0 / RequestAttribute.RECORDS_PER_PAGE);
                if (orderStatus.equals(Status.ORDERED)) {
                    request.setAttribute(RequestAttribute.ORDERS_PAGE_TITLE, "My Ordered Books");
                } else if (orderStatus.equals(Status.RESERVED)) {
                    request.setAttribute(RequestAttribute.ORDERS_PAGE_TITLE, "My Reserved Books");
                }
                request.setAttribute(RequestAttribute.ORDER_STATUS, orderStatus.getName());
                request.setAttribute(RequestAttribute.NUMBER_OF_PAGES, numberOfPages);
                request.setAttribute(RequestAttribute.PAGE, currentPage);
                request.setAttribute(RequestAttribute.ORDERS, orders);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during find books by userId and orderStatus: ", e);
                return new Router(PagePath.ERROR_PAGE_500, Router.RouterType.FORWARD);
            }
        }
        return new Router(PagePath.ORDERS, Router.RouterType.FORWARD);
    }
}