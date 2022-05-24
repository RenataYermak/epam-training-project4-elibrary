package by.yermak.eliblary.controller.command;

import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.SessionAttribute;
import by.yermak.eliblary.model.user.Role;
import by.yermak.eliblary.model.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The Command interface
 */
public interface Command {
    /**
     * Executes a given command.
     *
     * @param request the HttpServletRequest object
     * @param session the HttpSession object
     * @return a resulted Router object that contains the result page and routing type
     */
    Router execute(HttpServletRequest request, HttpSession session);

    static Command of(String name) {
        return CommandHelper.getInstance().getCommand(name);
    }

    /**
     * Checked an authorized user
     *
     * @param session the HttpSession object
     * @return whether the user is authorized
     */
    default boolean isAuthorized(HttpSession session) {
        return session.getAttribute(SessionAttribute.AUTHORIZED_USER) != null;
    }

    /**
     * Checked an administrated user
     *
     * @param session the HttpSession object
     * @return whether the user is admin
     */
    default boolean isAdmin(HttpSession session) {
        return getAuthUser(session).getRole().equals(Role.ADMIN);
    }
    /**
     * Get an authorized user from session
     *
     * @param session the HttpSession object
     * @return user from session
     */
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
