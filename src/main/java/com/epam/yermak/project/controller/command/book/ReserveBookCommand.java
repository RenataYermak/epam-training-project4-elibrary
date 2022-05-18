package com.epam.yermak.project.controller.command.book;

import com.epam.yermak.project.controller.PagePath;
import com.epam.yermak.project.controller.RequestAttribute;
import com.epam.yermak.project.controller.RequestParam;
import com.epam.yermak.project.controller.ResponseContext;
import com.epam.yermak.project.controller.command.Command;
import com.epam.yermak.project.model.order.Order;
import com.epam.yermak.project.model.order.Status;
import com.epam.yermak.project.service.BookService;
import com.epam.yermak.project.service.exception.ServiceException;
import com.epam.yermak.project.service.impl.BookServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ReserveBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookService bookService;

    public ReserveBookCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session) && isAdmin(session)) {
            try {
                Long orderId = Long.parseLong(request.getParameter(RequestParam.ORDER_ID));
                bookService.reserveBook(orderId);
                List<Order> orders = bookService.findOrdersByOrderStatus(Status.ORDERED);
                request.setAttribute(RequestAttribute.ORDERS_PAGE_TITLE, "All Ordered Books");
                request.setAttribute(RequestAttribute.ORDER_STATUS, Status.ORDERED.getValue());
                request.setAttribute(RequestAttribute.ORDERS, orders);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during reserve book: ", e);
            }
        }
        return new ResponseContext(PagePath.ORDERS, ResponseContext.ResponseContextType.FORWARD);
    }
}
