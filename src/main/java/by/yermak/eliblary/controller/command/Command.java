package by.yermak.eliblary.controller.command;

import by.yermak.eliblary.controller.ResponseContext;
import by.yermak.eliblary.controller.SessionAttribute;
import by.yermak.eliblary.model.user.Role;
import by.yermak.eliblary.model.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@FunctionalInterface
public interface Command {
    ResponseContext execute(HttpServletRequest request, HttpSession session);

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
}
