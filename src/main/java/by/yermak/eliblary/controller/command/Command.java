package by.yermak.eliblary.controller.command;

import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.SessionAttribute;
import by.yermak.eliblary.model.user.Role;
import by.yermak.eliblary.model.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@FunctionalInterface
public interface Command {
    Router execute(HttpServletRequest request, HttpSession session);

    static Command of(String name) {
        return CommandHelper.getInstance().getCommand(name);
    }

    default boolean isAuthorized(HttpSession session) {
        return session.getAttribute(SessionAttribute.AUTHORIZED_USER) != null;
    }

    default boolean isAdmin(HttpSession session) {
        return getAuthUser(session).getRole().equals(Role.ADMIN);
    }

    default User getAuthUser(HttpSession session) {
        return (User) session.getAttribute(SessionAttribute.AUTHORIZED_USER);
    }
    /**
     * Parses a long input parameter.
     *
     * @param input an input request parameter
     * @return parsed long parameter or 0 otherwise
     */
    default long parseLongParameter(String input) {
        try {
            return Long.parseLong((input));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Parses an int input parameter.
     *
     * @param input an input request parameter
     * @return parsed int parameter or 0 otherwise
     */
    default int parseIntParameter(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
