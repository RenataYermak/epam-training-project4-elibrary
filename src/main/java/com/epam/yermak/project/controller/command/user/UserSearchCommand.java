package com.epam.yermak.project.controller.command.user;

import com.epam.yermak.project.controller.PagePath;
import com.epam.yermak.project.controller.RequestAttribute;
import com.epam.yermak.project.controller.RequestParam;
import com.epam.yermak.project.controller.ResponseContext;
import com.epam.yermak.project.controller.command.Command;
import com.epam.yermak.project.model.user.User;
import com.epam.yermak.project.service.UserService;
import com.epam.yermak.project.service.exception.ServiceException;
import com.epam.yermak.project.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserSearchCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final UserService userService;

    public UserSearchCommand() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        String searchQuery = request.getParameter(RequestParam.SEARCH_QUERY);
        if (isAuthorized(session)) {
            try {
                List<User> users = userService.findUsersByQuery(searchQuery);
                if (!users.isEmpty()) {
                    request.setAttribute(RequestAttribute.USERS, users);
                } else {
                    request.setAttribute(RequestAttribute.SEARCH_QUERY, searchQuery);
                    request.setAttribute(
                            RequestAttribute.WARNING_MESSAGE_USER_SEARCH, "There were no users found for ");
                }
                return new ResponseContext(PagePath.USERS, ResponseContext.ResponseContextType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during search users: ", e);
            }
        }
        return new ResponseContext(PagePath.USERS, ResponseContext.ResponseContextType.FORWARD);
    }
}
