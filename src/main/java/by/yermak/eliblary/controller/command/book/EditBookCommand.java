package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParam;
import by.yermak.eliblary.controller.ResponseContext;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.model.book.Book;
import by.yermak.eliblary.model.book.Category;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class EditBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookService bookService;

    public EditBookCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session) && isAuthorized(session)) {
            try {
                Long id = parseLongParameter(request.getParameter(RequestParam.BOOK_ID));
                String title = request.getParameter(RequestParam.BOOK_TITLE);
                String author = request.getParameter(RequestParam.BOOK_AUTHOR);
                String category = request.getParameter(RequestParam.BOOK_CATEGORY);
 ///               String description = request.getParameter(RequestParam.BOOK_DESCRIPTION);
                int publishYear = parseIntParameter(request.getParameter(RequestParam.BOOK_PUBLISH_YEAR));
                int number = parseIntParameter(request.getParameter(RequestParam.BOOK_NUMBER));
                Book book = bookService.findBook(id);
                if (book != null) {
                    if (category != null) {
                        book.setCategory(Category.valueOf(category.toUpperCase()));
                    }
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setPublishYear(publishYear);
                    book.setNumber(number);
//                    book.setDescription(description);
                    bookService.update(book);
                    request.setAttribute(RequestAttribute.BOOK, book);
                    request.setAttribute(
                            RequestAttribute.SUCCESS_MESSAGE_BOOK_UPDATE, "Book updated successfully");
                    return new ResponseContext(PagePath.EDIT_BOOK, ResponseContext.ResponseContextType.FORWARD);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during updating book: ", e);
            }
        }
        request.setAttribute(
                RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, "Book didn't update");
        return new ResponseContext(PagePath.EDIT_BOOK, ResponseContext.ResponseContextType.FORWARD);
    }
}
