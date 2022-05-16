package com.epam.yermak.project.controller.command.user;

import com.epam.yermak.project.controller.RequestAttribute;
import com.epam.yermak.project.controller.RequestParam;
import com.epam.yermak.project.controller.ResponseContext;
import com.epam.yermak.project.controller.command.Command;
import com.epam.yermak.project.model.user.User;
import com.epam.yermak.project.service.UserService;
import com.epam.yermak.project.service.exception.ServiceException;
import com.epam.yermak.project.service.impl.UserServiceImpl;
import com.epam.yermak.project.controller.PagePath;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class EditPasswordCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final UserService userService;

    public EditPasswordCommand() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session)) {
            try {
                Long id = Long.parseLong(request.getParameter(RequestParam.USER_ID));
                String pass = request.getParameter(RequestParam.USER_PASSWORD);
                User user = userService.findUser(id);
                if (user != null) {
                    user.setPassword(pass);
                    userService.updatePassword(user);
                    request.setAttribute(RequestAttribute.USER, user);
                    request.setAttribute(
                            RequestAttribute.SUCCESS_MESSAGE_USER_UPDATE, "Password updated successfully");

                    return new ResponseContext(PagePath.USER, ResponseContext.ResponseContextType.FORWARD);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during updating user: ", e);
            }
        }
        return new ResponseContext(PagePath.USER, ResponseContext.ResponseContextType.FORWARD);
    }
}
