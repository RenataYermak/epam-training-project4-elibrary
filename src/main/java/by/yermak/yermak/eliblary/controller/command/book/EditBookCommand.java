package by.yermak.yermak.eliblary.controller.command.book;

import by.yermak.yermak.eliblary.controller.PagePath;
import by.yermak.yermak.eliblary.controller.RequestAttribute;
import by.yermak.yermak.eliblary.controller.RequestParam;
import by.yermak.yermak.eliblary.controller.ResponseContext;
import by.yermak.yermak.eliblary.service.BookService;
import by.yermak.yermak.eliblary.service.exception.ServiceException;
import by.yermak.yermak.eliblary.service.impl.BookServiceImpl;
import by.yermak.yermak.eliblary.controller.command.Command;
import by.yermak.yermak.eliblary.model.book.Book;
import by.yermak.yermak.eliblary.model.book.Category;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
                Long id = Long.parseLong(request.getParameter(RequestParam.BOOK_ID));
                String title = request.getParameter(RequestParam.BOOK_TITLE);
                String author = request.getParameter(RequestParam.BOOK_AUTHOR);
                String category = request.getParameter(RequestParam.BOOK_CATEGORY);
                String description = request.getParameter(RequestParam.BOOK_DESCRIPTION);
//                int publishYear = Integer.parseInt(request.getParameter(RequestParam.BOOK_PUBLISH_YEAR));
//                int number = Integer.parseInt(request.getParameter(RequestParam.BOOK_NUMBER));
                String publishYear = request.getParameter(RequestParam.BOOK_PUBLISH_YEAR);
                String number = request.getParameter(RequestParam.BOOK_NUMBER);
                Book book = bookService.findBook(id);
                if (book != null) {
                    if (category != null) {
                        book.setCategory(Category.valueOf(category.toUpperCase()));
                    }
                    book.setTitle(title);
                    book.setAuthor(author);
//                    book.setPublishYear(publishYear);
//                    book.setNumber(number);
                    book.setPublishYear(Integer.parseInt((publishYear)));
                    book.setNumber(Integer.parseInt(number));
                    book.setDescription(description);
                    bookService.update(book);
                    request.setAttribute(RequestAttribute.BOOK, book);
                    request.setAttribute(
                            RequestAttribute.SUCCESS_MESSAGE_BOOK_UPDATE, "Book updated successfully");
                    return new ResponseContext(PagePath.BOOK, ResponseContext.ResponseContextType.FORWARD);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during updating book: ", e);
            }
        }
        return new ResponseContext(PagePath.BOOK, ResponseContext.ResponseContextType.FORWARD);
    }
}
