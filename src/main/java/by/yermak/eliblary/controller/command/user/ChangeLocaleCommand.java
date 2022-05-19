package by.yermak.eliblary.controller.command.user;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.ResponseContext;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.command.Command;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLocaleCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        String locale = request.getParameter(RequestAttribute.LOCALE_NAME);
        request.getSession().setAttribute(RequestAttribute.LOCALE_NAME, locale);
        if (session.getAttribute(RequestAttribute.CURRENT_PAGE) == null) {
            return new ResponseContext(PagePath.SIGN_IN, ResponseContext.ResponseContextType.FORWARD);

        }
        return new ResponseContext(String.valueOf(session.getAttribute(RequestAttribute.CURRENT_PAGE)), ResponseContext.ResponseContextType.FORWARD);
    }
}

