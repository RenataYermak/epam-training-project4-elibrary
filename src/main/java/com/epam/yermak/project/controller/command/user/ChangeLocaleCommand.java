package com.epam.yermak.project.controller.command.user;

import com.epam.yermak.project.controller.PagePath;
import com.epam.yermak.project.controller.RequestAttribute;
import com.epam.yermak.project.controller.ResponseContext;
import com.epam.yermak.project.controller.command.Command;
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

