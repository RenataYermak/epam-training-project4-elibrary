package by.yermak.yermak.eliblary.controller.command.user;

import by.yermak.yermak.eliblary.controller.PagePath;
import by.yermak.yermak.eliblary.service.exception.ServiceException;
import by.yermak.yermak.eliblary.service.impl.UserServiceImpl;
import by.yermak.yermak.eliblary.controller.RequestAttribute;
import by.yermak.yermak.eliblary.controller.RequestParam;
import by.yermak.yermak.eliblary.controller.ResponseContext;
import by.yermak.yermak.eliblary.controller.command.Command;
import by.yermak.yermak.eliblary.model.user.User;
import by.yermak.yermak.eliblary.service.UserService;
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
