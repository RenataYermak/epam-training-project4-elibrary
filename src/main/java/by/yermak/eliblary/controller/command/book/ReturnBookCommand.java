package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParam;
import by.yermak.eliblary.controller.ResponseContext;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.model.order.Order;
import by.yermak.eliblary.model.order.Status;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ReturnBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookService bookService;

    public ReturnBookCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session) && isAdmin(session)) {
            try {
                Long orderId = Long.parseLong(request.getParameter(RequestParam.ORDER_ID));
                bookService.returnBook(orderId);
                List<Order> orders = bookService.findOrdersByOrderStatus(Status.RESERVED);
                request.setAttribute(RequestAttribute.ORDERS_PAGE_TITLE, "All Reserved Books");
                request.setAttribute(RequestAttribute.ORDER_STATUS, Status.RESERVED.getValue());
                request.setAttribute(RequestAttribute.ORDERS, orders);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during return book: ", e);
            }
        }
        return new ResponseContext(PagePath.ORDERS, ResponseContext.ResponseContextType.FORWARD);

    }
}
